package ru.josanr.sqlschool.domain.services;

import ru.josanr.sqlschool.domain.repositories.GroupRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GroupsService {

    GroupRepository repository;

    public GroupsService(GroupRepository repository) {
        this.repository = repository;
    }

    public List<String> findByStudentCount(long count) {
        return repository.findByStudentCount(count)
            .stream()
            .map(item -> item.getId() + " - " + item.getName())
            .collect(Collectors.toList());
    }
}
