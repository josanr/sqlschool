package ru.josanr.sqlschool.domain.services;

import ru.josanr.sqlschool.domain.entities.Course;
import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.infrastructure.dao.CoursesRepository;
import ru.josanr.sqlschool.infrastructure.dao.StudentsRepository;

import java.util.List;

public class CoursesServiceImpl implements CoursesService {

    private final CoursesRepository courseRepo;
    private final StudentsRepository studentRepo;

    public CoursesServiceImpl(CoursesRepository courseRepo, StudentsRepository studentRepo) {
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public List<Student> listCourseStudents(String courseName) {

        return studentRepo.findByCourseName(courseName);
    }

    @Override
    public void addStudentToCourse(Integer courseId, Integer studentId) {
        Course course = courseRepo.getById(courseId);
        Student student = studentRepo.getById(studentId);

        courseRepo.addStudentToCourse(course, student);

    }

    @Override
    public void removeStudentFromCourse(Integer courseId, Integer studentId) {
        Course course = courseRepo.getById(courseId);
        Student student = studentRepo.getById(studentId);

        courseRepo.removeStudentFromCourse(course, student);

    }

    @Override
    public List<Course> findAll() {
        return courseRepo.findAll();
    }
}
