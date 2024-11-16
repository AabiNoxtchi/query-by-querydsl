package com.example.querydsl.service;

import com.example.querydsl.models.entity.Student;
import com.example.querydsl.models.filter.StudentFilter;
import com.example.querydsl.repositories.StudentRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> findAll(StudentFilter filter) {

        Predicate predicate = filter.generatePredicate();
        return (List<Student>) studentRepository.findAll(predicate);
    }
}
