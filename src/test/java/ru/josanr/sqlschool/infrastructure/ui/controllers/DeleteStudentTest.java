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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteStudentTest {
    private StudentService studentService;
    private BufferedReader input;
    private PrintStream output;

    @BeforeEach
    void setUp() {
        studentService = Mockito.mock(StudentService.class);
        input = Mockito.mock(BufferedReader.class);
        output = Mockito.mock(PrintStream.class);

    }

    @Test
    void run_shouldDeleteStudent_givenIdAsInput() {
        try{
            DeleteStudent deleteStudent = new DeleteStudent(studentService, input, output);
            when(input.readLine()).thenReturn("1");
            deleteStudent.run();
            verify(studentService, times(1)).remove(1);
        } catch (IOException e){
            Assertions.fail();
        }
    }
}
