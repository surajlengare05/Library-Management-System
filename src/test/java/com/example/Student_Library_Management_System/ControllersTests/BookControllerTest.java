package com.example.Student_Library_Management_System.ControllersTests;

import com.example.Student_Library_Management_System.Controllers.BookController;
import com.example.Student_Library_Management_System.EntryDTOs.BookEntryDto;
import com.example.Student_Library_Management_System.Enum.Genre;
import com.example.Student_Library_Management_System.Model.Book;
import com.example.Student_Library_Management_System.ResponseDTOs.*;
import com.example.Student_Library_Management_System.Services.AuthorService;
import com.example.Student_Library_Management_System.Services.BookService;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest
{
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BookService bookService;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_addBook() throws Exception
    {
        BookEntryDto book1 = new BookEntryDto("abc", 40, Genre.EDUCATION, 1);

        // code for converting object into jason string format
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJason = ow.writeValueAsString(book1);

        when(bookService.addBook(book1))
                .thenReturn("Book added successfully");
        mockMvc.perform(post("/book/add").contentType(MediaType.APPLICATION_JSON).content(requestJason))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test_getAllBooksOfAuthor() throws Exception
    {
        BookResponseForAuthorDto book1 = new BookResponseForAuthorDto("maths", Genre.EDUCATION, true);
        BookResponseForAuthorDto book2 = new BookResponseForAuthorDto("xyz", Genre.ROMANTIC, true);
        List<BookResponseForAuthorDto> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);

        when(bookService.getAllBooksOfAuthor("suresh"))
                .thenReturn(bookList);
        mockMvc.perform(get("/book/getAllBooksOfAuthor?authorName=suresh"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test_getAllBooksFromGenre() throws Exception
    {
        BookResponseForGenreDto book1 = new BookResponseForGenreDto("abc", "jj roy", true);
        BookResponseForGenreDto book2 = new BookResponseForGenreDto("xyz", "bhagat", false);
        List<BookResponseForGenreDto> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);

        when(bookService.getAllBooksFromGenre("educational"))
                .thenReturn(bookList);
        mockMvc.perform(get("/book/getAllBooksFromGenre?genre=educational"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test_getAllBooks() throws Exception
    {
        BookResponseALLDto book1 = new BookResponseALLDto("abc", Genre.MOTIVATIONAL, "smith", true);
        BookResponseALLDto book2 = new BookResponseALLDto("xyz", Genre.FICTIONAL, "john", false);
        List<BookResponseALLDto> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);

        when(bookService.getAllBooks())
                .thenReturn(bookList);
        mockMvc.perform(get("/book/getAllBooks"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test_getAllAvailableBooks() throws Exception
    {
        BookResponseAvailableDto book1 = new BookResponseAvailableDto("abc", "smith", Genre.FICTIONAL);
        BookResponseAvailableDto book2 = new BookResponseAvailableDto("xyz", "john", Genre.MOTIVATIONAL);
        List<BookResponseAvailableDto> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);

        when(bookService.getAllAvailableBooks())
                .thenReturn(bookList);
        mockMvc.perform(get("/book/getAllAvailableBooks"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test_getIssuedBooksDetails() throws Exception
    {
        IssuedBooksResponseDto book1 = new IssuedBooksResponseDto(1, "abc", 1, "suraj", LocalDate.of(2023, 10, 28), LocalDate.of(2023, 11, 28));
        IssuedBooksResponseDto book2 = new IssuedBooksResponseDto(2, "xyz", 2, "kunal", LocalDate.of(2023, 11, 25), LocalDate.of(2023, 12, 25));
        List<IssuedBooksResponseDto> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);

        when(bookService.getIssuedBooksDetails())
                .thenReturn(bookList);
        mockMvc.perform(get("/book/getIssuedBooksDetails"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test_getMostDemandingBooks() throws Exception
    {
        List<String> bookList = new ArrayList<>();
        bookList.add("abcd");
        bookList.add("xyzu");

        when(bookService.getMostDemandingBooks())
                .thenReturn(bookList);
        mockMvc.perform(get("/book/getMostDemandingBook"))
                .andDo(print()).andExpect(status().isOk());
    }
}