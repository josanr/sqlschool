package ru.josanr.sqlschool.domain.services;

import ru.josanr.sqlschool.domain.repositories.GroupRepository;
import ru.josanr.sqlschool.domain.repositories.StudentsRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GroupsService {

    private final StudentsRepository studentRepo;
    GroupRepository groupRepo;

    public GroupsService(GroupRepository groupRepo, StudentsRepository studentRepo) {
        this.groupRepo = groupRepo;
        this.studentRepo = studentRepo;
    }

    public List<String> findByStudentCount(long count) {
        return groupRepo.findByStudentCount(count)
            .stream()
            .map(item -> item.getId() + " - " + item.getName())
            .collect(Collectors.toList());
    }

    public void addStudentToGroup(Integer groupId, Integer studentId) {
        var group = groupRepo.getById(groupId);
        var student = studentRepo.getById(studentId);

        groupRepo.addStudentToGroup(group, student);

    }

    public void removeStudentFromGroup(Integer groupId, Integer studentId) {
        var group = groupRepo.getById(groupId);
        var student = studentRepo.getById(studentId);

        groupRepo.removeStudentFromGroup(group, student);

    }
}
