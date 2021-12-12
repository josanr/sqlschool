package ru.josanr.sqlschool.domain.entities;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private final long id;
    private final String name;
    private final List<Student> studentList;

    public Group(long id, String name) {
        this.id = id;
        this.name = name;
        this.studentList = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }
}
