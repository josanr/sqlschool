package ru.josanr.sqlschool.domain.services.impl;

import ru.josanr.sqlschool.domain.services.GroupsService;
import ru.josanr.sqlschool.infrastructure.dao.GroupRepository;
import ru.josanr.sqlschool.infrastructure.dao.StudentsRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GroupsServiceImpl implements GroupsService {

    private final StudentsRepository studentRepo;
    final GroupRepository groupRepo;

    public GroupsServiceImpl(GroupRepository groupRepo, StudentsRepository studentRepo) {
        this.groupRepo = groupRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public List<String> findByStudentCount(int count) {
        return groupRepo.findByStudentCount(count)
            .stream()
            .map(item -> item.getId() + " - " + item.getName())
            .collect(Collectors.toList());
    }

    @Override
    public void addStudentToGroup(Long groupId, Long studentId) {
        var group = groupRepo.getById(groupId);
        var student = studentRepo.getById(studentId);

        groupRepo.addStudentToGroup(group, student);

    }

    @Override
    public void removeStudentFromGroup(Long groupId, Long studentId) {
        var group = groupRepo.getById(groupId);
        var student = studentRepo.getById(studentId);

        groupRepo.removeStudentFromGroup(group, student);

    }
}
