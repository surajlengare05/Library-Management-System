package com.example.Student_Library_Management_System.ResponseDTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookResponseForGenreDto
{
    private String name;
    private String authorName;
    private boolean isAvailable;
}
