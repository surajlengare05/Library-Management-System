package com.example.Student_Library_Management_System.EntryDTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class StudentEntryDto
{
    private String name;
    private String email;
    private String mobNo;
    private int age;
    private String country;
}
