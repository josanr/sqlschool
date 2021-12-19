package ru.josanr.sqlschool.application.commands;

import ru.josanr.sqlschool.domain.services.CoursesService;
import ru.josanr.sqlschool.domain.services.StudentService;
import ru.josanr.sqlschool.infrastructure.exceptions.StorageException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class RemoveStudentFromCourse implements Command {

    private final StudentService studentService;
    private final CoursesService coursesService;
    private final BufferedReader input;
    private final PrintStream output;

    public RemoveStudentFromCourse(
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
    public void execute() throws IOException {
        var students = studentService.findAll();

        output.println("Id - Students\n------------------------");
        for (var student : students) {
            output.println(student.getId() + " - " + student.getFullName());
        }

        try {
            output.print("student id: ");
            var studentIdInput = input.readLine();
            var studentId = Integer.parseInt(studentIdInput);
            var student = studentService.findById(studentId);
            output.println("id - Student Courses\n----------------------");
            for (var course : student.getCourseList()) {
                output.println(course.getId() + " - " + course.getName());
            }
            output.print("course id: ");
            var courseIdInput = input.readLine();
            var courseId = Integer.parseInt(courseIdInput);
            coursesService.removeStudentFromCourse(courseId, studentId);

        } catch (NumberFormatException e) {
            output.println("Student Id or Course Id not number.");
        } catch (StorageException e) {
            output.println("Error adding Student to course");
        }
    }
}
