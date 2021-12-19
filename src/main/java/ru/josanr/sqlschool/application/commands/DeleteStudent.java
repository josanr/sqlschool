package ru.josanr.sqlschool.application.commands;

import ru.josanr.sqlschool.domain.services.StudentService;
import ru.josanr.sqlschool.infrastructure.exceptions.StorageException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class DeleteStudent implements Command {

    private final StudentService studentService;
    private final BufferedReader input;
    private final PrintStream output;

    public DeleteStudent(StudentService studentService, BufferedReader input, PrintStream output) {
        this.studentService = studentService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void execute() throws IOException {
        output.print("student id: ");
        var studentIdInput = input.readLine();
        try {
            var studentId = Integer.parseInt(studentIdInput);
            studentService.remove(studentId);

        } catch (NumberFormatException e) {
            output.println(studentIdInput + " is not a number");
        } catch (StorageException e) {
            output.println("Could Not Delete student with id: " + studentIdInput);
        }
    }
}
