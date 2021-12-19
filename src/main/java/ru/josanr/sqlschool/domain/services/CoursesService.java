package ru.josanr.sqlschool.domain.services;

import ru.josanr.sqlschool.domain.entities.Course;
import ru.josanr.sqlschool.domain.entities.Student;
import ru.josanr.sqlschool.domain.repositories.CoursesRepository;
import ru.josanr.sqlschool.domain.repositories.StudentsRepository;

import java.util.List;

public class CoursesService {

    private final CoursesRepository courseRepo;
    private final StudentsRepository studentRepo;

    public CoursesService(CoursesRepository courseRepo, StudentsRepository studentRepo) {
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
    }

    public List<Student> listCourseStudents(String courseName) {

        return studentRepo.findByCourseName(courseName);
    }

    public void addStudentToCourse(Integer courseId, Integer studentId) {
        Course course = courseRepo.getById(courseId);
        Student student = studentRepo.getById(studentId);

        courseRepo.addStudentToCourse(course, student);

    }

    public void removeStudentFromCourse(Integer courseId, Integer studentId) {
        Course course = courseRepo.getById(courseId);
        Student student = studentRepo.getById(studentId);

        courseRepo.removeStudentFromCourse(course, student);

    }

    public List<Course> findAll() {
        return courseRepo.findAll();
    }
}
