package ru.josanr.sqlschool.infrastructure.ui.controllers;

import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.domain.services.CoursesService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class FindStudentsRelatedToCourse implements Controller {

    private final CoursesService coursesService;
    private final BufferedReader input;
    private final PrintStream output;

    public FindStudentsRelatedToCourse(CoursesService coursesService, BufferedReader input, PrintStream output) {
        this.coursesService = coursesService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() throws IOException {
        output.print("Course name: ");
        var courseName = input.readLine();
        List<Student> students = coursesService.listCourseStudents(courseName);
        output.println("Name\n-------------");
        for (var student : students) {
            output.println(student.getFullName());
        }
    }
}
