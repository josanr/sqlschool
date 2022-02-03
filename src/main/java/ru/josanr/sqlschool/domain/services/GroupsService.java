package ru.josanr.sqlschool.domain.services;

import java.util.List;

public interface GroupsService {

    List<String> findByStudentCount(int count);

    void addStudentToGroup(Long groupId, Long studentId);

    void removeStudentFromGroup(Long groupId, Long studentId);
}
