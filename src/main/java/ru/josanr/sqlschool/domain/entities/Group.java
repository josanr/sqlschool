package ru.josanr.sqlschool.domain.entities;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private final String name;
    private final List<Student> studentList;

    private Integer id;

    public Group(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.studentList = new ArrayList<>();
    }

    public Group(String name) {
        this.name = name;
        this.studentList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }
}
