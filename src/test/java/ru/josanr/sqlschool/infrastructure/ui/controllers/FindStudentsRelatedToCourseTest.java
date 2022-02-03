package ru.josanr.sqlschool.infrastructure.ui.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.domain.services.CoursesService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindStudentsRelatedToCourseTest {

    private BufferedReader input;
    private PrintStream output;
    private CoursesService coursesService;

    @BeforeEach
    void setUp() {
        coursesService = Mockito.mock(CoursesService.class);
        input = Mockito.mock(BufferedReader.class);
        output = Mockito.mock(PrintStream.class);

    }

    @Test
    void run_ShouldReturnListOfStudentsOfCourse_givenCourseName() {
        try{
            FindStudentsRelatedToCourse controller = new FindStudentsRelatedToCourse(coursesService, input, output);
            String courseName = "test_course";
            String firstName = "test_First_name";
            String lastName = "test_Last_name";

            when(input.readLine()).thenReturn(courseName);
            when(coursesService.listCourseStudents(courseName)).thenReturn(List.of(new Student(1L, firstName, lastName)));
            controller.run();
            verify(coursesService, times(1)).listCourseStudents(courseName);
            verify(output, times(1)).println("Name\n-------------");
            verify(output, times(1)).println(firstName + " " + lastName);
        } catch (IOException e){
            Assertions.fail();
        }
    }
}
