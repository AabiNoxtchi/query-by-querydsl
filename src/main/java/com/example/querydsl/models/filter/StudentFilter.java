package com.example.querydsl.models.filter;

import com.example.querydsl.models.entity.QStudent;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
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

    public Predicate generatePredicate() {
        QStudent student = QStudent.student;
        Predicate aTrue = Expressions.asBoolean(true).isTrue();

        return ExpressionUtils.allOf(
                Objects.nonNull(id) ? student.id.eq(id) : aTrue,
                Objects.nonNull(name) ? student.name.containsIgnoreCase(name) : aTrue,
                Objects.nonNull(email) ? student.email.containsIgnoreCase(email) : aTrue,
                Objects.nonNull(ageGreaterThan) ? student.age.gt(ageGreaterThan) : aTrue,
                Objects.nonNull(ageLessThan) ? student.age.lt(ageLessThan) : aTrue);
    }

}
