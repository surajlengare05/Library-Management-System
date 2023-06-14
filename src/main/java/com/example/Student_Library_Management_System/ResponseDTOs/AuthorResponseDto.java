package com.example.Student_Library_Management_System.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponseDto
{
    private String name;
    private int age;
    private double rating;
    private String country;

    private List<String> booksWritten;


    public AuthorResponseDto(String name, int age, double rating, String country) {
        this.name = name;
        this.age = age;
        this.rating = rating;
        this.country = country;
    }
}

