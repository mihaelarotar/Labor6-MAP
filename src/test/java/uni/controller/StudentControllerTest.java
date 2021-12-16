package uni.controller;

import com.example.uni.controller.StudentController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.example.uni.entities.Student;
import com.example.uni.exceptions.ExceededValueException;
import com.example.uni.repository.StudentJdbcRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentControllerTest {

    private StudentController studentController;

    @BeforeEach
    void setup() throws SQLException {
        StudentJdbcRepository studentRepository = Mockito.mock(StudentJdbcRepository.class);
        studentController = new StudentController(studentRepository);
        Student student = new Student("Maria", "Pop", 100);
        Student student1 = new Student("Vlad", "Popa", 101);
        Student student2 = new Student("Marian", "Popa", 102);
        Student student3 = new Student("Vlad", "Apopei", 104);
        Student student4 = new Student("Jane", "Doe", 105);

        try {
            student.setTotalCredits(30);
            student1.setTotalCredits(25);
            student4.setTotalCredits(10);
            student2.setTotalCredits(25);
        } catch (ExceededValueException exception) {
            System.out.println(exception.getMessage());
        }

        studentController.add(student);
        studentController.add(student1);
        studentController.add(student2);
        studentController.add(student3);
        studentController.add(student4);

        List<Student> students = new ArrayList<>(Arrays.asList(student, student1, student2, student3, student4));
        Mockito.when(studentRepository.getAll()).thenReturn(students);
        Mockito.when(studentRepository.findByID(100)).thenReturn(student);
        Mockito.when(studentRepository.findByID(101)).thenReturn(student1);
        Mockito.when(studentRepository.findByID(102)).thenReturn(student2);
        Mockito.when(studentRepository.findByID(104)).thenReturn(student3);
        Mockito.when(studentRepository.findByID(105)).thenReturn(student4);
    }

    @Test
    void filterByTotalCredits() throws Exception{
        List<Student> filteredStudents = studentController.filterByTotalCredits(25);
        assertEquals(filteredStudents, new ArrayList<>(Arrays.asList(studentController.findByID(101), studentController.findByID(102))));
    }

    @Test
    void sortByName() throws Exception{
        studentController.sortByName();
        assertEquals(studentController.getAll(), new ArrayList<>(Arrays.asList(studentController.findByID(104),
                studentController.findByID(105), studentController.findByID(100),
                studentController.findByID(102), studentController.findByID(101))));
    }

    @Test
    void sortByCreditsDescending() throws Exception{
        studentController.sortByCreditsDescending();
        assertEquals(studentController.getAll(), new ArrayList<>(Arrays.asList(studentController.findByID(100),
                studentController.findByID(101), studentController.findByID(102),
                studentController.findByID(105), studentController.findByID(104))));
    }
}