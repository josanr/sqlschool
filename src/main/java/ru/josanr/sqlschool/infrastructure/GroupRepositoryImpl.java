package ru.josanr.sqlschool.infrastructure;

import ru.josanr.sqlschool.domain.entities.Group;
import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.domain.repositories.GroupRepository;
import ru.josanr.sqlschool.infrastructure.exceptions.StorageException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupRepositoryImpl implements GroupRepository {

    private final ConnectionSource connectionSource;

    public GroupRepositoryImpl(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    @Override
    public List<Group> findByStudentCount(long count) {
        String sql = "SELECT g.id " +
            "FROM groups g " +
            "JOIN students s on g.id = s.group_id " +
            "GROUP BY g.id HAVING count(*) <= ?";

        var result = new ArrayList<Group>();
        try (var stmt = connectionSource.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, count);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    result.add(getById(resultSet.getInt("id")));
                }
            }
        } catch (SQLException e) {
            throw new StorageException("Error on find group by student count", e);
        }

        return result;
    }

    @Override
    public Group add(Group group) {
        if (group.getId() == null) {
            return this.addNewGroup(group);
        }
        return this.updateGroup(group);
    }

    private Group updateGroup(Group group) {
        var sql = "UPDATE groups SET name = ? WHERE id = ?";
        try (var stmt = connectionSource.getConnection().prepareStatement(sql)) {
            stmt.setString(1, group.getName());
            stmt.setInt(2, group.getId());
            stmt.executeUpdate();
            return group;
        } catch (SQLException e) {
            throw new StorageException("Error on group update", e);
        }
    }

    private Group addNewGroup(Group group) {
        var sql = "INSERT INTO groups (name)" +
            "VALUES (?) RETURNING id; ";
        try (var stmt = connectionSource.getConnection().prepareStatement(sql)) {
            stmt.setString(1, group.getName());
            try (ResultSet resultSet = stmt.executeQuery()) {
                resultSet.next();
                var newId = resultSet.getInt(1);
                return getById(newId);
            }
        } catch (SQLException e) {
            throw new StorageException("Error on group create", e);
        }
    }

    @Override
    public Group getById(Integer groupId) {
        String sql = "SELECT id, name FROM groups WHERE id = ?";

        try (var stmt = connectionSource.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, groupId);
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

        try (var stmt = connectionSource.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, group.getId());
            stmt.setInt(2, student.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException("Error on updating student group", e);
        }
    }

    @Override
    public void removeStudentFromGroup(Group group, Student student) {
        String sql = "UPDATE students SET group_id = null WHERE id = ? AND group_id = ?";

        try (var stmt = connectionSource.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, student.getId());
            stmt.setInt(2, group.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException("Error on removing student from group", e);
        }
    }


    private Group map(ResultSet resultSet) throws SQLException {
        return new Group(resultSet.getInt("id"), resultSet.getString("name"));
    }
}
