package com.example.Student_Library_Management_System.ServicesTests;

import com.example.Student_Library_Management_System.EntryDTOs.BookEntryDto;
import com.example.Student_Library_Management_System.Enum.Genre;
import com.example.Student_Library_Management_System.Model.Author;
import com.example.Student_Library_Management_System.Model.Book;
import com.example.Student_Library_Management_System.Repositories.AuthorRepository;
import com.example.Student_Library_Management_System.Repositories.BookRepository;
import com.example.Student_Library_Management_System.Repositories.TransactionRepository;
import com.example.Student_Library_Management_System.ResponseDTOs.BookResponseALLDto;
import com.example.Student_Library_Management_System.Services.AuthorService;
import com.example.Student_Library_Management_System.Services.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookServiceTest
{
    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorRepository authorRepository;
    @Mock
    TransactionRepository transactionRepository;

    BookService bookService;
    AutoCloseable autoCloseable;
    Book book1;
    Author author1;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, authorRepository, transactionRepository);
        book1 = new Book(1, "abc", 70, Genre.ROMANTIC, true);
        author1 = new Author(1, "akash", 25, "india", 9.9);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void addBook()
    {
        mock(Book.class);
        mock(BookRepository.class);
        mock(AuthorRepository.class);

        BookEntryDto bookEntry1 = new BookEntryDto("abc", 40, Genre.EDUCATION, 1);

        when(authorRepository.findById(1))
                .thenReturn(Optional.ofNullable(author1));
        when(authorRepository.save(author1))
                .thenReturn(author1);
        assertThat(bookService.addBook(bookEntry1)).isEqualTo("Book added successfully");
    }

    @Test
    void getAllBooksOfAuthor()
    {
        mock(Book.class);
        mock(AuthorRepository.class);

        when(authorRepository.findByName("akash"))
                .thenReturn(author1);
        assertThat(bookService.getAllBooksOfAuthor("akash").size()).isEqualTo(0);
    }

    @Test
    void getAllBooksFromGenre()
    {
        mock(Book.class);
        mock(BookRepository.class);

        List<Book> bookList = new ArrayList<>();
        when(bookRepository.findAllBooksFromGenre("EDUCATIONAL"))
                .thenReturn(bookList);
        assertThat(bookService.getAllBooksFromGenre("EDUCATIONAL").size()).isEqualTo(0);
    }

}