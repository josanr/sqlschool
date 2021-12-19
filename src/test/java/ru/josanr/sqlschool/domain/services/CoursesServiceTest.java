package ru.josanr.sqlschool.domain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.josanr.sqlschool.domain.entities.Course;
import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.domain.repositories.CoursesRepository;
import ru.josanr.sqlschool.domain.repositories.StudentsRepository;
import ru.josanr.sqlschool.helpers.FakeHelper;

class CoursesServiceTest {

    private CoursesRepository repo;
    private CoursesService courseService;
    private FakeHelper fakerHelper;
    private StudentsRepository studentRepo;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(CoursesRepository.class);
        studentRepo = Mockito.mock(StudentsRepository.class);
        courseService = new CoursesService(repo, studentRepo);
        fakerHelper = new FakeHelper();
    }

    @Test
    void addStudentToCourse_shouldAddStudentToCourse_givenIds() {
        var courseId = 1;
        var studentId = 1;

        Course course = fakerHelper.course();
        Student student = fakerHelper.student();

        Mockito.when(repo.getById(courseId)).thenReturn(course);
        Mockito.when(studentRepo.getById(studentId)).thenReturn(student);

        courseService.addStudentToCourse(courseId, studentId);
        Mockito.verify(repo, Mockito.times(1)).addStudentToCourse(course, student);
    }

    @Test
    void addStudentToCourse_shouldRemoveStudentFromCourse_givenIds() {
        var courseId = 1;
        var studentId = 1;

        Course course = fakerHelper.course();
        Student student = fakerHelper.student();

        Mockito.when(repo.getById(courseId)).thenReturn(course);
        Mockito.when(studentRepo.getById(studentId)).thenReturn(student);

        courseService.removeStudentFromCourse(courseId, studentId);
        Mockito.verify(repo, Mockito.times(1)).removeStudentFromCourse(course, student);
    }
}
