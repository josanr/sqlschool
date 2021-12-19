package ru.josanr.sqlschool.application.commands;

import java.io.IOException;

public interface Command {

    void execute() throws IOException;
}
