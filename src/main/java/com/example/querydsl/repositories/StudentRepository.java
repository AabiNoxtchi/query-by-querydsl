package com.example.querydsl.repositories;

import com.example.querydsl.models.entity.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, QuerydslPredicateExecutor<Student> {

    @EntityGraph(attributePaths = {"enrollments", "enrollments.course"})
    List<Student> findByEnrollmentsNotNull();
}
