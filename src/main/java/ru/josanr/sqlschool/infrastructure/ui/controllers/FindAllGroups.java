package ru.josanr.sqlschool.infrastructure.ui.controllers;

import ru.josanr.sqlschool.domain.services.GroupsService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class FindAllGroups implements Controller {

    private final GroupsService groupsService;
    private final BufferedReader input;
    private final PrintStream output;

    public FindAllGroups(GroupsService groupsService, BufferedReader input, PrintStream output) {
        this.groupsService = groupsService;

        this.input = input;
        this.output = output;
    }

    @Override
    public void run() throws IOException {
        output.print("number of students: ");
        var numberOfStudentsInput = input.readLine();
        try {
            var numberOfStudents = Integer.parseInt(numberOfStudentsInput);
            var list = groupsService.findByStudentCount(numberOfStudents);
            output.println("id - Name\n-------------");
            for (var group : list) {
                output.println(group);
            }
        } catch (NumberFormatException e) {
            output.println(numberOfStudentsInput + " is not a number");
        }

    }
}
