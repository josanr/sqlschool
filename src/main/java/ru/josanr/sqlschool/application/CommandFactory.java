package ru.josanr.sqlschool.application;

import ru.josanr.sqlschool.application.commands.AddNewStudent;
import ru.josanr.sqlschool.application.commands.AddStudentToCourse;
import ru.josanr.sqlschool.application.commands.Command;
import ru.josanr.sqlschool.application.commands.DeleteStudent;
import ru.josanr.sqlschool.application.commands.FindAllGroups;
import ru.josanr.sqlschool.application.commands.FindStudentsRelatedToCourse;
import ru.josanr.sqlschool.application.commands.RemoveStudentFromCourse;
import ru.josanr.sqlschool.domain.services.CoursesService;
import ru.josanr.sqlschool.domain.services.GroupsService;
import ru.josanr.sqlschool.domain.services.StudentService;

import java.io.BufferedReader;
import java.io.PrintStream;

public class CommandFactory {

    private final GroupsService groupsService;
    private final CoursesService coursesService;
    private final StudentService studentService;
    private final BufferedReader input;
    private final PrintStream output;

    public CommandFactory(
        GroupsService groupsService,
        CoursesService coursesService,
        StudentService studentService,
        BufferedReader input,
        PrintStream output
    ) {
        this.groupsService = groupsService;
        this.coursesService = coursesService;
        this.studentService = studentService;
        this.input = input;
        this.output = output;
    }

    Command get(String command) {
        switch (command) {
            case "a":
                return new FindAllGroups(
                    groupsService,
                    input,
                    output
                );
            case "b":
                return new FindStudentsRelatedToCourse(
                    coursesService,
                    input,
                    output
                );
            case "c":
                return new AddNewStudent(
                    studentService,
                    input,
                    output
                );
            case "d":
                return new DeleteStudent(
                    studentService,
                    input,
                    output
                );
            case "e":
                return new AddStudentToCourse(
                    studentService,
                    coursesService,
                    input,
                    output
                );
            case "f":
                return new RemoveStudentFromCourse(
                    studentService,
                    coursesService,
                    input,
                    output
                );
            default:
                throw new IllegalStateException("Command not implemented: " + command);
        }
    }
}
