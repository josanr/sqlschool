package ru.josanr.sqlschool.domain.services;

import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.domain.factories.StudentFactory;
import ru.josanr.sqlschool.domain.repositories.StudentsRepository;

import java.util.List;

public class StudentService {

    private final StudentsRepository repository;
    private final StudentFactory factory;

    public StudentService(StudentsRepository repository, StudentFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    public Student createStudent(String firstName, String lastName) {
        var student = factory.create(firstName, lastName);
        return repository.add(student);
    }

    public void remove(Integer id) {
        var student = repository.getById(id);
        repository.remove(student);
    }

    public List<Student> findAll() {
        return repository.findAll();
    }

    public Student findById(int studentId) {
        return repository.getById(studentId);
    }
}
