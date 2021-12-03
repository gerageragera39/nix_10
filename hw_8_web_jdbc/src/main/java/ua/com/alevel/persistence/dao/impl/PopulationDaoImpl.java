package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.persistence.dao.PopulationDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Population;
import ua.com.alevel.persistence.sex.Sex;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PopulationDaoImpl implements PopulationDao {

    private final JpaConfig jpaConfig;

    private static final String CREATE_PERSON_QUERY = "insert into population values(default, ?,?,?,?,?,?,?,?)";
    private static final String FIND_PERSON_BY_COUNTRY_ID_QUERY = "select id, first_name, last_name from population left join country_person cp on population.id = cp.person_id where country_id = ";
    private static final String CREATE_RELATION_QUERY = "insert into country_person values(?,?)";
    private static final String DELETE_PERSON_QUERY = "delete from population where id = ";
    private static final String DELETE_RELATION_BY_PERSON_ID_QUERY = "delete from country_person where person_id = ";
    private static final String FIND_PERSON_BY_ID_QUERY = "select * from population where id = ";
    private final static String EXIST_PERSON_BY_PASSPORT_ID_QUERY = "select count(*) from population where passport_id = ";
    private static final String DELETE_RELATION_BY_PERSON_ID_AND_COUNTRY_ID_QUERY = "delete from country_person where country_id = ? and person_id = ?";
    private static final String FIND_ALL_PERSON_WITH_COUNTRY_COUNT_QUERY = "select id, count(person_id) as countryCount from population as people left join country_person as cp on people.id = cp.person_id group by people.id";
    private static final String DO_PERSON_NOT_VISIBLE_QUERY = "update population set visible = false where id = ";
    private static final String UPDATE_PERSON_QUERY = "update population set updated = ?, first_name = ?, last_name = ?, age = ?, sex = ? where id = ?";
    private static final String FIND_PERSON_BY_PASSPORT_ID_QUERY = "select * from population where passport_id = ";
    private static final String UPDATE_PERSON_VISIBLE_QUERY = "update population set updated = ?, visible = true where passport_id = ?";

    public PopulationDaoImpl(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

    @Override
    public void create(Population entity) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_PERSON_QUERY)) {
            preparedStatement.setTimestamp(1, new Timestamp(entity.getCreated().getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(entity.getUpdated().getTime()));
            preparedStatement.setBoolean(3, entity.getVisible());
            preparedStatement.setString(4, entity.getFirstName());
            preparedStatement.setString(5, entity.getLastName());
            preparedStatement.setInt(6, entity.getAge());
            preparedStatement.setString(7, entity.getSex().toString());
            preparedStatement.setString(8, entity.getPassportID());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public void update(Population entity) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_PERSON_QUERY)) {
            preparedStatement.setTimestamp(1, new Timestamp(entity.getUpdated().getTime()));
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setString(3, entity.getLastName());
            preparedStatement.setInt(4, entity.getAge());
            preparedStatement.setString(5, entity.getSex().toString());
            preparedStatement.setLong(6, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(DELETE_PERSON_QUERY + id)) {
            PreparedStatement preparedStatement2 = jpaConfig.getConnection().prepareStatement(DELETE_RELATION_BY_PERSON_ID_QUERY + id);
            preparedStatement2.executeUpdate();
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public boolean existById(Long id) {
        return false;
    }

    public boolean existById(String passportId) {
        passportId = String.valueOf("'") + passportId + String.valueOf("'");
        int count = 0;
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(EXIST_PERSON_BY_PASSPORT_ID_QUERY + passportId)) {
            if (resultSet.next()) {
                count = resultSet.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count == 1;
    }

    @Override
    public void removeRelation(String countryName, String personPassportId) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(DELETE_RELATION_BY_PERSON_ID_AND_COUNTRY_ID_QUERY)) {
            preparedStatement.setLong(1, findCountryIdByCountryName(countryName));
            preparedStatement.setLong(2, findPersonIdByPassportId(personPassportId));
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
    public DataTableResponse<Population> findAllNotVisible(DataTableRequest dataTableRequest) {
        List<Population> people = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();

        int limit = (dataTableRequest.getCurrentPage() - 1) * dataTableRequest.getPageSize();

        String sql = "select id, first_name, last_name, age, sex, passport_id, visible, count(person_id) as countryCount " +
                "from population as people left join country_person as cp on people.id = cp.person_id " +
                "where visible = false group by people.id order by " +
                dataTableRequest.getSort() + " " +
                dataTableRequest.getOrder() + " limit " +
                limit + "," +
                dataTableRequest.getPageSize();

        System.out.println(sql);

        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(sql)) {
            while (resultSet.next()) {
                PopulationResultSet populationResultSet = convertResultSetToSimpleAuthor(resultSet);
                System.out.println(populationResultSet.person + String.valueOf(populationResultSet.person.getVisible()));
                people.add(populationResultSet.getPerson());
                otherParamMap.put(populationResultSet.getPerson().getId(), populationResultSet.getCountryCount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DataTableResponse<Population> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(people);
        tableResponse.setOtherParamMap(otherParamMap);
        return tableResponse;
    }

    @Override
    public long notVisibleCount() {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery("select count(*) as count from population where visible = false")) {
            while (resultSet.next()) {
                return resultSet.getLong("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Population findById(Long id) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_PERSON_BY_ID_QUERY + id)) {
            while (resultSet.next()) {
                return initPersonByResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DataTableResponse<Population> findAll(DataTableRequest request) {
        List<Population> people = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();

        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        String sql = "select id, first_name, last_name, age, sex, passport_id, visible, count(person_id) as countryCount " +
                "from population as people left join country_person as cp on people.id = cp.person_id " +
                "where visible = true group by people.id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();

        System.out.println(sql);

        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(sql)) {
            while (resultSet.next()) {
                PopulationResultSet populationResultSet = convertResultSetToSimpleAuthor(resultSet);
                System.out.println(populationResultSet.person + String.valueOf(populationResultSet.person.getVisible()));
                people.add(populationResultSet.getPerson());
                otherParamMap.put(populationResultSet.getPerson().getId(), populationResultSet.getCountryCount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DataTableResponse<Population> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(people);
        tableResponse.setOtherParamMap(otherParamMap);
        return tableResponse;
    }

    @Override
    public long count() {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery("select count(*) as count from population where visible = true")) {
            while (resultSet.next()) {
                return resultSet.getLong("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private PopulationResultSet convertResultSetToSimpleAuthor(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String passportID = resultSet.getString("passport_id");
        int age = resultSet.getInt("age");
        String sex = resultSet.getString("sex");
        int countryCount = resultSet.getInt("countryCount");
        boolean visible = resultSet.getBoolean("visible");

        Population person = new Population();
        person.setId(id);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setPassportID(passportID);
        person.setAge(age);
        person.setSex(Sex.valueOf(sex));
        person.setVisible(visible);

        return new PopulationResultSet(person, countryCount);
    }

    private Population initPersonByResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        Timestamp created = resultSet.getTimestamp("created");
        Timestamp updated = resultSet.getTimestamp("updated");
        Boolean visible = resultSet.getBoolean("visible");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String sex = resultSet.getString("sex");
        String passportId = resultSet.getString("passport_id");
        int age = resultSet.getInt("age");

        Population person = new Population();
        person.setId(id);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAge(age);
        person.setSex(Sex.valueOf(sex));
        person.setPassportID(passportId);

        return person;
    }

    @Override
    public Map<Long, String> findByCountryId(Long countryId) {
        Map<Long, String> map = new HashMap<>();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_PERSON_BY_COUNTRY_ID_QUERY + countryId)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                map.put(id, firstName + " " + lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void createRelation(String countryName, String personPassportId) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_RELATION_QUERY)) {
            preparedStatement.setLong(1, findCountryIdByCountryName(countryName));
            preparedStatement.setLong(2, findPersonIdByPassportId(personPassportId));
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public void addRelation(String countryName, String personPassportId) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_RELATION_QUERY)) {
            preparedStatement.setLong(1, findCountryIdByCountryName(countryName));
            preparedStatement.setLong(2, findPersonIdByPassportId(personPassportId));
            preparedStatement.executeUpdate();

            Population person = findPersonByPassportId(personPassportId);
            PreparedStatement preparedStatement1 = jpaConfig.getConnection().prepareStatement(UPDATE_PERSON_VISIBLE_QUERY);
            preparedStatement1.setTimestamp(1, new Timestamp(person.getUpdated().getTime()));
            preparedStatement1.setString(2, personPassportId);
            preparedStatement1.executeUpdate();
        } catch (SQLException e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    public Population findPersonByPassportId(String passportId) {
        Population person = new Population();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_PERSON_BY_PASSPORT_ID_QUERY + passportId)) {
            while (resultSet.next()) {
                person.setId(resultSet.getLong("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setVisible(resultSet.getBoolean("visible"));
                person.setAge(resultSet.getInt("age"));
                person.setPassportID(resultSet.getString("passport_id"));
                person.setCreated(resultSet.getDate("created"));
                person.setUpdated(resultSet.getDate("updated"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }


    public long findCountryIdByCountryName(String countryName) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery("select country_name, id from countries")) {
            while (resultSet.next()) {
                if (countryName.equals(resultSet.getString("country_name"))) {
                    return resultSet.getLong("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long findPersonIdByPassportId(String personPassportId) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery("select passport_id, id from population")) {
            while (resultSet.next()) {
                if (personPassportId.equals(resultSet.getString("passport_id"))) {
                    return resultSet.getLong("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static class PopulationResultSet {

        private final Population person;
        private final int countryCount;

        private PopulationResultSet(Population person, int countryCount) {
            this.person = person;
            this.countryCount = countryCount;
        }

        public Population getPerson() {
            return person;
        }

        public int getCountryCount() {
            return countryCount;
        }
    }
}
