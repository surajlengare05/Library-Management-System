package com.example.Student_Library_Management_System.ControllersTests;

import com.example.Student_Library_Management_System.Controllers.CardController;
import com.example.Student_Library_Management_System.Enum.CardStatus;
import com.example.Student_Library_Management_System.Model.Card;
import com.example.Student_Library_Management_System.RequestDTOs.ChangeCardStatusRequestDto;
import com.example.Student_Library_Management_System.ResponseDTOs.BookResponseForCardDto;
import com.example.Student_Library_Management_System.ResponseDTOs.CardResponseDto;
import com.example.Student_Library_Management_System.Services.AuthorService;
import com.example.Student_Library_Management_System.Services.CardService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
class CardControllerTest
{
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CardService cardService;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_changeCardStatus() throws Exception
    {
        ChangeCardStatusRequestDto dto1 = new ChangeCardStatusRequestDto("suraj", CardStatus.ACTIVATED);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJason = ow.writeValueAsString(dto1);

        when(cardService.changeCardStatus(dto1))
                .thenReturn("Card of "+dto1.getStudentName()+" has been updated as "+dto1.getNewStatus());
        mockMvc.perform(put("/card/changeCardStatus").contentType(MediaType.APPLICATION_JSON).content(requestJason))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test_getCardsWithGivenStatus() throws Exception
    {
        CardResponseDto card1 = new CardResponseDto(1, 1, "suraj", new Date(1999, 9, 20));
        CardResponseDto card2 = new CardResponseDto(2, 2, "kunal", new Date(1999, 9, 20));
        List<CardResponseDto> cardList = new ArrayList<>();
        cardList.add(card1);
        cardList.add(card2);

        when(cardService.getCardsWithGivenStatus("ACTIVATED"))
                .thenReturn(cardList);
        mockMvc.perform(get("/card/getCardsWithGivenStatus?status=ACTIVATED"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void test_getBooksIssuedToCard() throws Exception
    {
        BookResponseForCardDto book1 = new BookResponseForCardDto("abc", LocalDate.of(2023,11,20), LocalDate.of(2023,11,30));
        BookResponseForCardDto book2 = new BookResponseForCardDto("xyz", LocalDate.of(2023,10,20), LocalDate.of(2023,10,30));
        List<BookResponseForCardDto> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);

        when(cardService.getBooksIssuedToCard(1))
                .thenReturn(bookList);
        mockMvc.perform(get("/card/getBooksIssuedToCard?cardId=1"))
                .andDo(print()).andExpect(status().isOk());
    }
}