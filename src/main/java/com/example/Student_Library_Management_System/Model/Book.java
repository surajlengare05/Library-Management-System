package com.example.Student_Library_Management_System.Model;

import com.example.Student_Library_Management_System.Enum.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int pages;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    private boolean isIssued;

                    // Unidirectional mapping -> parent=Author, child=Book
    @ManyToOne
    @JoinColumn
    private Author author;

                    // Unidirectional mapping -> parent=Card, child=Book
                    // Book is also a child wrt. Card
     @ManyToOne
     @JoinColumn
     private Card card;

                       // Bidirectional mapping
                        // parent-> Book, child-> Transaction
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Transactions> listOfTransactions = new ArrayList<>();
    // since oneToMany mapping, hence List is required to store many childs




    public Book(int id, String name, int pages, Genre genre, boolean isIssued) {
        this.id = id;
        this.name = name;
        this.pages = pages;
        this.genre = genre;
        this.isIssued = isIssued;
    }


}

