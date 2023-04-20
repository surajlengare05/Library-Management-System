package com.example.Student_Library_Management_System.RequestDTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentUpdateMobRequestDto
{
    private int id;
    private String mobNo;
}
