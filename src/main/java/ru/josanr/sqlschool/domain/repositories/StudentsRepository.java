package ru.josanr.sqlschool.domain.repositories;

import ru.josanr.sqlschool.domain.entities.Student;

import java.util.List;

public interface StudentsRepository {

    List<Student> findByCourseName(String courseName);

    Student add(Student student);

    void remove(Student student);

    Student getById(Integer studentId);

    List<Student> findAll();
}
