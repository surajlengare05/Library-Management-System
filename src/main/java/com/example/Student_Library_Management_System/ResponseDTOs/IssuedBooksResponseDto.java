package com.example.Student_Library_Management_System.ResponseDTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class IssuedBooksResponseDto
{
    private int bookId;
    private String bookName;
    private int cardId;
    private String studentName;
    private LocalDate issueDate;
    private LocalDate dueDate;
}
