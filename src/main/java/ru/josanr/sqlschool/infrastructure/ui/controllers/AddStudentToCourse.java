package ru.josanr.sqlschool.infrastructure.ui.controllers;

import ru.josanr.sqlschool.domain.services.CoursesService;
import ru.josanr.sqlschool.domain.services.StudentService;
import ru.josanr.sqlschool.infrastructure.dao.exceptions.StorageException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class AddStudentToCourse implements Controller {

    private final StudentService studentService;
    private final CoursesService coursesService;
    private final BufferedReader input;
    private final PrintStream output;

    public AddStudentToCourse(
        StudentService studentService,
        CoursesService coursesService,
        BufferedReader input,
        PrintStream output
    ) {
        this.studentService = studentService;
        this.coursesService = coursesService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() throws IOException {
        var students = studentService.findAll();
        var courses = coursesService.findAll();

        output.println("Id - Students\n------------------------");
        for (var student : students) {
            output.println(student.getId() + " - " + student.getFullName());
        }
        output.println("----------------");
        output.println("Id - Course\n------------------------");
        for (var course : courses) {
            output.println(course.getId() + " - " + course.getName());
        }

        output.print("student id: ");
        var studentIdInput = input.readLine();
        output.print("Course id: ");
        var courseIdInput = input.readLine();

        try {
            var studentId = Long.parseLong(studentIdInput);
            var courseId = Long.parseLong(courseIdInput);
            coursesService.addStudentToCourse(courseId, studentId);
        } catch (NumberFormatException e) {
            output.println("Student Id or Course Id not number.");
        } catch (StorageException e) {
            output.println("Error adding Student to course");
        }
    }
}
