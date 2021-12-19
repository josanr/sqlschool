package ru.josanr.sqlschool.application;

import com.github.javafaker.Faker;
import ru.josanr.sqlschool.domain.entities.Course;
import ru.josanr.sqlschool.domain.entities.Group;
import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.domain.repositories.CoursesRepository;
import ru.josanr.sqlschool.domain.repositories.GroupRepository;
import ru.josanr.sqlschool.domain.repositories.StudentsRepository;

import java.util.ArrayList;
import java.util.Random;

public class AppInitializer {

    private static final int INITIAL_GROUP_COUNT = 10;
    private static final int INITIAL_STUDENTS_COUNT = 20;
    private final StudentsRepository studentsRepo;
    private final GroupRepository groupRepo;
    private final CoursesRepository courseRepo;

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
    private final Random random;

    public AppInitializer(
        StudentsRepository studentsRepo,
        GroupRepository groupRepo,
        CoursesRepository courseRepo

    ) {
        this.studentsRepo = studentsRepo;
        this.groupRepo = groupRepo;
        this.courseRepo = courseRepo;
        random = new Random();
    }

    public void run(Faker faker) {
        var groupList = new ArrayList<Group>();
        var courseList = new ArrayList<Course>();
        var studentList = new ArrayList<Student>();

        for (int index = 0; index < INITIAL_GROUP_COUNT; index++) {
            var fakeName = faker.lorem().characters(2) + "-" + faker.number().digits(2);
            groupList.add(index, groupRepo.add(new Group(fakeName)));
        }

        for (int index = 0; index < courseNameList.length; index++) {
            courseList.add(index, courseRepo.add(new Course(courseNameList[index], "description")));
        }

        for (int index = 0; index < INITIAL_STUDENTS_COUNT; index++) {
            var student = new Student(faker.name().firstName(), faker.name().lastName());
            studentList.add(index, studentsRepo.add(student));
        }

        for (Student student : studentList) {
            var randomGroupIndex = random.ints(0, groupList.size() - 1)
                .findFirst()
                .getAsInt();

            var randomCourseIndex = random.ints(0, courseList.size() - 1)
                .findFirst()
                .getAsInt();

            var course = courseList.get(randomCourseIndex);
            var group = groupList.get(randomGroupIndex);
            courseRepo.addStudentToCourse(course, student);
            groupRepo.addStudentToGroup(group, student);
        }
    }
}
