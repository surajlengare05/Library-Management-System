package com.example.Student_Library_Management_System.RequestDTOs;

import com.example.Student_Library_Management_System.Enum.CardStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeCardStatusRequestDto
{
   private String studentName;
   private CardStatus newStatus;
}
