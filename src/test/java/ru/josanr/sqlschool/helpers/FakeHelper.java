package ru.josanr.sqlschool.helpers;

import com.github.javafaker.Faker;
import ru.josanr.sqlschool.domain.entities.Course;
import ru.josanr.sqlschool.domain.entities.Student;

public class FakeHelper {

    private final Faker faker;

    public FakeHelper() {
        faker = new Faker();
    }

    public String word() {
        return faker.lorem().word();
    }

    public String description() {
        return faker.lorem().words().toString();
    }

    public Integer id() {
        return faker.number().randomDigit();
    }

    public String name() {
        return faker.name().fullName();
    }

    public Student student() {
        return new Student(this.id(), this.word(), this.word());
    }

    public Course course() {
        return new Course(this.id(), this.word(), this.description());
    }
}
