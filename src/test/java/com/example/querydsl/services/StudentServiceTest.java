package com.example.querydsl.services;

import com.example.querydsl.models.entity.Student;
import com.example.querydsl.models.filter.StudentFilter;
import com.example.querydsl.repositories.StudentRepository;
import com.example.querydsl.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.querydsl.InitialTestData.initStudents;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StudentServiceTest {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    private final Random rand = new Random();
    private List<Student> dbStudents = new ArrayList<>();

    @BeforeEach
    public void setup() {

        if(studentRepository.count() == 0) {
            studentRepository.saveAll(initStudents());
        }
        if(dbStudents.isEmpty()) {
            dbStudents = studentRepository.findAll();
        }
    }

    @Test
    public void filterStudents_providingFullName_ShouldPass() {
        // create
        int rnd = rand.nextInt(dbStudents.size());
        String nameToFilter = dbStudents.get(rnd).getName();
        int count = dbStudents.stream().filter(student -> student.getName().contains(nameToFilter)).toList().size();

        StudentFilter filter = new StudentFilter();
        filter.setName(nameToFilter);

        // test
        List<Student> students = studentService.findAll(filter);

        // assert
        assertEquals(count, students.size());
    }

    @Test
    public void filterStudents_providingPartNameAndPartEmail_ShouldPass() {
        // create
        Student randomStudent = dbStudents.get(rand.nextInt(dbStudents.size()));
        String partNameToFilter = randomStudent.getName().split(" ")[0].toLowerCase();
        String partEmailToFilter = randomStudent.getEmail().split("@")[1].toLowerCase();
        int count = dbStudents.stream()
                .filter(student -> student.getName().toLowerCase().contains(partNameToFilter) &&
                        student.getEmail().toLowerCase().contains(partEmailToFilter))
                .toList().size();

        StudentFilter filter = new StudentFilter();
        filter.setName(partNameToFilter);
        filter.setEmail(partEmailToFilter);

        // test
        List<Student> students = studentService.findAll(filter);

        // assert
        assertEquals(count, students.size());
    }

    @Test
    public void filterStudents_providingAgeBoundaries_ShouldPass() {
        // create
        int minAge = 20;
        int maxAge = 30;
        int count = dbStudents.stream()
                .filter(student -> student.getAge() > minAge && student.getAge() < maxAge)
                .toList().size();

        StudentFilter filter = new StudentFilter();
        filter.setAgeGreaterThan(minAge);
        filter.setAgeLessThan(maxAge);

        // test
        List<Student> students = studentService.findAll(filter);

        // assert
        assertEquals(count, students.size());
    }

}
