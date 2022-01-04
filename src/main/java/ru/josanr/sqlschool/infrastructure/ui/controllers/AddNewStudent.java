package ru.josanr.sqlschool.infrastructure.ui.controllers;

import ru.josanr.sqlschool.domain.services.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class AddNewStudent implements Controller {

    private final StudentService studentService;
    private final BufferedReader input;
    private final PrintStream output;

    public AddNewStudent(StudentService studentService, BufferedReader input, PrintStream output) {

        this.studentService = studentService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() throws IOException {
        output.print("Student first name: ");
        var firstName = input.readLine();
        output.print("Student second name: ");
        var secondName = input.readLine();
        var student = studentService.createStudent(firstName, secondName);

        output.println("New student created with id: " + student.getId());
    }
}
