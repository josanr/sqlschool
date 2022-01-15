package ru.josanr.sqlschool.infrastructure.ui.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.josanr.sqlschool.domain.services.CoursesService;
import ru.josanr.sqlschool.domain.services.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddStudentToCourseTest {
    private StudentService studentService;
    private BufferedReader input;
    private PrintStream output;
    private CoursesService coursesService;

    @BeforeEach
    void setUp() {
        coursesService = Mockito.mock(CoursesService.class);
        studentService = Mockito.mock(StudentService.class);
        input = Mockito.mock(BufferedReader.class);
        output = Mockito.mock(PrintStream.class);

    }

    @Test
    void run_ShouldAddStudentToCourse_givenStudentAndCourseId() {
        try{
            AddStudentToCourse addStudentToCourse = new AddStudentToCourse(studentService, coursesService, input, output);
            when(input.readLine()).thenReturn("1", "2");
            addStudentToCourse.run();
            verify(coursesService, times(1)).addStudentToCourse(2, 1);
        } catch (IOException e){
            Assertions.fail();
        }
    }
}
