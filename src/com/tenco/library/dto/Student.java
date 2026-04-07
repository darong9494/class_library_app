package com.tenco.library.dto;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Student {
    private int id;
    private String name;
    private String studentId;

    @Builder
    public Student(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
    }
}

