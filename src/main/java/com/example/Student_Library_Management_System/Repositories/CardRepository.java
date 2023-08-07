package com.example.Student_Library_Management_System.Repositories;


import com.example.Student_Library_Management_System.Model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer>
{
    @Query(value = "select * from card where id=:cardId", nativeQuery = true)
    Card findCardById(int cardId);
    // used in issueBook methode (Transaction Service)

    @Query(value = "select * from card where card_status=:status", nativeQuery = true)
    List<Card> findCardsWithGivenStatus(String status);
    // used in getCardsWithGivenStatus methode (Card Service)
}
