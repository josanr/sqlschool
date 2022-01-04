package ru.josanr.sqlschool.infrastructure.dao;

import ru.josanr.sqlschool.domain.entities.Course;
import ru.josanr.sqlschool.domain.entities.Student;

import java.util.List;

public interface CoursesRepository {

    Course create(Course course);

    Course getById(Integer id);

    void addStudentToCourse(Course course, Student student);

    void removeStudentFromCourse(Course course, Student student);

    List<Course> findByStudent(Student student);

    List<Course> findAll();
}
