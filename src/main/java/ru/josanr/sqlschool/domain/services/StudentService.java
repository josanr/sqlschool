package ru.josanr.sqlschool.domain.services;

import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.domain.factories.StudentFactory;
import ru.josanr.sqlschool.domain.repositories.StudentsRepository;

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
        repository.remove(id);
    }
}
