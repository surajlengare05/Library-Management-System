package com.example.Student_Library_Management_System.ResponseDTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TransactionResponseForCardDto
{
    private int id;
    private String bookName;
    private String operationType;
    private int fine;
    private LocalDate transactionDate;
}
