package com.example.Student_Library_Management_System.RepositoriesTests;

import com.example.Student_Library_Management_System.Enum.CardStatus;
import com.example.Student_Library_Management_System.Model.Card;
import com.example.Student_Library_Management_System.Repositories.CardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CardRepositoryTest
{
    @Autowired
    CardRepository cardRepository;

    Card card;


    @BeforeEach
    void setUp() {
        card = new Card(1, CardStatus.ACTIVATED, new Date(2023,12,20), new Date(2023,12,22));
        cardRepository.save(card);
    }

    @AfterEach
    void tearDown() {
        card = null;
        cardRepository.deleteAll();
    }

    @Test
    void test_findCardById()
    {
        Card sampleCard = cardRepository.findCardById(1);
        assertThat(sampleCard.getId()).isEqualTo(1);
        assertThat(sampleCard.getCardStatus()).isEqualTo(CardStatus.ACTIVATED);
    }

    @Test
    void test_findCardsWithGivenStatus()
    {
        List<Card> cardList = cardRepository.findCardsWithGivenStatus("ACTIVATED");
        assertThat(cardList.size()).isEqualTo(1);
        assertThat(cardList.get(0).getId()).isEqualTo(1);
        assertThat(cardList.get(0).getCardStatus()).isEqualTo(CardStatus.ACTIVATED);
    }
}