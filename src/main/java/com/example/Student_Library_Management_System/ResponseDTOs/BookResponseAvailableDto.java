package com.example.Student_Library_Management_System.ResponseDTOs;

import com.example.Student_Library_Management_System.Enum.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseAvailableDto
{
    private String name;
    private String authorName;
    private Genre genre;
}
