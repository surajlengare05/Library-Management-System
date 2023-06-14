package com.example.Student_Library_Management_System.Model;

import com.example.Student_Library_Management_System.Enum.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="card")
public class Card
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(value = EnumType.STRING)
    private CardStatus cardStatus;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    // unidirectional mapping
    // parent-> Student, child-> Card
    @OneToOne
    @JoinColumn          // adds foreign key to current table ->// by default considers parent entity's primary key
    private Student student;

    // Bidirectional mapping
    // parent-> Card, child-> Book
    // Card is also a parent wrt. Book
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<Book> booksIssued = new ArrayList<>(); //can be initialized inside constructor also;

    // Bidirectional mapping
    // parent-> Card, child-> Transaction
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<Transactions> transactionList = new ArrayList<>();




    public Card(int id, CardStatus cardStatus, Date createdOn, Date updatedOn) {
        this.id = id;
        this.cardStatus = cardStatus;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }
}

