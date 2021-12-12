package ru.josanr.sqlschool.domain.entities;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private final List<Course> courseList = new ArrayList<>();
    private final String firstName;
    private final String lastName;
    private Integer id;

    private Group group;

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public Group getGroup() {
        return group;
    }

    public void addCourse(Course course) {
        courseList.add(course);
    }
}
