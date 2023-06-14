package com.example.Student_Library_Management_System.RepositoriesTests;

import com.example.Student_Library_Management_System.Enum.Genre;
import com.example.Student_Library_Management_System.Model.Book;
import com.example.Student_Library_Management_System.Repositories.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest
{
    @Autowired
    BookRepository bookRepository;

    Book book;


    @BeforeEach
    void setUp() {
        book = new Book(1, "abc", 70, Genre.ROMANTIC, true);
        bookRepository.save(book);
    }

    @AfterEach
    void tearDown() {
        book = null;
        bookRepository.deleteAll();
    }

    @Test
    void test_findBookById()
    {
        Book sampleBook = bookRepository.findBookById(1);
        assertThat(sampleBook.getName()).isEqualTo("abc");
        assertThat(sampleBook.getPages()).isEqualTo(70);
        assertThat(sampleBook.getGenre()).isEqualTo(Genre.ROMANTIC);
        assertThat(sampleBook.isIssued()).isTrue();
    }

    @Test
    void test_findByName()
    {
        Book sampleBook = bookRepository.findByName("abc");
        assertThat(sampleBook.getId()).isEqualTo(1);
        assertThat(sampleBook.getPages()).isEqualTo(70);
    }

    @Test
    void test_findAllBooksFromGenre()
    {
        List<Book> bookList = bookRepository.findAllBooksFromGenre("ROMANTIC");
        assertThat(bookList.size()).isEqualTo(1);
        assertThat(bookList.get(0).getName()).isEqualTo("abc");
    }

    @Test
    void test_findAllAvailableBooks()
    {
        List<Book> bookList = bookRepository.findAllAvailableBooks();
        assertThat(bookList.isEmpty()).isTrue();
        // OR -> assertThat(bookList.size()).isEqualTo(0);
    }

    @Test
    void test_findAllIssuedBooks()
    {
        List<Book> bookList = bookRepository.findAllIssuedBooks();
        assertThat(bookList.isEmpty()).isFalse();
         //assertThat(bookList.size()).isEqualTo(1);
        assertThat(bookList.get(0).getName()).isEqualTo("abc");
    }
}