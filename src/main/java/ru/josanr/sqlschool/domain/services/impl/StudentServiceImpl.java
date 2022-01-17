package ru.josanr.sqlschool.domain.services.impl;

import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.domain.factories.StudentFactory;
import ru.josanr.sqlschool.domain.services.StudentService;
import ru.josanr.sqlschool.infrastructure.dao.StudentsRepository;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    private final StudentsRepository repository;
    private final StudentFactory factory;

    public StudentServiceImpl(StudentsRepository repository, StudentFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public Student createStudent(String firstName, String lastName) {
        var student = factory.create(firstName, lastName);
        return repository.create(student);
    }

    @Override
    public void remove(Integer id) {
        var student = repository.getById(id);
        repository.remove(student);
    }

    @Override
    public List<Student> findAll() {
        return repository.findAll();
    }

    @Override
    public Student findById(int studentId) {
        return repository.getById(studentId);
    }
}
