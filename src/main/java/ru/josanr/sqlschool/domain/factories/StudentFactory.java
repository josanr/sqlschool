package ru.josanr.sqlschool.domain.factories;

import ru.josanr.sqlschool.domain.entities.Student;

public class StudentFactory {

    public Student create(String firstName, String lastName) {
        return new Student(firstName, lastName);
    }
}
