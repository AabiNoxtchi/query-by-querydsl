package com.example.querydsl.models.filter;

import com.example.querydsl.models.entity.Grade;
import com.example.querydsl.models.entity.QEnrollment;
import com.example.querydsl.models.entity.QStudent;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.function.Function;

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

        return ExpressionUtils.allOf(
                buildEqualityPredicate(student.id, id),
                buildContainsIgnoreCasePredicate(student.name, name),
                buildContainsIgnoreCasePredicate(student.email, email),
                buildComparisonPredicate(student.age::gt, ageGreaterThan),
                buildComparisonPredicate(student.age::lt, ageLessThan),
                buildComparisonPredicate(student.enrollments.size()::gt, enrollmentsCountGreaterThan),
                buildComparisonPredicate(student.enrollments.size()::lt, enrollmentsCountLessThan),
                buildCourseNamePredicate(courseName, courseGrade, student),
                buildCourseGradePredicate(courseName, courseGrade, student),
                buildCourseNameAndGradePredicate(courseName, courseGrade, student)
        );
    }

    private static Predicate buildEqualityPredicate(NumberPath<Long> path, Long value) {
        return Objects.nonNull(value) ? path.eq(value) : null;
    }

    private static Predicate buildContainsIgnoreCasePredicate(StringPath path, String value) {
        return Objects.nonNull(value) ? path.containsIgnoreCase(value) : null;
    }

    private static <T extends Comparable<?>> Predicate buildComparisonPredicate(Function<T, Predicate> operation, T value) {
        return Objects.nonNull(value) ? operation.apply(value) : null;
    }

    private static Predicate buildCourseNamePredicate(String courseName, Grade courseGrade, QStudent student) {
        if (Objects.nonNull(courseName) && Objects.isNull(courseGrade)) {
            return student.enrollments.any().course.name.containsIgnoreCase(courseName);
        }
        return null;
    }

    private static Predicate buildCourseGradePredicate(String courseName, Grade courseGrade, QStudent student) {
        if (Objects.nonNull(courseGrade) && Objects.isNull(courseName)) {
            return student.enrollments.any().grade.eq(courseGrade);
        }
        return null;
    }

    private static Predicate buildCourseNameAndGradePredicate(String courseName, Grade courseGrade, QStudent student) {
        QEnrollment enrollment = QEnrollment.enrollment;

        if (Objects.nonNull(courseName) && Objects.nonNull(courseGrade)) {
            return student.enrollments.contains(
                    JPAExpressions.selectFrom(enrollment)
                            .where(enrollment.student.id.eq(student.id)
                                    .and(enrollment.course.name.eq(courseName)
                                            .and(enrollment.grade.eq(courseGrade))))
            );
        }
        return null;
    }

}
