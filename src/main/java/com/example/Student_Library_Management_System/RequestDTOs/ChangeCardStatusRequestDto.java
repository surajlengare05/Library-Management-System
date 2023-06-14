package com.example.Student_Library_Management_System.RequestDTOs;

import com.example.Student_Library_Management_System.Enum.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeCardStatusRequestDto
{
   private String studentName;
   private CardStatus newStatus;
}
