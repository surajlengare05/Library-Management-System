package com.example.Student_Library_Management_System.ControllersTests;

import com.example.Student_Library_Management_System.Controllers.AuthorController;
import com.example.Student_Library_Management_System.EntryDTOs.AuthorEntryDto;
import com.example.Student_Library_Management_System.Model.Author;
import com.example.Student_Library_Management_System.ResponseDTOs.AuthorResponseDto;
import com.example.Student_Library_Management_System.Services.AuthorService;
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

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; // write this import statement manually. It is required for get(), post(), put() etc of perform method.
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// write this import statement manually. It is required for get() of perform method.


@WebMvcTest(AuthorController.class)
class AuthorControllerTest
{
    @Autowired
    MockMvc mockMvc;
    @MockBean
    AuthorService authorService;

   /* Author author1;
    Author author2;
    List<Author> authorList = new ArrayList<>(); */


    @BeforeEach
    void setUp() {
        // we are returning different ResponseDto's from different API's of this controller.
        // hence we are not creating any object here.
        // Objects of corresponding ResponseDto's will be created in that specific test itself.
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_addAuthor() throws Exception
    {
        AuthorEntryDto author1 = new AuthorEntryDto("akash", 25, "india", 9.9);

        // code for converting object into jason string format
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJason = ow.writeValueAsString(author1);

        when(authorService.addAuthor(author1))
                .thenReturn("Author added successfully");
        mockMvc.perform(post("/author/add").contentType(MediaType.APPLICATION_JSON).content(requestJason))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test_getAuthor() throws Exception
    {
        AuthorResponseDto authorResponseDto1 = new AuthorResponseDto("jj bose", 65, 8.0, "uk");

        when(authorService.getAuthor(1))
                .thenReturn(authorResponseDto1);
        mockMvc.perform(get("/author/getAuthor?authorId=1"))
                .andDo(print()).andExpect(status().isOk());
    }
}