package ru.josanr.sqlschool.domain.services;

import ru.josanr.sqlschool.domain.entities.Student;

import java.util.List;

public interface StudentService {

    Student createStudent(String firstName, String lastName);

    void remove(Long id);

    List<Student> findAll();

    Student findById(Long studentId);
}
