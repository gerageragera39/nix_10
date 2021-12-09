package ua.com.alevel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.alevel.persistence.dao.CountriesDao;
import ua.com.alevel.persistence.dao.PopulationDao;
import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.persistence.entity.Population;
import ua.com.alevel.persistence.sex.Sex;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class Hw9WebJpaApplicationTests {

    @Autowired
    private PopulationDao populationDao;

    @Autowired
    private CountriesDao countriesDao;

    @Test
    void contextLoads() {
    }

    @Test
    void crud() {
        for (int i = 0; i < 5; i++) {
            Population person = new Population();
            person.setFirstName("fn" + (i + 1));
            person.setLastName("ln1" + (i + 1));
            person.setAge(i + 1);
            person.setSex(Sex.F);
            person.setPassportID(String.valueOf(i+1));
            populationDao.create(person);
        }

//        for (int i = 0; i < 5; i++) {
//            Countries countries = new Countries();
//            countries.setNameOfCountry("name"+(i+1));
//            countries.setISO(i*12);
//            countriesDao.create(countries);
//        }

//        Department department = departmentDao.findById(2L);
//        Assertions.assertNotNull(department);
//        department.setDepartmentType(DepartmentType.JAVA);
//        departmentDao.update(department);
//        department = departmentDao.findById(2L);
//        Assertions.assertEquals(department.getDepartmentType(), DepartmentType.JAVA);
//
////        departmentDao.delete(3L);
////        boolean dell = departmentDao.existById(3L);
////        Assertions.assertFalse(dell);
////
////        department = departmentDao.findById(4L);
////        departmentDao.delete(department);
////        Assertions.assertFalse(departmentDao.existById(4L));
//
//        List<Department> departments = departmentDao.findAll(1, 5, "name", "desc");
//        Assertions.assertNotNull(departments);
//        Assertions.assertEquals(5, departments.size());
//
//        department = departmentDao.findById(5L);
//        Employee employee = new Employee();
//        employee.setAge(35);
//        employee.setFirstName("name31");
//        employee.setLastName("name32");
//        department.addEmployee(employee);
//        employee = new Employee();
//        employee.setAge(25);
//        employee.setFirstName("name41");
//        employee.setLastName("name42");
//        department.addEmployee(employee);
//
//        departmentDao.update(department);
    }

    @Test
    void addPerson(){
        System.out.println(countriesDao.findById(20L).toString());
    }

    @Test
    void initRelations(){
        countriesDao.findById(20L).addPerson(populationDao.findById(1L));
    }
}
