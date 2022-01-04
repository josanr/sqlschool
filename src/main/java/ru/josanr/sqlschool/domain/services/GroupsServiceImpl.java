package ru.josanr.sqlschool.domain.services;

import ru.josanr.sqlschool.infrastructure.dao.GroupRepository;
import ru.josanr.sqlschool.infrastructure.dao.StudentsRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GroupsServiceImpl implements GroupsService {

    private final StudentsRepository studentRepo;
    GroupRepository groupRepo;

    public GroupsServiceImpl(GroupRepository groupRepo, StudentsRepository studentRepo) {
        this.groupRepo = groupRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public List<String> findByStudentCount(long count) {
        return groupRepo.findByStudentCount(count)
            .stream()
            .map(item -> item.getId() + " - " + item.getName())
            .collect(Collectors.toList());
    }

    @Override
    public void addStudentToGroup(Integer groupId, Integer studentId) {
        var group = groupRepo.getById(groupId);
        var student = studentRepo.getById(studentId);

        groupRepo.addStudentToGroup(group, student);

    }

    @Override
    public void removeStudentFromGroup(Integer groupId, Integer studentId) {
        var group = groupRepo.getById(groupId);
        var student = studentRepo.getById(studentId);

        groupRepo.removeStudentFromGroup(group, student);

    }
}
