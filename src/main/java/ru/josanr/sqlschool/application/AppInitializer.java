package ru.josanr.sqlschool.application;

import com.github.javafaker.Faker;
import org.flywaydb.core.Flyway;
import ru.josanr.sqlschool.domain.entities.Course;
import ru.josanr.sqlschool.domain.entities.Group;
import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.infrastructure.dao.CoursesRepository;
import ru.josanr.sqlschool.infrastructure.dao.GroupRepository;
import ru.josanr.sqlschool.infrastructure.dao.StudentsRepository;

import java.util.ArrayList;
import java.util.Random;

public class AppInitializer {

    private static final int INITIAL_GROUP_COUNT = 10;
    private static final int INITIAL_STUDENTS_COUNT = 20;
    private final StudentsRepository studentsRepo;
    private final GroupRepository groupRepo;
    private final CoursesRepository courseRepo;
    private final Faker faker;
    private final Flyway migrations;
    private final Random random;
    private final String[] courseNameList = {
        "Agriculture",
        "Astronomy",
        "Biology",
        "Botany",
        "Chemistry",
        "Earth science",
        "Electronics",
        "Environmental science",
        "Environmental studies",
        "Forensic science"
    };

    public AppInitializer(
        StudentsRepository studentsRepo,
        GroupRepository groupRepo,
        CoursesRepository courseRepo,
        Faker faker,
        Flyway flyway
    ) {
        this.studentsRepo = studentsRepo;
        this.groupRepo = groupRepo;
        this.courseRepo = courseRepo;
        this.faker = faker;
        this.migrations = flyway;
        random = new Random();
    }

    public void run() {
        migrations.migrate();
        fillInitialData();
    }

    private void fillInitialData() {
        ArrayList<Group> groupList = buildGroupList();
        ArrayList<Course> courseList = buildCourseList();
        ArrayList<Student> studentList = buildStudentList();

        for (Student student : studentList) {
            var randomGroupIndex = random.ints(0, groupList.size() - 1)
                .findFirst();

            var randomCourseIndex = random.ints(0, courseList.size() - 1)
                .findFirst();


            var course = courseList.get(randomCourseIndex.orElse(0));
            var group = groupList.get(randomGroupIndex.orElse(0));
            courseRepo.addStudentToCourse(course, student);
            groupRepo.addStudentToGroup(group, student);
        }
    }

    private ArrayList<Student> buildStudentList() {
        var studentList = new ArrayList<Student>();
        for (int index = 0; index < INITIAL_STUDENTS_COUNT; index++) {
            var student = new Student(faker.name().firstName(), faker.name().lastName());
            studentList.add(index, studentsRepo.create(student));
        }
        return studentList;
    }

    private ArrayList<Course> buildCourseList() {
        var courseList = new ArrayList<Course>();
        for (int index = 0; index < courseNameList.length; index++) {
            courseList.add(index, courseRepo.create(new Course(courseNameList[index], "description")));
        }
        return courseList;
    }

    private ArrayList<Group> buildGroupList() {
        var groupList = new ArrayList<Group>();
        for (int index = 0; index < INITIAL_GROUP_COUNT; index++) {
            var fakeName = faker.lorem().characters(2) + "-" + faker.number().digits(2);
            groupList.add(index, groupRepo.create(new Group(fakeName)));
        }
        return groupList;
    }
}
