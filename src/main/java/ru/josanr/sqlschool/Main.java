package ru.josanr.sqlschool;

import com.github.javafaker.Faker;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import ru.josanr.sqlschool.application.App;
import ru.josanr.sqlschool.application.AppInitializer;
import ru.josanr.sqlschool.application.Config;
import ru.josanr.sqlschool.domain.factories.StudentFactory;
import ru.josanr.sqlschool.domain.services.CoursesServiceImpl;
import ru.josanr.sqlschool.domain.services.GroupsServiceImpl;
import ru.josanr.sqlschool.domain.services.StudentServiceImpl;
import ru.josanr.sqlschool.infrastructure.dao.CoursesRepositoryImpl;
import ru.josanr.sqlschool.infrastructure.dao.GroupRepositoryImpl;
import ru.josanr.sqlschool.infrastructure.dao.StudentsRepositoryImpl;
import ru.josanr.sqlschool.infrastructure.ui.ControllerFactory;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        Config config = getConfig();
        DataSource dataSource = getDataSource(config);

        Flyway migrationTool = Flyway.configure().dataSource(dataSource).load();

        var groupRepo = new GroupRepositoryImpl(dataSource);
        var coursesRepo = new CoursesRepositoryImpl(dataSource);
        var studentRepo = new StudentsRepositoryImpl(dataSource, groupRepo, coursesRepo);

        var initializer = new AppInitializer(
            studentRepo,
            groupRepo,
            coursesRepo,
            new Faker(),
            migrationTool
        );
        initializer.run();


        var groupService = new GroupsServiceImpl(groupRepo, studentRepo);
        var coursesService = new CoursesServiceImpl(coursesRepo, studentRepo);
        var studentService = new StudentServiceImpl(studentRepo, new StudentFactory());

        var inputReader = new BufferedReader(new InputStreamReader(System.in));

        var commandFactory = new ControllerFactory(
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

    private static PGSimpleDataSource getDataSource(Config config) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(config.getConfig("app.db_dsn"));
        dataSource.setUser(config.getConfig("app.db_user"));
        dataSource.setPassword(config.getConfig("app.db_password"));
        return dataSource;
    }

    private static Config getConfig() {
        Config config = new Config();
        config.loadProperties("/application.properties");
        return config;
    }
}
