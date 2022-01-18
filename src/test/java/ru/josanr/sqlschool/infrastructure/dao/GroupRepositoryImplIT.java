package ru.josanr.sqlschool.infrastructure.dao;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.josanr.sqlschool.domain.entities.Group;
import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.helpers.DbHelper;
import ru.josanr.sqlschool.infrastructure.dao.impl.CoursesRepositoryImpl;
import ru.josanr.sqlschool.infrastructure.dao.impl.GroupRepositoryImpl;
import ru.josanr.sqlschool.infrastructure.dao.impl.StudentsRepositoryImpl;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@DBRider
@Tag("IntegrationTests")
class GroupRepositoryImplIT {

    private final DbHelper dbHelper;
    private GroupRepository repo;
    private StudentsRepository studentsRepository;

    public GroupRepositoryImplIT() {
        this.dbHelper = new DbHelper();
        dbHelper.resetSequence();
    }

    @BeforeEach
    @DataSet("sqlschool.json")
    void setUp() {

        DataSource dataSource = dbHelper.getDataSource();
        repo = new GroupRepositoryImpl(dataSource);
        CoursesRepositoryImpl coursesRepo = new CoursesRepositoryImpl(dataSource);
        studentsRepository = new StudentsRepositoryImpl(dataSource, repo, coursesRepo);

    }

    @Test
    void findByStudentCount_shouldReturnListOfGroups_givenMaxCountOfStudents() {
        var list = repo.findByStudentCount(1);
        assertThat(list, hasSize(2));
    }

    @Test
    void create_ShouldCreateNewGroupAndId_GivenGroupObject() {
        Group group = new Group("test_group");
        Group storedGroup = repo.create(group);
        assertThat(storedGroup.getId(), notNullValue());
        assertThat(storedGroup.getName(), is("test_group"));
    }

    @Test
    void getById_ShouldReturnGroup_givenId() {
        Group group = repo.getById(1L);
        assertThat(group.getId(), is(1L));
        assertThat(group.getName(), is("vy-20"));
    }

    @Test
    void addStudentToGroup() {
        Student student = studentsRepository.getById(1L);
        Group group = new Group(1L, "");
        repo.addStudentToGroup(group, student);

        Student updated = studentsRepository.getById(1L);
        assertThat(updated.getGroup().getId(), is(1L));
    }

    @Test
    void removeStudentFromGroup() {
        Student student = studentsRepository.getById(1L);
        Group group = new Group(5L, "");
        repo.removeStudentFromGroup(group, student);

        Student updated = studentsRepository.getById(1L);
        assertThat(updated.getGroup(), Matchers.nullValue());
    }
}
