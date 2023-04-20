package com.example.Student_Library_Management_System.EntryDTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorEntryDto
{
    private String name;
    private int age;
    private String country;
    private double rating;
}
