package ru.josanr.sqlschool.infrastructure.ui.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.josanr.sqlschool.domain.entities.Student;
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

class RemoveStudentFromCourseTest {

    private StudentService studentService;
    private BufferedReader input;
    private PrintStream output;
    private CoursesService coursesService;
    private GroupsService groupsService;

    @BeforeEach
    void setUp() {
        groupsService = Mockito.mock(GroupsService.class);
        coursesService = Mockito.mock(CoursesService.class);
        studentService = Mockito.mock(StudentService.class);
        input = Mockito.mock(BufferedReader.class);
        output = Mockito.mock(PrintStream.class);

    }

    @Test
    void run() {
        try{
            RemoveStudentFromCourse controller = new RemoveStudentFromCourse(studentService, coursesService, input, output);
            Integer studentId = 1;
            Integer courseId = 2;
            when(input.readLine()).thenReturn(String.valueOf(studentId), String.valueOf(courseId));
            when(studentService.findById(studentId)).thenReturn(new Student(1, "", ""));
            controller.run();
            verify(coursesService, times(1)).removeStudentFromCourse(courseId, studentId);
            verify(output, times(1)).print("student id: ");
            verify(output, times(1)).println("id - Student Courses\n----------------------");
            verify(output, times(1)).print("course id: ");
        } catch (IOException e){
            Assertions.fail();
        }
    }
}
