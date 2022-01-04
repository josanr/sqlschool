package ru.josanr.sqlschool.infrastructure.dao;

import ru.josanr.sqlschool.domain.entities.Group;
import ru.josanr.sqlschool.domain.entities.Student;

import java.util.List;

public interface GroupRepository {

    List<Group> findByStudentCount(long count);

    Group create(Group group);

    Group getById(Integer groupId);

    void addStudentToGroup(Group group, Student student);

    void removeStudentFromGroup(Group group, Student student);
}
