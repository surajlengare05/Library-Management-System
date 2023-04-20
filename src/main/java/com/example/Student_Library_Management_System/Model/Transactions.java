package com.example.Student_Library_Management_System.Model;

import com.example.Student_Library_Management_System.Enum.TransactionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name="transaction")
public class Transactions
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;      // primary key

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    private String transactionId;        // Random number generation
    private int fine;
    private boolean isIssueOperation;

    @CreationTimestamp
    private LocalDate transactionDate;

                    // unidirectional mapping
                        // parent-> Book, child->Transaction
    @ManyToOne
    @JoinColumn
    private Book book;

    @ManyToOne            // parent-> Card, child->Transaction
    @JoinColumn
    private Card card;


}
