package ru.josanr.sqlschool.infrastructure.dao;

import ru.josanr.sqlschool.domain.entities.Student;

import java.util.List;

public interface StudentsRepository {

    List<Student> findByCourseName(String courseName);

    Student create(Student student);

    Student update(Student student);

    void remove(Student student);

    Student getById(Integer studentId);

    List<Student> findAll();
}
