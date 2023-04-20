package com.example.Student_Library_Management_System.ResponseDTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AuthorResponseDto
{
    private String name;
    private int age;
    private double rating;
    private String country;

    private List<String> booksWritten;
}

