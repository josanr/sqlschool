package ru.josanr.sqlschool.domain.services.impl;

import ru.josanr.sqlschool.domain.entities.Course;
import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.domain.services.CoursesService;
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
    public void addStudentToCourse(Long courseId, Long studentId) {
        Course course = courseRepo.getById(courseId);
        Student student = studentRepo.getById(studentId);

        courseRepo.addStudentToCourse(course, student);

    }

    @Override
    public void removeStudentFromCourse(Long courseId, Long studentId) {
        Course course = courseRepo.getById(courseId);
        Student student = studentRepo.getById(studentId);

        courseRepo.removeStudentFromCourse(course, student);

    }

    @Override
    public List<Course> findAll() {
        return courseRepo.findAll();
    }
}
