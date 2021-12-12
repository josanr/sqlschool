package ru.josanr.sqlschool.domain.repositories;

import ru.josanr.sqlschool.domain.entities.Group;

import java.util.List;

public interface GroupRepository {

    List<Group> findByStudentCount(long count);
}
