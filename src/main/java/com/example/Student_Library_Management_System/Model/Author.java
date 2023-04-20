package com.example.Student_Library_Management_System.Model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "author")
public class Author
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int age;
    private String country;
    private double rating;

                     // Bidirectional mapping -> parent=Author, child=Book
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> booksWritten = new ArrayList<>(); //can be initialized inside constructor also
    // this will not be added in Parent-table(Author). but will be present in database for our refference

}
