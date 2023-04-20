package com.example.Student_Library_Management_System.ResponseDTOs;

import com.example.Student_Library_Management_System.Enum.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookResponseALLDto
{
    private String name;
    private Genre genre;
    private String authorName;
    private boolean isAvailable;
}

