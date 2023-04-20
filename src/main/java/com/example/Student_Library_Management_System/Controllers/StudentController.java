package com.example.Student_Library_Management_System.Controllers;

import com.example.Student_Library_Management_System.EntryDTOs.StudentEntryDto;
import com.example.Student_Library_Management_System.RequestDTOs.StudentUpdateMobRequestDto;
import com.example.Student_Library_Management_System.Model.Student;
import com.example.Student_Library_Management_System.ResponseDTOs.StudentResponseDto;
import com.example.Student_Library_Management_System.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController
{
    @Autowired
    StudentService studentService;


    @PostMapping("/add")
    public String addStudent(@RequestBody StudentEntryDto studentEntryDto)
    {
        return studentService.addStudent(studentEntryDto);
    }

    @GetMapping("/getStudentByName")
    public StudentResponseDto getStudentByName(@RequestParam("name") String name)
    {
        return studentService.getStudentByName(name);
    }

    @PutMapping("/updateMobNo")
    public String updateMobNo(@RequestBody StudentUpdateMobRequestDto studentReq)
    {                                                       //we can use Request Parameter to send id, new MobNo
                                                        //when no. of parameters is large then request parameter is not recomonded.
                                                        //in that case, RequestBody is preffered
        return studentService.updateMobNo(studentReq);
    }

    @GetMapping("/findSumOfFinesPaidByStudent")
    public String findSumOfFinesPaidByStudent(@RequestParam("studentName") String studentName)
    {
        return studentService.getSumOfFinesPaid(studentName);
    }
}
