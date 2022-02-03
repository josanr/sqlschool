DROP TABLE IF EXISTS students_courses;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS courses;

CREATE TABLE groups
(
    id   serial  not null primary key,
    name varchar not null
);

CREATE TABLE students
(
    id         serial primary key,
    group_id   integer references groups (id),
    first_name varchar not null,
    last_name  varchar not null
);

CREATE TABLE courses
(
    id          serial primary key,
    name        varchar not null,
    description text    not null
);

CREATE TABLE students_courses
(
    student_id integer
        CONSTRAINT students_courses_students_id_fk
            REFERENCES students (id) ON DELETE CASCADE,
    course_id  integer
        CONSTRAINT students_courses_course_id_fk
            REFERENCES courses (id) ON DELETE CASCADE
);

ALTER TABLE students
    OWNER TO "school_admin";
ALTER TABLE groups
    OWNER TO "school_admin";
ALTER TABLE courses
    OWNER TO "school_admin";
ALTER TABLE students_courses
    OWNER TO "school_admin";

CREATE UNIQUE INDEX IF NOT EXISTS students_courses_student_id_course_id_uindex
    ON students_courses (student_id, course_id);

