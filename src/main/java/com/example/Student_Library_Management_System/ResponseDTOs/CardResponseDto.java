package com.example.Student_Library_Management_System.ResponseDTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CardResponseDto
{
    private int id;
    private int studentId;
    private String studentName;
    private Date updatedOn;
}
