package ru.josanr.sqlschool.domain.services;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.josanr.sqlschool.domain.entities.Group;
import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.domain.repositories.GroupRepository;
import ru.josanr.sqlschool.helpers.FakeHelper;

import java.util.List;

class GroupsServiceTest {

    private GroupsService groupsService;
    private GroupRepository repository;
    private FakeHelper faker;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(GroupRepository.class);
        groupsService = new GroupsService(repository);
        faker = new FakeHelper();
    }

    @Test
    void findWithStudentCount_shouldReturnGroupContainingNumberOfStudents() {

        Mockito.when(repository.findByStudentCount(2))
            .thenReturn(getListOfGroups());

        var byStudentCount = groupsService.findByStudentCount(2);

        Assertions.assertEquals(3, byStudentCount.size());
    }

    private List<Group> getListOfGroups() {
        var group1 = new Group(faker.id(), faker.word());
        for (var index = 0; index <= 3; index++) {
            group1.addStudent(faker.student());
        }


        var group2 = new Group(faker.id(), faker.word());
        for (var index = 0; index <= 2; index++) {
            group2.addStudent(faker.student());
        }


        var group3 = new Group(faker.id(), faker.word());
        for (var index = 0; index <= 1; index++) {
            group3.addStudent(faker.student());
        }

        return List.of(
            group1,
            group2,
            group3
        );
    }
}
