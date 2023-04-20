package com.example.Student_Library_Management_System.ResponseDTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BookResponseForCardDto
{
    private String bookName;
    private LocalDate issueDate;
    private LocalDate DueDate;
}
