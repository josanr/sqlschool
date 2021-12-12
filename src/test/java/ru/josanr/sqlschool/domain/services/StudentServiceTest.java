package ru.josanr.sqlschool.domain.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.domain.factories.StudentFactory;
import ru.josanr.sqlschool.domain.repositories.StudentsRepository;
import ru.josanr.sqlschool.helpers.FakeHelper;

class StudentServiceTest {

    private StudentsRepository repository;
    private StudentService studentService;
    private FakeHelper fakeHelper;
    private StudentFactory factory;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(StudentsRepository.class);
        factory = Mockito.mock(StudentFactory.class);
        studentService = new StudentService(repository, factory);
        fakeHelper = new FakeHelper();
    }

    @Test
    void createStudent_shouldAddANewStudentToRepository() {

        var student = fakeHelper.student();
        Mockito.when(factory.create(student.getFirstName(), student.getLastName())).thenReturn(student);
        Mockito.when(repository.add(student)).thenReturn(new Student(1, student.getFirstName(), student.getLastName()));

        var createdStudent = studentService.createStudent(student.getFirstName(), student.getLastName());

        Mockito.verify(repository, Mockito.times(1)).add(student);
        Assertions.assertEquals(student.getFirstName(), createdStudent.getFirstName());
        Assertions.assertEquals(student.getLastName(), createdStudent.getLastName());
        Assertions.assertNotEquals(student.getId(), createdStudent.getId());
    }

    @Test
    void remove_shouldCallRepositoryRemove() {
        var id = fakeHelper.id();

        studentService.remove(id);

        Mockito.verify(repository, Mockito.times(1)).remove(id);
    }
}
