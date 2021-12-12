package ru.josanr.sqlschool.domain.entities;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private final Integer id;
    private final String name;
    private final String description;
    private final List<Student> studentList;

    public Course(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        studentList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }

    public List<Student> getStudents() {
        return new ArrayList<>(this.studentList);
    }
}
