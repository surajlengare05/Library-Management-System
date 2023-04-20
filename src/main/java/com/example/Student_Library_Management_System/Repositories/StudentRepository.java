package com.example.Student_Library_Management_System.Repositories;

import com.example.Student_Library_Management_System.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>
{
    Student findByName(String name);   // findBy(any attribute) First we have to define a methode here &
                                        // then use it in service layer
    // we can return List of objects also.  --->
    // List<Student> findByCountry(String country);
}
