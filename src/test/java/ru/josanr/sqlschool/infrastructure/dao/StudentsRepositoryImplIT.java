package ru.josanr.sqlschool.infrastructure.dao;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.helpers.DbHelper;
import ru.josanr.sqlschool.infrastructure.dao.exceptions.StorageException;
import ru.josanr.sqlschool.infrastructure.dao.impl.CoursesRepositoryImpl;
import ru.josanr.sqlschool.infrastructure.dao.impl.GroupRepositoryImpl;
import ru.josanr.sqlschool.infrastructure.dao.impl.StudentsRepositoryImpl;

import javax.sql.DataSource;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DBRider
@Tag("IntegrationTests")
class StudentsRepositoryImplIT {

    private final DbHelper dbHelper;
    private StudentsRepositoryImpl studentsRepository;
    private final String lastName = "test_last_name";
    private final String name = "test_name";

    public StudentsRepositoryImplIT() {
        this.dbHelper = new DbHelper();
        dbHelper.resetSequence();
    }

    @DataSet("sqlschool.json")
    @BeforeEach
    void setUp() {
        DataSource dataSource = dbHelper.getDataSource();
        GroupRepository groupRepo = new GroupRepositoryImpl(dataSource);
        CoursesRepositoryImpl coursesRepo = new CoursesRepositoryImpl(dataSource);
        studentsRepository = new StudentsRepositoryImpl(dataSource, groupRepo, coursesRepo);
    }

    @Test
    void findByCourseName_shouldReturnAllStudentsAssignedToCourse_givenCourseName() {
        List<Student> courseStudents = studentsRepository.findByCourseName("Earth science");
        assertThat(courseStudents.size(), is(3));
    }

    @Test
    void update_ShouldUpdateStudent_givenInput() {
        Student newStudent = new Student(2, name, lastName);

        studentsRepository.update(newStudent);
        Student updatedUser = studentsRepository.getById(2);
        assertThat(updatedUser.getLastName(), is(lastName));
        assertThat(updatedUser.getFirstName(), is(name));
    }

    @Test
    void create_shouldStoreANewStudent_givenStudentModel() {

        Student newStudent = new Student(name, lastName);

        Student student = studentsRepository.create(newStudent);
        assertThat(student.getId(), notNullValue());
        assertThat(student.getLastName(), is(lastName));
        assertThat(student.getFirstName(), is(name));

    }

    @Test
    void remove() {
        Student student = new Student(1, "", "");
        studentsRepository.remove(student);

        assertThrows(StorageException.class,
            ()->{
                Student stored = studentsRepository.getById(1);
            });
    }

    @Test
    void getById() {
        Student student = studentsRepository.getById(1);
        assertThat(student.getId(), is(1));
        assertThat(student.getFirstName(), is("Zachary"));
        assertThat(student.getLastName(), is("Pollich"));
    }

    @Test
    void findAll() {
        List<Student> studentList = studentsRepository.findAll();
        assertThat(studentList.size(), is(20));
    }
}
