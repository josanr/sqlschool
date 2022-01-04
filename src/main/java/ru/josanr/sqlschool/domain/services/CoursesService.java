package ru.josanr.sqlschool.domain.services;

import ru.josanr.sqlschool.domain.entities.Course;
import ru.josanr.sqlschool.domain.entities.Student;

import java.util.List;

public interface CoursesService {

    List<Student> listCourseStudents(String courseName);

    void addStudentToCourse(Integer courseId, Integer studentId);

    void removeStudentFromCourse(Integer courseId, Integer studentId);

    List<Course> findAll();
}
