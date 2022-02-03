package ru.josanr.sqlschool.infrastructure.dao.impl;

import ru.josanr.sqlschool.domain.entities.Group;
import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.infrastructure.dao.GroupRepository;
import ru.josanr.sqlschool.infrastructure.dao.exceptions.StorageException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupRepositoryImpl implements GroupRepository {

    private final DataSource connectionSource;

    public GroupRepositoryImpl(DataSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    @Override
    public List<Group> findByStudentCount(int count) {
        String sql = "SELECT g.id " +
            "FROM groups g " +
            "JOIN students s on g.id = s.group_id " +
            "GROUP BY g.id HAVING count(*) <= ?";

        var result = new ArrayList<Group>();

        try (
            var connection = connectionSource.getConnection();
            var stmt = connection.prepareStatement(sql)
        ) {
            stmt.setLong(1, count);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    result.add(getById(resultSet.getLong("id")));
                }
            }
        } catch (SQLException e) {
            throw new StorageException("Error on find group by student count", e);
        }

        return result;
    }

    @Override
    public Group create(Group group) {
        var sql = "INSERT INTO groups (name)" +
            "VALUES (?) RETURNING id; ";
        try (
            var connection = connectionSource.getConnection();
            var stmt = connection.prepareStatement(sql)
        ) {
            stmt.setString(1, group.getName());
            try (ResultSet resultSet = stmt.executeQuery()) {
                resultSet.next();
                var newId = resultSet.getLong(1);
                return getById(newId);
            }
        } catch (SQLException e) {
            throw new StorageException("Error on group create", e);
        }
    }

    @Override
    public Group getById(Long groupId) {
        String sql = "SELECT id, name FROM groups WHERE id = ?";

        try (
            var connection = connectionSource.getConnection();
            var stmt = connection.prepareStatement(sql)
        ) {
            stmt.setLong(1, groupId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                resultSet.next();
                return map(resultSet);
            }
        } catch (SQLException e) {
            throw new StorageException("Error on getting group by id", e);
        }
    }


    @Override
    public void addStudentToGroup(Group group, Student student) {
        String sql = "UPDATE students SET group_id = ? WHERE id = ?";

        try (
            var connection = connectionSource.getConnection();
            var stmt = connection.prepareStatement(sql)
        ) {
            stmt.setLong(1, group.getId());
            stmt.setLong(2, student.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException("Error on updating student group", e);
        }
    }

    @Override
    public void removeStudentFromGroup(Group group, Student student) {
        String sql = "UPDATE students SET group_id = null WHERE id = ? AND group_id = ?";

        try (
            var connection = connectionSource.getConnection();
            var stmt = connection.prepareStatement(sql)
        ) {
            stmt.setLong(1, student.getId());
            stmt.setLong(2, group.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException("Error on removing student from group", e);
        }
    }


    private Group map(ResultSet resultSet) throws SQLException {
        return new Group(resultSet.getLong("id"), resultSet.getString("name"));
    }
}
