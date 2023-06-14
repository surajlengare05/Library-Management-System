package com.example.Student_Library_Management_System.ControllersTests;

import com.example.Student_Library_Management_System.Controllers.StudentController;
import com.example.Student_Library_Management_System.EntryDTOs.StudentEntryDto;
import com.example.Student_Library_Management_System.Model.Student;
import com.example.Student_Library_Management_System.RequestDTOs.StudentUpdateMobRequestDto;
import com.example.Student_Library_Management_System.ResponseDTOs.StudentResponseDto;
import com.example.Student_Library_Management_System.Services.AuthorService;
import com.example.Student_Library_Management_System.Services.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest
{
    @Autowired
    MockMvc mockMvc;
    @MockBean
    StudentService studentService;



    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_addStudent() throws Exception
    {
        StudentEntryDto student1 = new StudentEntryDto("suraj", "suraj@gmail.com", "12345678", 24, "india" );

        // code for converting object into jason string format
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJason = ow.writeValueAsString(student1);

        when(studentService.addStudent(student1))
                .thenReturn("Student & Card added successfully");
        mockMvc.perform(post("/student/add").contentType(MediaType.APPLICATION_JSON).content(requestJason))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test_getStudentByName() throws Exception
    {
        StudentResponseDto student1 = new StudentResponseDto(1, "kunal", "kunal@gmail.com", "01010101", 28, "london", 1);

        when(studentService.getStudentByName("kunal"))
                .thenReturn(student1);
        mockMvc.perform(get("/student/getStudentByName?name=kunal"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test_updateMobNo() throws Exception
    {
        StudentUpdateMobRequestDto dto1 = new StudentUpdateMobRequestDto(1, "00000000");

        // code for converting object into jason string format
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJason = ow.writeValueAsString(dto1);

        when(studentService.updateMobNo(dto1))
                .thenReturn("Mobile no. updated successfully");
        mockMvc.perform(put("/student/updateMobNo").contentType(MediaType.APPLICATION_JSON).content(requestJason))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test_findSumOfFinesPaidByStudent() throws Exception
    {
        when(studentService.getSumOfFinesPaid("rohit"))
                .thenReturn("Total fine paid till now is: 10 RS");
        mockMvc.perform(get("/student/findSumOfFinesPaidByStudent?studentName=rohit"))
                .andDo(print()).andExpect(status().isOk());
    }
}