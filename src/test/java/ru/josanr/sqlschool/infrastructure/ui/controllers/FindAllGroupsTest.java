package ru.josanr.sqlschool.infrastructure.ui.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.josanr.sqlschool.domain.services.CoursesService;
import ru.josanr.sqlschool.domain.services.GroupsService;
import ru.josanr.sqlschool.domain.services.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindAllGroupsTest {

    private BufferedReader input;
    private PrintStream output;
    private GroupsService groupsService;

    @BeforeEach
    void setUp() {
        groupsService = Mockito.mock(GroupsService.class);
        input = Mockito.mock(BufferedReader.class);
        output = Mockito.mock(PrintStream.class);

    }

    @Test
    void run() {
        try{
            FindAllGroups findAllGroups = new FindAllGroups(groupsService, input, output);
            when(input.readLine()).thenReturn("1");
            when(groupsService.findByStudentCount(1)).thenReturn(List.of("1 - group_name"));
            findAllGroups.run();
            verify(groupsService, times(1)).findByStudentCount(1);
            verify(output, times(1)).println("id - Name\n-------------");
            verify(output, times(1)).println("1 - group_name");
        } catch (IOException e){
            Assertions.fail();
        }
    }
}
