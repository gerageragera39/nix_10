package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.persistence.dao.CountriesDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CountriesDaoImpl implements CountriesDao {

    private final JpaConfig jpaConfig;
    private final PopulationDaoImpl populationDao;

    private static final String CREATE_COUNTRY_QUERY = "insert into countries values(default, ?,?,?,?,?)";
    private static final String FIND_COUNTRY_BY_ID_QUERY = "select * from countries where id = ";
    private static final String FIND_COUNTRY_BY_PERSON_ID_QUERY = "select id, country_name, ISO from countries left join country_person cp on countries.id = cp.country_id where person_id = ";
    private final static String EXIST_COUNTRY_NAME_ID_QUERY = "select count(*) from countries where country_name = ";
    private final static String EXIST_COUNTRY_ISO_ID_QUERY = "select count(*) from countries where ISO = ";
    private static final String DELETE_COUNTRY_QUERY = "delete from countries where id = ";
    private static final String DELETE_RELATION_BY_COUNTRY_ID_QUERY = "delete from country_person where country_id = ";
    private static final String FIND_ALL_PERSON_WITH_COUNTRY_COUNT_QUERY = "select id, count(person_id) as countryCount from population as people left join country_person as cp on people.id = cp.person_id group by people.id";
    private static final String DO_PERSON_NOT_VISIBLE_QUERY = "update population set visible = false where id = ";
    private static final String UPDATE_COUNTRY_QUERY = "update countries set updated = ?, country_name = ?, ISO = ? where id = ?";

    public CountriesDaoImpl(JpaConfig jpaConfig, PopulationDaoImpl populationDao) {
        this.jpaConfig = jpaConfig;
        this.populationDao = populationDao;
    }

    @Override
    public void create(Countries entity) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_COUNTRY_QUERY)) {
            preparedStatement.setTimestamp(1, new Timestamp(entity.getCreated().getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(entity.getUpdated().getTime()));
            preparedStatement.setBoolean(3, entity.getVisible());
            preparedStatement.setString(4, entity.getNameOfCountry());
            preparedStatement.setInt(5, entity.getISO());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public void update(Countries entity) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_COUNTRY_QUERY)) {
            preparedStatement.setTimestamp(1, new Timestamp(entity.getUpdated().getTime()));
            preparedStatement.setString(2, entity.getNameOfCountry());
            preparedStatement.setLong(3, entity.getISO());
            preparedStatement.setLong(4, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(DELETE_COUNTRY_QUERY + id);
            PreparedStatement preparedStatement2 = jpaConfig.getConnection().prepareStatement(DELETE_RELATION_BY_COUNTRY_ID_QUERY + id);
            preparedStatement2.executeUpdate();
            preparedStatement.executeUpdate();

            ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_ALL_PERSON_WITH_COUNTRY_COUNT_QUERY);
            while (resultSet.next()) {
                int count = resultSet.getInt("countryCount");
                if (count == 0) {
                    long personId = resultSet.getLong("id");
                    PreparedStatement preparedStatement3 = jpaConfig.getConnection().prepareStatement(DO_PERSON_NOT_VISIBLE_QUERY + personId);
                    preparedStatement3.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public boolean existById(Long id) {
        return false;
    }

    @Override
    public boolean existById(String countryName, int ISO) {
        int count1 = 0;
        int count2 = 0;
        try {
            countryName = String.valueOf("'") + countryName + String.valueOf("'");
            ResultSet resultSet = jpaConfig.getStatement().executeQuery(EXIST_COUNTRY_NAME_ID_QUERY + countryName);
            if (resultSet.next()) {
                count1 = resultSet.getInt("count(*)");
            }
            ResultSet resultSet2 = jpaConfig.getStatement().executeQuery(EXIST_COUNTRY_ISO_ID_QUERY + ISO);
            if (resultSet2.next()) {
                count2 = resultSet2.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (count1 == 1) || (count2 == 1);
    }

    @Override
    public Countries findById(Long id) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_COUNTRY_BY_ID_QUERY + id)) {
            while (resultSet.next()) {
                return initCountryByResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DataTableResponse<Countries> findAll(DataTableRequest request) {
        List<Countries> countries = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();

        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        String sql = "select id, country_name, ISO, count(country_id) as personCount " +
                "from countries as country left join country_person as cp on country.id = cp.country_id " +
                "group by country.id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();

        System.out.println(sql);

        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(sql)) {
            while (resultSet.next()) {
                CountriesResultSet countriesResultSet = convertResultSetToSimpleCountry(resultSet);
                countries.add(countriesResultSet.getCountry());
                otherParamMap.put(countriesResultSet.getCountry().getId(), countriesResultSet.getPersonCount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataTableResponse<Countries> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(countries);
        tableResponse.setOtherParamMap(otherParamMap);
        return tableResponse;
    }

    @Override
    public long count() {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery("select count(*) as count from countries")) {
            while (resultSet.next()) {
                return resultSet.getLong("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private CountriesResultSet convertResultSetToSimpleCountry(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String countryName = resultSet.getString("country_name");
        int ISO = resultSet.getInt("ISO");
        int personCount = resultSet.getInt("personCount");

        Countries country = new Countries();
        country.setId(id);
        country.setNameOfCountry(countryName);
        country.setISO(ISO);

        return new CountriesResultSet(country, personCount);
    }

    private Countries initCountryByResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        Timestamp created = resultSet.getTimestamp("created");
        Timestamp updated = resultSet.getTimestamp("updated");
        Boolean visible = resultSet.getBoolean("visible");
        String nameOfCountry = resultSet.getString("country_name");
        int ISO = resultSet.getInt("ISO");

        Countries country = new Countries();
        country.setId(id);
        country.setCreated(created);
        country.setUpdated(updated);
        country.setVisible(visible);
        country.setNameOfCountry(nameOfCountry);
        country.setISO(ISO);

        return country;
    }

    private static class CountriesResultSet {

        private final Countries country;
        private final int personCount;

        private CountriesResultSet(Countries country, int personCount) {
            this.country = country;
            this.personCount = personCount;
        }

        public Countries getCountry() {
            return country;
        }

        public int getPersonCount() {
            return personCount;
        }
    }

    public List<String> findAllCountriesNames() {
        List<String> names = new ArrayList<>();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery("select country_name from countries")) {
            while (resultSet.next()) {
                names.add(resultSet.getString("country_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    @Override
    public Map<Long, String> findByCountryId(Long personId) {
        Map<Long, String> map = new HashMap<>();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_COUNTRY_BY_PERSON_ID_QUERY + personId)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String countryName = resultSet.getString("country_name");
                String ISO = resultSet.getString("ISO");
                map.put(id, countryName + " " + ISO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }
}
