package ru.josanr.sqlschool.infrastructure.ui;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.josanr.sqlschool.domain.services.CoursesService;
import ru.josanr.sqlschool.domain.services.GroupsService;
import ru.josanr.sqlschool.domain.services.StudentService;
import ru.josanr.sqlschool.infrastructure.ui.controllers.Controller;

import java.io.BufferedReader;
import java.io.PrintStream;

class ControllerFactoryTest {

    private ControllerFactory controllerFactory;

    @BeforeEach
    void setUp() {
        GroupsService groupsService = Mockito.mock(GroupsService.class);
        CoursesService coursesService = Mockito.mock(CoursesService.class);
        StudentService studentService = Mockito.mock(StudentService.class);
        BufferedReader input = Mockito.mock(BufferedReader.class);
        PrintStream output = Mockito.mock(PrintStream.class);

        controllerFactory = new ControllerFactory(groupsService,
            coursesService,
            studentService,
            input,
            output);
    }

    @Test
    void getController_shouldReturnController_givenStringOption() {
        Controller controller = controllerFactory.getController("a");
        MatcherAssert.assertThat(controller, Matchers.notNullValue());
    }

    @Test
    void getController_shouldThrowException_givenStringOptionWhichDoesNotExist() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            controllerFactory.getController("y");
        });
    }
}
