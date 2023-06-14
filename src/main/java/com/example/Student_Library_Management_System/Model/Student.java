package com.example.Student_Library_Management_System.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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




    public Student(int id, String name, String email, String mobNo, int age, String country) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobNo = mobNo;
        this.age = age;
        this.country = country;
    }
}
