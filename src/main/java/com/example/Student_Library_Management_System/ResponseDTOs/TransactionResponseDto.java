package com.example.Student_Library_Management_System.ResponseDTOs;

import com.example.Student_Library_Management_System.Enum.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto
{
    private int id;
    private String operationType;
    private TransactionStatus transactionStatus;
    private int fine;
    private LocalDate transactionDate;
}
