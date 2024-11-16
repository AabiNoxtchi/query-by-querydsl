package com.example.querydsl;

import com.example.querydsl.models.entity.Student;
import com.example.querydsl.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialTestData implements CommandLineRunner {

    private static final Random RANDOM = new Random();
    private static final List<String> STUDENT_NAMES =
            List.of("John Doe", "Bob Doe",  "Jane Smith", "Alice Brown", "Bob White", "Charlie Black");
    private static final List<String> EMAIL_VENDORS =
            List.of("@gmail.com", "@yahoo.com", "@outlook.com", "@icloud.com", "@aol.com", "@zoho.com", "@protonmail.com");

    private final StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {
        if(studentRepository.count() == 0) {
            studentRepository.saveAll(initStudents());
        }
        log.info("student repository count = {}", studentRepository.count());
    }

    public static List<Student> initStudents() {

        List<Student> students = new ArrayList<>();
        IntStream.range(1, 100).forEach(index -> {
            String name =
                    STUDENT_NAMES.get(RANDOM.nextInt(STUDENT_NAMES.size())) + " " + index;
            String email =
                    name.replace(" ", "").toLowerCase() +
                            EMAIL_VENDORS.get(RANDOM.nextInt(EMAIL_VENDORS.size()));
            int age = RANDOM.nextInt(32) + 18; // from 18 to 50

            students.add(new Student((long) index, name, age, email, new HashSet<>()));
        });
        return students;
    }

}
