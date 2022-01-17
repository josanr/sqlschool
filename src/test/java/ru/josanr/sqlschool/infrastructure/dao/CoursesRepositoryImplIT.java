package ru.josanr.sqlschool.infrastructure.dao;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.josanr.sqlschool.domain.entities.Course;
import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.helpers.DbHelper;
import ru.josanr.sqlschool.infrastructure.dao.impl.CoursesRepositoryImpl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

@DBRider
@Tag("IntegrationTests")
@DataSet("sqlschool.json")
class CoursesRepositoryImplIT {


    private final CoursesRepository repo;

    public CoursesRepositoryImplIT() {
        DbHelper dbHelper = new DbHelper();
        dbHelper.resetSequence();
        repo = new CoursesRepositoryImpl(dbHelper.getDataSource());
    }

    @Test
    void findByStudent_shouldReturnCoursesLinkedToStudent_givenStudent() {
        Student student = new Student(1, "", "");
        var list = repo.findByStudent(student);
        assertThat(list, hasSize(3));
    }

    @Test
    void findAll_shouldReturnAllCourses() {
        var list = repo.findAll();
        assertThat(list, hasSize(10));
    }

    @Test
    void getById_shouldReturnSingleCourse_givenIntId() {
        Course course = repo.getById(1);
        assertThat(course.getName(), equalTo("Agriculture"));
        assertThat(course.getId(), equalTo(1));
    }

    @Test
    @DataSet("students_courses.json")
    void addStudentToCourse_shouldAddCourseToStudent_givenStudentAndCourse() {
        var course = new Course(1, "", "");
        var student = new Student(5, "", "");
        repo.addStudentToCourse(course, student);

        List<Course> list = repo.findByStudent(student);

        assertThat(list, hasSize(2));

    }

    @Test
    @DataSet("students_courses.json")
    void removeStudentFromCourse_shouldRemoveCourseFromStudent_givenStudentAndCourse() {
        var course = new Course(5, "", "");
        var student = new Student(1, "", "");
        repo.removeStudentFromCourse(course, student);

        List<Course> list = repo.findByStudent(student);

        assertThat(list, hasSize(2));
    }

    @Test
    void create_shouldCreateCourse_givenCourseData() {
        var course = new Course( "Apt", "New Apt");
        Course newCourse = repo.create(course);
        assertThat(newCourse, not(course));
        assertThat(newCourse.getId(), notNullValue());
    }
}
