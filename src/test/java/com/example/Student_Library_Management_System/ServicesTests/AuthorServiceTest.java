package com.example.Student_Library_Management_System.ServicesTests;

import com.example.Student_Library_Management_System.EntryDTOs.AuthorEntryDto;
import com.example.Student_Library_Management_System.Model.Author;
import com.example.Student_Library_Management_System.Repositories.AuthorRepository;
import com.example.Student_Library_Management_System.ResponseDTOs.AuthorResponseDto;
import com.example.Student_Library_Management_System.Services.AuthorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthorServiceTest
{
    @Mock
    AuthorRepository authorRepository;
    AuthorService authorService;
    AutoCloseable autoCloseable;
    Author author;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        authorService = new AuthorService(authorRepository);
        author = new Author(1, "akash", 25, "india", 9.9);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void addAuthor()
    {
        mock(Author.class);
        mock(AuthorRepository.class);

        AuthorEntryDto author1 = new AuthorEntryDto("sameer", 35, "india", 7.0);

        when(authorRepository.save(author))
                .thenReturn(author);
        assertThat(authorService.addAuthor(author1)).isEqualTo("Author added successfully");
    }

    @Test
    void getAuthor()
    {
        mock(Author.class);
        mock(AuthorRepository.class);

        when(authorRepository.findById(1)).thenReturn(Optional.ofNullable(author));
                                                // findById().get() method might return entity or might return null.
        assertThat(authorService.getAuthor(1).getName()).isEqualTo("akash");
    }
}