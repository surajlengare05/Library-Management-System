package com.example.Student_Library_Management_System.RequestDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueBookRequestDto
{
    private int bookId;
    private int cardId;
}
