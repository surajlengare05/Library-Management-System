package com.example.Student_Library_Management_System.RepositoriesTests;

import com.example.Student_Library_Management_System.Model.Author;
import com.example.Student_Library_Management_System.Model.Book;
import com.example.Student_Library_Management_System.Repositories.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest      // so that it uses in-memory H2 Database for Testing Purpose
class AuthorRepositoryTest
{

    @Autowired
    AuthorRepository authorRepository;

    Author author;


    @BeforeEach
    void setUp() {
       author = new Author(1, "akash", 25, "india", 9.9);
       authorRepository.save(author);
    }

    @AfterEach
    void tearDown() {
        author = null;
        authorRepository.deleteAll();
    }

    @Test
    void test_findByName()
    {
        Author sampleAuthor = authorRepository.findByName("akash");
        assertThat(sampleAuthor.getName()).isEqualTo("akash");
        assertThat(sampleAuthor.getId()).isEqualTo(1);
        // we can write like this also ->
        /* assertThat(sampleAuthor.getName()).isEqualTo(author.getName());
           assertThat(sampleAuthor.getId()).isEqualTo(author.getId()); */

        // we can check a s many attributes as we want. There is no restriction
    }
}