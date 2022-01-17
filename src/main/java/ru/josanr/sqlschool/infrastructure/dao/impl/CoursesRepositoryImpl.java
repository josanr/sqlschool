package ru.josanr.sqlschool.infrastructure.dao.impl;

import ru.josanr.sqlschool.domain.entities.Course;
import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.infrastructure.dao.CoursesRepository;
import ru.josanr.sqlschool.infrastructure.dao.exceptions.StorageException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoursesRepositoryImpl implements CoursesRepository {

    private final DataSource connectionSource;

    public CoursesRepositoryImpl(DataSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    @Override
    public List<Course> findByStudent(Student student) {
        String sql = "SELECT course_id FROM students_courses WHERE student_id = ?";
        var result = new ArrayList<Course>();
        try (
            var connection = connectionSource.getConnection();
            var stmt = connection.prepareStatement(sql)
        ) {
            stmt.setInt(1, student.getId());
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    result.add(getById(resultSet.getInt("course_id")));
                }
            }
        } catch (SQLException e) {
            throw new StorageException("Error on getting course by student", e);
        }

        return result;
    }

    @Override
    public List<Course> findAll() {
        String sql = "SELECT id, name, description FROM courses";
        var result = new ArrayList<Course>();
        try (
            var connection = connectionSource.getConnection();
            var stmt = connection.prepareStatement(sql)
        ) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    result.add(map(resultSet));
                }
            }
            return result;
        } catch (SQLException e) {
            throw new StorageException("Error on getting all course list", e);
        }
    }

    @Override
    public Course getById(Integer id) {
        String sql = "SELECT id, name, description FROM courses WHERE id = ?";

        try (
            var connection = connectionSource.getConnection();
            var stmt = connection.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                resultSet.next();
                return map(resultSet);
            }
        } catch (SQLException e) {
            throw new StorageException("Error on getting course by id", e);
        }
    }

    private Course map(ResultSet resultSet) throws SQLException {
        return new Course(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("description")
        );
    }

    @Override
    public void addStudentToCourse(Course course, Student student) {
        String sql = "INSERT INTO students_courses (course_id, student_id) " +
            "VALUES (?, ?)";

        try (
            var connection = connectionSource.getConnection();
            var stmt = connection.prepareStatement(sql)
        ) {
            stmt.setInt(1, course.getId());
            stmt.setInt(2, student.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException("Error on updating student course", e);
        }
    }

    @Override
    public void removeStudentFromCourse(Course course, Student student) {
        String sql = "DELETE FROM students_courses " +
            "WHERE course_id = ? AND student_id = ?";

        try (
            var connection = connectionSource.getConnection();
            var stmt = connection.prepareStatement(sql)
        ) {
            stmt.setInt(1, course.getId());
            stmt.setInt(2, student.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException("Error on removing student from  course", e);
        }
    }

    @Override
    public Course create(Course course) {
        var sql = "INSERT INTO courses (name, description)" +
            "VALUES (?, ?) RETURNING id; ";
        try (
            var connection = connectionSource.getConnection();
            var stmt = connection.prepareStatement(sql)
        ) {
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getDescription());
            try (ResultSet resultSet = stmt.executeQuery()) {
                resultSet.next();
                var newId = resultSet.getInt(1);
                return getById(newId);
            }
        } catch (SQLException e) {
            throw new StorageException("Error on course create", e);
        }
    }
}
