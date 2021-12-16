package uni.controller;

import com.example.uni.controller.CourseController;
import com.example.uni.controller.RegistrationSystem;
import com.example.uni.controller.StudentController;
import com.example.uni.controller.TeacherController;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import com.example.uni.entities.Course;
import com.example.uni.entities.Student;
import com.example.uni.entities.Teacher;

import com.example.uni.exceptions.NonExistingDataException;
import com.example.uni.repository.CourseJdbcRepository;
import com.example.uni.repository.StudentJdbcRepository;
import com.example.uni.repository.TeacherJdbcRepository;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationSystemTest {

    private RegistrationSystem registrationSystem = Mockito.mock(RegistrationSystem.class);

    @BeforeEach
    void setup() throws SQLException {
        CourseJdbcRepository courseRepository = Mockito.mock(CourseJdbcRepository.class);
        StudentJdbcRepository studentRepository = Mockito.mock(StudentJdbcRepository.class);
        TeacherJdbcRepository teacherRepository = Mockito.mock(TeacherJdbcRepository.class);
        StudentController studentController = new StudentController(studentRepository);
        TeacherController teacherController = new TeacherController(teacherRepository);
        CourseController courseController = new CourseController(courseRepository);
        registrationSystem = new RegistrationSystem(studentController, courseController, teacherController);

        Teacher teacher = new Teacher("Ana", "Pop", 1);
        Teacher teacher1 = new Teacher("John", "Smith", 2);
        teacherController.add(teacher);
        teacherController.add(teacher1);

        List<Teacher> teachers = new ArrayList<>(Arrays.asList(teacher, teacher1));
        Mockito.when(teacherRepository.getAll()).thenReturn(teachers);
        Mockito.when(teacherRepository.findByID(1)).thenReturn(teacher);
        Mockito.when(teacherRepository.findByID(2)).thenReturn(teacher1);

        Student student = new Student("Vlad", "Pop", 1);
        Student student1 = new Student("Maria", "Popa", 2);
        Student student2 = new Student("Jane", "Smith", 3);
        studentController.add(student);
        studentController.add(student1);
        studentController.add(student2);
        List<Student> students = new ArrayList<>(Arrays.asList(student, student1, student2));
        Mockito.when(studentRepository.getAll()).thenReturn(students);
        Mockito.when(studentRepository.findByID(1)).thenReturn(student);
        Mockito.when(studentRepository.findByID(2)).thenReturn(student1);
        Mockito.when(studentRepository.findByID(3)).thenReturn(student2);

        Course databases = new Course("DB", 1, 2, 4);
        Course oop = new Course("OOP", 2, 50, 5);

        registrationSystem.addCourse(databases);
        registrationSystem.addCourse(oop);

        List<Course> courses = new ArrayList<>(Arrays.asList(databases, oop));
        Mockito.when(courseRepository.getAll()).thenReturn(courses);
        Mockito.when(courseRepository.findByName("DB")).thenReturn(databases);
        Mockito.when(courseRepository.findByName("OOP")).thenReturn(oop);

    }


    @Test
    @Description("check if an exception is thrown if the given course or student are not in the list")
    void registerNonExistingData() {
        Student student = new Student("Vlad", "Pop", 1111);
        Course databases = new Course("DB", 1,2,4);
        assertThrows(NonExistingDataException.class, () -> registrationSystem.register(databases.getName(), student.getStudentID()));
    }

    @Test
    @Description("checks if an exception is thrown when the total number of credits for a students exceeds 30")
    void registerExceededValue() throws Exception {
        Course databases = registrationSystem.getCourseController().getAll().get(0);
        Student student = registrationSystem.getStudentController().getAll().get(0);
        student.setTotalCredits(27);
        assertFalse(registrationSystem.register(databases.getName(), student.getStudentID()));
    }


    @Test
    void getAllCourses() throws Exception{
        assertEquals(registrationSystem.getAllCourses().size(), 2);
        assertEquals(registrationSystem.getAllCourses(), registrationSystem.getCourseController().getAll());
    }

    @Test
    @Description("checks if the deleted course was also removed from the teacher's list of courses")
    void deleteCourseFromTeachersList() throws Exception{
        Course databases = registrationSystem.getCourseController().getAll().get(0);
        Teacher teacher = registrationSystem.getTeacherController().getAll().get(0);
        registrationSystem.deleteCourse("DB");
        assertFalse(teacher.getCourses().contains(databases));
    }


    @Test
    @Description("checks if the added course was also added to the teacher's list of courses")
    void saveCourseInTeachersList() throws Exception{
        Course databases = registrationSystem.getCourseController().findByName("DB");
        Teacher teacher = registrationSystem.getTeacherController().findByID(1);
        assertTrue(teacher.getCourses().contains(databases));
    }
}