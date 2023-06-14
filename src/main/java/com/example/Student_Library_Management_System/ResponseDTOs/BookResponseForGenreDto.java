package com.example.Student_Library_Management_System.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseForGenreDto
{
    private String name;
    private String authorName;
    private boolean isAvailable;
}
