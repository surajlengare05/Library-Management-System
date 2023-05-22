package com.example.Student_Library_Management_System.Model;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="student")
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true)
    private String email;

    private String mobNo;
    private int age;
    private String country;

    // Bidirectional mapping -> parent=Student, child=Card
    //(mappedBy="name of the variable of Parent Entity that we have written in child calss Foreign key attribaute")
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private Card card;

}
