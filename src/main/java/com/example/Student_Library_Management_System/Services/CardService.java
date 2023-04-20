package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.Enum.CardStatus;
import com.example.Student_Library_Management_System.Model.Book;
import com.example.Student_Library_Management_System.Model.Card;
import com.example.Student_Library_Management_System.Model.Student;
import com.example.Student_Library_Management_System.Model.Transactions;
import com.example.Student_Library_Management_System.Repositories.CardRepository;
import com.example.Student_Library_Management_System.Repositories.StudentRepository;
import com.example.Student_Library_Management_System.Repositories.TransactionRepository;
import com.example.Student_Library_Management_System.RequestDTOs.ChangeCardStatusRequestDto;
import com.example.Student_Library_Management_System.ResponseDTOs.BookResponseForCardDto;
import com.example.Student_Library_Management_System.ResponseDTOs.CardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardService
{
    @Autowired
    CardRepository cardRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TransactionRepository transactionRepository;


    public String changeCardStatus(ChangeCardStatusRequestDto changeCardStatusRequestDto) throws Exception
    {
        String studentName = changeCardStatusRequestDto.getStudentName();
        CardStatus newStatus = changeCardStatusRequestDto.getNewStatus();

        Student student = studentRepository.findByName(studentName);
        if (student==null) {
            throw new Exception("Invalid Student");
        }

        student.getCard().setCardStatus(newStatus);
        studentRepository.save(student);  // saving parent. Card(child) will get saved by cascading

        return ("Card of "+studentName+" has been updated as "+newStatus);
    }

    public List<CardResponseDto> getCardsWithGivenStatus(String status)
    {
        List<Card> cardList = cardRepository.findCardsWithGivenStatus(status);
        List<CardResponseDto> cardsWithGivenStatusList = new ArrayList<>();

        for (Card card: cardList)
        {
            CardResponseDto cardResponseDto = new CardResponseDto();

            cardResponseDto.setId(card.getId());
            cardResponseDto.setStudentId(card.getStudent().getId());
            cardResponseDto.setStudentName(card.getStudent().getName());
            cardResponseDto.setUpdatedOn(card.getUpdatedOn());

            cardsWithGivenStatusList.add(cardResponseDto);
        }

        return cardsWithGivenStatusList;
    }

    public List<BookResponseForCardDto> getBooksIssuedToCard(int cardId)
    {
        Card card = cardRepository.findById(cardId).get();
        List<Book> booksIssued = card.getBooksIssued();
        List<BookResponseForCardDto> bookListForCard = new ArrayList<>();

        for (Book book: booksIssued)
        {
            BookResponseForCardDto bookResponseForCardDto = new BookResponseForCardDto();

            bookResponseForCardDto.setBookName(book.getName());

            int id = transactionRepository.find_Last_Successful_Issue_Transaction_Id(book.getId(), book.getCard().getId());
            Transactions transaction = transactionRepository.findById(id).get();
            LocalDate issueDate = transaction.getTransactionDate();
            LocalDate dueDate = issueDate.plusDays(15);

            bookResponseForCardDto.setIssueDate(issueDate);
            bookResponseForCardDto.setDueDate(dueDate);

            bookListForCard.add(bookResponseForCardDto);
        }

        return bookListForCard;
    }
}
