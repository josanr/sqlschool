package ru.josanr.sqlschool.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class App {


    private final CommandFactory factory;
    private final BufferedReader input;
    private final PrintStream output;

    public App(
        CommandFactory factory,
        BufferedReader input,
        PrintStream output
    ) {
        this.factory = factory;

        this.input = input;
        this.output = output;
    }

    public void run() {
        while (true) {
            this.printMenu();
            this.printCommandWait();
            try {
                String command = input.readLine();
                if ("exit".equalsIgnoreCase(command)) {
                    output.println("quiting");
                    break;
                }

                this.execute(command);

            } catch (Exception e) {
                output.println("Application error: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }

    private void printCommandWait() {
        output.print("Select menu item (exit to quit): ");
    }

    private void printMenu() {
        output.println(
            "==================================\n" +
                "a. Find all groups with less or equals student count\n" +
                "b. Find all students related to course with given name\n" +
                "c. Add new student\n" +
                "d. Delete student by STUDENT_ID\n" +
                "e. Add a student to the course (from a list)\n" +
                "f. Remove the student from one of his or her courses\n" +
                "==================================\n"
        );
    }

    private void execute(String command) throws IOException {
        try {
            factory.get(command).execute();
        } catch (RuntimeException e) {
            output.println(e.getMessage());
        }
        output.println("Press Enter key to continue...");
        input.readLine();
    }
}
