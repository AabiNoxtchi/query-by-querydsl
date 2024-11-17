package com.example.querydsl.models.filter;

import com.example.querydsl.models.entity.Grade;
import com.example.querydsl.models.entity.QEnrollment;
import com.example.querydsl.models.entity.QStudent;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class StudentFilter {

    private Long id;

    private String name;
    private String email;

    private Integer ageGreaterThan;
    private Integer ageLessThan;

    private Integer enrollmentsCountGreaterThan;
    private Integer enrollmentsCountLessThan;

    private String courseName;
    private Grade courseGrade;

    public Predicate generatePredicate() {
        QStudent student = QStudent.student;
        QEnrollment enrollment = QEnrollment.enrollment;
        Predicate aTrue = Expressions.asBoolean(true).isTrue();

        return ExpressionUtils.allOf(
                Objects.nonNull(id) ? student.id.eq(id) : aTrue,
                Objects.nonNull(name) ? student.name.containsIgnoreCase(name) : aTrue,
                Objects.nonNull(email) ? student.email.containsIgnoreCase(email) : aTrue,
                Objects.nonNull(ageGreaterThan) ? student.age.gt(ageGreaterThan) : aTrue,
                Objects.nonNull(ageLessThan) ? student.age.lt(ageLessThan) : aTrue,
                Objects.nonNull(enrollmentsCountGreaterThan) ?
                        student.enrollments.size().gt(enrollmentsCountGreaterThan) : aTrue,
                Objects.nonNull(enrollmentsCountLessThan) ?
                        student.enrollments.size().lt(enrollmentsCountLessThan) : aTrue,
                Objects.nonNull(courseName) && Objects.isNull(courseGrade) ?
                        student.enrollments.any().course.name.containsIgnoreCase(courseName) : aTrue,
                Objects.nonNull(courseGrade) && Objects.isNull(courseName) ?
                        student.enrollments.any().grade.eq(courseGrade) : aTrue,
                Objects.nonNull(courseName) && Objects.nonNull(courseGrade) ?
                        student.enrollments.contains(
                                JPAExpressions.selectFrom(enrollment)
                                        .where(enrollment.student.id.eq(student.id)
                                                .and(enrollment.course.name.eq(courseName)
                                                        .and(enrollment.grade.eq(courseGrade))))) : aTrue
        );
    }

}
