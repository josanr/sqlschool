package ru.josanr.sqlschool.domain.services;

import java.util.List;

public interface GroupsService {

    List<String> findByStudentCount(long count);

    void addStudentToGroup(Integer groupId, Integer studentId);

    void removeStudentFromGroup(Integer groupId, Integer studentId);
}
