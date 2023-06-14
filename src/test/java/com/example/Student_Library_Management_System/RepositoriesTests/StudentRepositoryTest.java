package com.example.Student_Library_Management_System.RepositoriesTests;

import com.example.Student_Library_Management_System.Model.Student;
import com.example.Student_Library_Management_System.Repositories.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest
{
    @Autowired
    StudentRepository studentRepository;

    Student student;


    @BeforeEach
    void setUp() {
        student = new Student(1, "rohit", "rohit@gmail.com", "12345678", 24, "india");
        studentRepository.save(student);
    }

    @AfterEach
    void tearDown() {
        student = null;
        studentRepository.deleteAll();
    }

    @Test
    void findByName()
    {
        Student sampleStudent = studentRepository.findByName("rohit");
        assertThat(sampleStudent.getId()).isEqualTo(1);
        assertThat(sampleStudent.getName()).isEqualTo("rohit");
    }
}