package com.example.Student_Library_Management_System.EntryDTOs;

import com.example.Student_Library_Management_System.Enum.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookEntryDto
{
    private String name;
    private int pages;
    private Genre genre;
    private int authorId;        //Author Id also needs to be send through postman
}
