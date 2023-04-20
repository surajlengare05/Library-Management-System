package com.example.Student_Library_Management_System.Controllers;

import com.example.Student_Library_Management_System.RequestDTOs.ChangeCardStatusRequestDto;
import com.example.Student_Library_Management_System.ResponseDTOs.BookResponseForCardDto;
import com.example.Student_Library_Management_System.ResponseDTOs.CardResponseDto;
import com.example.Student_Library_Management_System.Services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController
{
    @Autowired
    CardService cardService;


    // new card will get added automatically when a new student is added

    @PutMapping("/changeCardStatus")
    public String changeCardStatus(@RequestBody ChangeCardStatusRequestDto changeCardStatusRequestDto)
    {
        try {
           return cardService.changeCardStatus(changeCardStatusRequestDto);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/getCardsWithGivenStatus")
    public List<CardResponseDto> getCardsWithGivenStatus(@RequestParam("status") String status)
    {
        return cardService.getCardsWithGivenStatus(status);
    }

    @GetMapping("/getBooksIssuedToCard")
    public List<BookResponseForCardDto> getBooksIssuedToCard(@RequestParam("cardId") int cardId)
    {
        return cardService.getBooksIssuedToCard(cardId);
    }
}
