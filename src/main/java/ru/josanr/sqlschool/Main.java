package ru.josanr.sqlschool;

import com.github.javafaker.Faker;
import ru.josanr.sqlschool.application.App;
import ru.josanr.sqlschool.application.AppInitializer;
import ru.josanr.sqlschool.application.CommandFactory;
import ru.josanr.sqlschool.domain.factories.StudentFactory;
import ru.josanr.sqlschool.domain.services.CoursesService;
import ru.josanr.sqlschool.domain.services.GroupsService;
import ru.josanr.sqlschool.domain.services.StudentService;
import ru.josanr.sqlschool.infrastructure.ConnectionSource;
import ru.josanr.sqlschool.infrastructure.CoursesRepositoryImpl;
import ru.josanr.sqlschool.infrastructure.GroupRepositoryImpl;
import ru.josanr.sqlschool.infrastructure.PostgresConnectionSource;
import ru.josanr.sqlschool.infrastructure.StudentsRepositoryImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        ConnectionSource connectionSource = new PostgresConnectionSource();
        var groupRepo = new GroupRepositoryImpl(connectionSource);
        var coursesRepo = new CoursesRepositoryImpl(connectionSource);
        var studentRepo = new StudentsRepositoryImpl(connectionSource, groupRepo, coursesRepo);

        var initializer = new AppInitializer(
            studentRepo,
            groupRepo,
            coursesRepo
        );

        initializer.run(new Faker());


        var groupService = new GroupsService(groupRepo, studentRepo);
        var coursesService = new CoursesService(coursesRepo, studentRepo);
        var studentService = new StudentService(studentRepo, new StudentFactory());
        var inputReader = new BufferedReader(new InputStreamReader(System.in));

        var commandFactory = new CommandFactory(
            groupService,
            coursesService,
            studentService,
            inputReader,
            System.out
        );
        var app = new App(
            commandFactory,
            inputReader,
            System.out
        );

        app.run();
    }
}
