package ru.josanr.sqlschool.domain.repositories;

import ru.josanr.sqlschool.domain.entities.Course;
import ru.josanr.sqlschool.domain.entities.Student;

import java.util.List;

public interface CoursesRepository {

    List<Course> findByName(String courseName);

    Course getById(Integer id);

    void addStudentToCourse(Course course, Student student);

    void removeStudentFromCourse(Course course, Student student);

    Course add(Course course);

    List<Course> findByStudent(Student student);

    List<Course> findAll();
}
