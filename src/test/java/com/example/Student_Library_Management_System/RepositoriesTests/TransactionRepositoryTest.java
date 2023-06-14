package com.example.Student_Library_Management_System.RepositoriesTests;

import com.example.Student_Library_Management_System.Enum.CardStatus;
import com.example.Student_Library_Management_System.Enum.Genre;
import com.example.Student_Library_Management_System.Enum.TransactionStatus;
import com.example.Student_Library_Management_System.Model.Book;
import com.example.Student_Library_Management_System.Model.Card;
import com.example.Student_Library_Management_System.Model.Transactions;
import com.example.Student_Library_Management_System.Repositories.BookRepository;
import com.example.Student_Library_Management_System.Repositories.CardRepository;
import com.example.Student_Library_Management_System.Repositories.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransactionRepositoryTest
{
    @Autowired
    TransactionRepository transactionRepository;

    Transactions transaction;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_findTransactionsForBookAndCard()
    {

    }

    @Test
    void test_find_Last_Successful_Issue_Transaction_Id()
    {

    }

    @Test
    void test_findSumOfFinesPaidByStudent()
    {

    }

    @Test
    void test_findCountForEachBook()
    {

    }
}