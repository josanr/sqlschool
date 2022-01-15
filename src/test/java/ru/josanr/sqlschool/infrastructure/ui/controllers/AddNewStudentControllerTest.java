package ru.josanr.sqlschool.infrastructure.ui.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.domain.services.CoursesService;
import ru.josanr.sqlschool.domain.services.GroupsService;
import ru.josanr.sqlschool.domain.services.StudentService;
import ru.josanr.sqlschool.infrastructure.ui.ControllerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddNewStudentControllerTest {

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
    void run_shouldReturnTextWithID_givenStudentParams() {
        try {
            AddNewStudent addNewStudent = new AddNewStudent(studentService, input, output);
            String first_name = "first_name";
            String second_name = "second_name";
            when(input.readLine()).thenReturn(first_name, second_name);
            when(studentService.createStudent(first_name, second_name)).thenReturn(new Student(1, first_name, second_name));
            addNewStudent.run();
            verify(output, times(1)).println("New student created with id: 1");
        } catch (IOException e){
            Assertions.fail();
        }
    }
}
