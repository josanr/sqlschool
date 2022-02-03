package ru.josanr.sqlschool.application;

import ru.josanr.sqlschool.infrastructure.ui.ControllerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class App {

    private final ControllerFactory factory;
    private final BufferedReader input;
    private final PrintStream output;

    public App(ControllerFactory factory, BufferedReader input, PrintStream output) {
        this.factory = factory;
        this.input = input;
        this.output = output;
    }

    public void run() {
        while (true) {
            printMenu();
            printCommandWait();
            try {
                String command = input.readLine();
                if ("exit".equalsIgnoreCase(command)) {
                    output.println("quiting");
                    return;
                }

                this.execute(command);

                output.println("Press Enter key to continue...");
                input.readLine();

            } catch (Exception e) {
                output.println("Application error: " + e.getMessage());
                e.printStackTrace();
                return;
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
            factory.getController(command).run();
        } catch (RuntimeException e) {
            output.println(e.getMessage());
        }
    }
}
