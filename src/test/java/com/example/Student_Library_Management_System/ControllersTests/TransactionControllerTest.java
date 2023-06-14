package com.example.Student_Library_Management_System.ControllersTests;

import com.example.Student_Library_Management_System.Controllers.TransactionController;
import com.example.Student_Library_Management_System.EntryDTOs.BookEntryDto;
import com.example.Student_Library_Management_System.Enum.Genre;
import com.example.Student_Library_Management_System.Enum.TransactionStatus;
import com.example.Student_Library_Management_System.Model.Transactions;
import com.example.Student_Library_Management_System.RequestDTOs.IssueBookRequestDto;
import com.example.Student_Library_Management_System.RequestDTOs.ReturnBookRequestDto;
import com.example.Student_Library_Management_System.ResponseDTOs.TransactionResponseDto;
import com.example.Student_Library_Management_System.ResponseDTOs.TransactionResponseForBookDto;
import com.example.Student_Library_Management_System.ResponseDTOs.TransactionResponseForCardDto;
import com.example.Student_Library_Management_System.Services.AuthorService;
import com.example.Student_Library_Management_System.Services.TransactionService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest
{
    @Autowired
    MockMvc mockMvc;
    @MockBean
    TransactionService transactionService;



    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_issueBook() throws Exception
    {
        IssueBookRequestDto issue1 = new IssueBookRequestDto(1, 2);

        // code for converting object into jason string format
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJason = ow.writeValueAsString(issue1);

        when(transactionService.issueBook(issue1))
                .thenReturn("Book issued successfully");
        mockMvc.perform(post("/transactions/issueBook").contentType(MediaType.APPLICATION_JSON).content(requestJason))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test_getTransactionsForCardAndBook() throws Exception
    {
        TransactionResponseDto transaction1 = new TransactionResponseDto(1, "ISSUE", TransactionStatus.SUCCESSFUL, 0, LocalDate.of(2023,9,19));
        TransactionResponseDto transaction2 = new TransactionResponseDto(2, "RETURN", TransactionStatus.SUCCESSFUL, 50, LocalDate.of(2023,10,20));
        List<TransactionResponseDto> transactionsList = new ArrayList<>();
        transactionsList.add(transaction1);
        transactionsList.add(transaction2);

        when(transactionService.getTransactionsForCardAndBook(1, 2))
                .thenReturn(transactionsList);
        mockMvc.perform(get("/transactions/getTransactionsForCardAndBook?bookId=1&cardId=2"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test_returnBook() throws Exception
    {
        ReturnBookRequestDto return1 = new ReturnBookRequestDto(1, 2);

        // code for converting object into jason string format
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJason = ow.writeValueAsString(return1);

        when(transactionService.returnBook(return1))
                .thenReturn("Book returned successfully. Your fine = 80 Rs.");
        mockMvc.perform(post("/transactions/returnBook").contentType(MediaType.APPLICATION_JSON).content(requestJason))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test_getListOfTransactionsForCard() throws Exception
    {
        TransactionResponseForCardDto transaction1 = new TransactionResponseForCardDto(1, "abc", "ISSUE", 0, LocalDate.of(2023,10,20));
        TransactionResponseForCardDto transaction2 = new TransactionResponseForCardDto(2, "xyz", "ISSUE", 0, LocalDate.of(2023,11,20));
        List<TransactionResponseForCardDto> transactionList = new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transaction2);

        when(transactionService.getListOfTransactionsForCard(1))
                .thenReturn(transactionList);
        mockMvc.perform(get("/transactions/getListOfTransactionsForCard?cardId=1"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test_getListOfTransactionsForBook() throws Exception
    {
        TransactionResponseForBookDto transaction1 = new TransactionResponseForBookDto(1, 2, "ISSUE", 0, LocalDate.of(2023,10,20));
        TransactionResponseForBookDto transaction2 = new TransactionResponseForBookDto(3, 4, "ISSUE", 0, LocalDate.of(2023,11,20));
        List<TransactionResponseForBookDto> transactionList = new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transaction2);

        when(transactionService.getListOfTransactionsForBook("abc"))
                .thenReturn(transactionList);
        mockMvc.perform(get("/transactions/getListOfTransactionsForBook?bookName=abc"))
                .andDo(print()).andExpect(status().isOk());
    }
}