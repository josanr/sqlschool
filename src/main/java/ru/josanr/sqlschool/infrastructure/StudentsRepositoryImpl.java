package ru.josanr.sqlschool.infrastructure;

import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.domain.repositories.CoursesRepository;
import ru.josanr.sqlschool.domain.repositories.GroupRepository;
import ru.josanr.sqlschool.domain.repositories.StudentsRepository;
import ru.josanr.sqlschool.infrastructure.exceptions.StorageException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class StudentsRepositoryImpl implements StudentsRepository {

    private final ConnectionSource connectionSource;
    private final GroupRepository groupRepo;
    private final CoursesRepository coursesRepo;

    public StudentsRepositoryImpl(
        ConnectionSource connectionSource,
        GroupRepository groupRepo,
        CoursesRepository coursesRepo
    ) {
        this.connectionSource = connectionSource;
        this.groupRepo = groupRepo;
        this.coursesRepo = coursesRepo;
    }

    @Override
    public List<Student> findByCourseName(String courseName) {

        String sql = "SELECT s.id, s.first_name, s.last_name, s.group_id FROM students s " +
            "JOIN students_courses sc on s.id = sc.student_id  " +
            "JOIN courses c on sc.course_id = c.id AND c.name LIKE ?";

        var result = new ArrayList<Student>();
        try (var stmt = connectionSource.getConnection().prepareStatement(sql)) {
            stmt.setString(1, courseName);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    result.add(map(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new StorageException("Error on findStudent by course name", e);
        }

        return result;
    }

    @Override
    public Student add(Student student) {
        if (student.getId() == null) {
            return this.addNewStudent(student);
        }
        return this.updateStudent(student);
    }

    private Student updateStudent(Student student) {
        var sql = "UPDATE students SET group_id = ?, first_name = ?, last_name = ? WHERE id = ?";
        try (var stmt = connectionSource.getConnection().prepareStatement(sql)) {
            if (student.getGroup() != null) {
                stmt.setInt(1, student.getGroup().getId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }

            stmt.setString(2, student.getFirstName());
            stmt.setString(3, student.getLastName());
            stmt.setInt(4, student.getId());
            stmt.executeUpdate();
            return student;
        } catch (SQLException e) {
            throw new StorageException("Error on student update", e);
        }
    }

    private Student addNewStudent(Student student) {
        var sql = "INSERT INTO students (group_id, first_name, last_name)" +
            "VALUES (?, ?, ?) RETURNING id; ";
        try (var stmt = connectionSource.getConnection().prepareStatement(sql)) {
            if (student.getGroup() != null) {
                stmt.setInt(1, student.getGroup().getId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            stmt.setString(2, student.getFirstName());
            stmt.setString(3, student.getLastName());
            try (ResultSet resultSet = stmt.executeQuery()) {
                resultSet.next();
                var newId = resultSet.getInt(1);
                return getById(newId);
            }
        } catch (SQLException e) {
            throw new StorageException("Error on student create", e);
        }
    }

    @Override
    public void remove(Student student) {
        var sql = "DELETE FROM students WHERE id = ?";
        try (var stmt = connectionSource.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, student.getId());
            var rows = stmt.executeUpdate();
            if (rows == 0 || rows > 1) {
                throw new StorageException("No rows or two many deleted");
            }
        } catch (SQLException e) {
            throw new StorageException("Error on student create", e);
        }
    }

    @Override
    public Student getById(Integer studentId) {
        String sql = "SELECT s.id, s.first_name, s.last_name, s.group_id FROM students s WHERE id = ?";

        try (var stmt = connectionSource.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                resultSet.next();
                return map(resultSet);
            }
        } catch (SQLException e) {
            throw new StorageException("Error on getting student by id", e);
        }
    }

    @Override
    public List<Student> findAll() {
        String sql = "SELECT s.id, s.first_name, s.last_name, s.group_id FROM students s";

        var result = new ArrayList<Student>();
        try (var stmt = connectionSource.getConnection().prepareStatement(sql)) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    result.add(map(resultSet));
                }
            }
            return result;
        } catch (SQLException e) {
            throw new StorageException("Error on getting all students", e);
        }
    }

    private Student map(ResultSet resultSet) throws SQLException {
        var student = new Student(
            resultSet.getInt("id"),
            resultSet.getString("first_name"),
            resultSet.getString("last_name")
        );

        int groupId = resultSet.getInt("group_id");
        if (groupId != 0) {
            var group = groupRepo.getById(groupId);
            student.setGroup(group);
        }

        var courseList = coursesRepo.findByStudent(student);
        for (var course : courseList) {
            student.addCourse(course);
        }

        return student;
    }
}
