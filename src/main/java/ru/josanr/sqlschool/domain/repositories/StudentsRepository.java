package ru.josanr.sqlschool.domain.repositories;

import ru.josanr.sqlschool.domain.entities.Student;

import java.util.List;

public interface StudentsRepository {

    List<Student> findByCourseName(String courseName);

    Student add(Student student);

    void remove(Integer id);

    Student getById(Integer studentId);

    void save(Student student);
}
