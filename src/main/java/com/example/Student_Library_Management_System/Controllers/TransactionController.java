package com.example.Student_Library_Management_System.Controllers;

import com.example.Student_Library_Management_System.RequestDTOs.IssueBookRequestDto;
import com.example.Student_Library_Management_System.RequestDTOs.ReturnBookRequestDto;
import com.example.Student_Library_Management_System.ResponseDTOs.TransactionResponseDto;
import com.example.Student_Library_Management_System.ResponseDTOs.TransactionResponseForBookDto;
import com.example.Student_Library_Management_System.ResponseDTOs.TransactionResponseForCardDto;
import com.example.Student_Library_Management_System.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController
{
    @Autowired
    TransactionService transactionService;


    @PostMapping("/issueBook")
    public String issueBook(@RequestBody IssueBookRequestDto issueBookRequestDto)
    {
        try {
            return transactionService.issueBook(issueBookRequestDto);
        }
        catch (Exception e) {
            return (e.getMessage());
        }
    }

    @GetMapping("/getTransactionsForCardAndBook")             // RequestDto is not created. sending parameters through @requestParam
    public List<TransactionResponseDto> getTransactionsForCardAndBook(@RequestParam("bookId") int bookId, @RequestParam("cardId") int cardId)
    {
        return transactionService.getTransactionsForCardAndBook(bookId,cardId);
    }

    @PostMapping("/returnBook")
    public String returnBook(@RequestBody ReturnBookRequestDto returnBookRequestDto)
    {
        try {
            return transactionService.returnBook(returnBookRequestDto);
        }
        catch (Exception e) {
            return (e.getMessage());
        }
    }

    // only successful transactions
    @GetMapping("/getListOfTransactionsForCard")
    public List<TransactionResponseForCardDto> getListOfTransactionsForCard(@RequestParam("cardId") int cardId)
    {
        return transactionService.getListOfTransactionsForCard(cardId);
    }
    @GetMapping("/getListOfTransactionsForBook")
    public List<TransactionResponseForBookDto> getListOfTransactionsForBook(@RequestParam("bookName") String bookName)
    {
        return transactionService.getListOfTransactionsForBook(bookName);
    }
}
