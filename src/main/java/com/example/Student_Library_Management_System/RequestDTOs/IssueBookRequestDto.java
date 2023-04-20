package com.example.Student_Library_Management_System.RequestDTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IssueBookRequestDto
{
    private int bookId;
    private int cardId;
}
