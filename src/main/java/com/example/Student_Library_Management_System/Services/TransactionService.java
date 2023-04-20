package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.RequestDTOs.IssueBookRequestDto;
import com.example.Student_Library_Management_System.Enum.CardStatus;
import com.example.Student_Library_Management_System.Enum.TransactionStatus;
import com.example.Student_Library_Management_System.Model.Book;
import com.example.Student_Library_Management_System.Model.Card;
import com.example.Student_Library_Management_System.Model.Transactions;
import com.example.Student_Library_Management_System.Repositories.BookRepository;
import com.example.Student_Library_Management_System.Repositories.CardRepository;
import com.example.Student_Library_Management_System.Repositories.TransactionRepository;
import com.example.Student_Library_Management_System.RequestDTOs.ReturnBookRequestDto;
import com.example.Student_Library_Management_System.ResponseDTOs.TransactionResponseDto;
import com.example.Student_Library_Management_System.ResponseDTOs.TransactionResponseForBookDto;
import com.example.Student_Library_Management_System.ResponseDTOs.TransactionResponseForCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transaction;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService
{
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CardRepository cardRepository;


    public String issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception
    {
        int book_Id = issueBookRequestDto.getBookId();
        int card_Id = issueBookRequestDto.getCardId();

        Book book = bookRepository.findBookById(book_Id);   // a default methode findById().get() wont work if we dont get any object from Db
        Card card = cardRepository.findCardById(card_Id);   // since null.get() will give o/p -> No such value
                                                            // hence created our own methodes in book & card Repositories and using them here

        Transactions transaction = new Transactions();

        transaction.setIssueOperation(true);
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setBook(book);
        transaction.setCard(card);

        // Check for validations
        if (book==null || card==null || book.isIssued()==true || card.getCardStatus() != CardStatus.ACTIVATED)
        {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);

            if (book==null) {
                throw new Exception("Invalid Book..!");
            }
            if (card==null) {
                throw  new Exception("Invalid Card..!");
            }
            if (book.isIssued()==true) {
                throw new Exception("SORRY. Book has already been issued.");
            }
            if (card.getCardStatus() != CardStatus.ACTIVATED) {
                throw  new Exception("SORRY. Card is not ACTIVATED.");
            }
        }

        // We have reached a success case now.
        transaction.setTransactionStatus(TransactionStatus. SUCCESSFUL);


        // set attributes of book
        book.setIssued(true);
        book.setCard(card);
                    // adding current transaction to arraylist in Book(parent)
        List<Transactions> listOfTransactionForBook = book.getListOfTransactions();
        listOfTransactionForBook.add(transaction);
        book.setListOfTransactions(listOfTransactionForBook);


        // set attributes of card
                    // adding current book to arraylist in Card
        List<Book> issuedBooksForCard = card.getBooksIssued();
        issuedBooksForCard.add(book);
        card.setBooksIssued(issuedBooksForCard);
                    // adding current transaction to arraylist in Card(parent)
        List<Transactions> transactionsListForCard = card.getTransactionList();
        transactionsListForCard.add(transaction);
        card.setTransactionList(transactionsListForCard);


        // save the parent.
        cardRepository.save(card);
        // card is a parent of book & transactions both
        // automatically by cascading : book and transaction will be saved.

        return ("Book issued successfully");
    }

    public List<TransactionResponseDto> getTransactionsForCardAndBook(int bookId, int cardId)
    {
        // there can be more than 1 transaction held for given BOOK & Card
        List<Transactions> transactionsList = transactionRepository.findTransactionsForBookAndCard(bookId,cardId);
        List<TransactionResponseDto> transactionResponseDtoList = new ArrayList<>();

        for (Transactions transaction: transactionsList)
        {
            TransactionResponseDto transactionResponseDto = new TransactionResponseDto();

            transactionResponseDto.setId(transaction.getId());
            transactionResponseDto.setTransactionStatus(transaction.getTransactionStatus());
            if (transaction.isIssueOperation()==true) {
                transactionResponseDto.setOperationType("ISSUE");
            }
            else {
                transactionResponseDto.setOperationType("RETURN");
            }
            transactionResponseDto.setTransactionDate(transaction.getTransactionDate());
            transactionResponseDto.setFine(transaction.getFine());

            transactionResponseDtoList.add(transactionResponseDto);
        }

        return transactionResponseDtoList;
    }

    public String returnBook(ReturnBookRequestDto returnBookRequestDto) throws Exception
    {
        int book_Id = returnBookRequestDto.getBookId();
        int card_Id = returnBookRequestDto.getCardId();

        Book book = bookRepository.findBookById(book_Id);    // a default methode findById().get() wont work if we dont get any object from Db
        Card card = cardRepository.findCardById(card_Id);    // since null.get() will give o/p -> No such value
                                                             // hence created our own methodes in book & card Repositories and using them here

        Transactions transaction = new Transactions();

        transaction.setIssueOperation(false);                       // since it is return book operation
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setBook(book);
        transaction.setCard(card);

        // check for validations
        if (book==null || card==null || card.getBooksIssued().contains(book)==false)
        {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);

            if (book==null) {
                throw new Exception("Invalid Book..!");
            }
            if (card==null) {
                throw  new Exception("Invalid Card..!");
            }
            if (card.getBooksIssued().contains(book)==false) {
                throw new Exception("SORRY. Currently this book has not issued to this card.");
            }
        }

        // We have reached a success case now.
        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);

        // update attributes of book
        book.setIssued(false);
        book.setCard(null);
        // remove book from arraylist booksIssued in card
        card.getBooksIssued().remove(book);

        // Now calculate Fine
        int fine = calculateFine(book_Id, card_Id);
        transaction.setFine(fine);

        book.getListOfTransactions().add(transaction);  // adding current transaction to arraylist in Book(parent)
        card.getTransactionList().add(transaction);    // adding current transaction to arraylist in Card(parent)

        // save the parent.
        cardRepository.save(card);
        // card is a parent of book & transactions both
        // automatically by cascading : book and transaction will be saved.

        return ("Book returned successfully. Your fine = "+fine+" Rs.");
    }

    public int calculateFine(int book_Id, int card_Id)
    {
        int id = transactionRepository.find_Last_Successful_Issue_Transaction_Id(book_Id, card_Id);
        Transactions lastTransaction = transactionRepository.findById(id).get();

        /*   Steps to convert 'Date' datatype to 'LocalDate' datatype ---->
        Timestamp issueTimeStamp = (Timestamp) lastTransaction.getTransactionDate();
        LocalDateTime issueLocalDateTime = issueTimeStamp.toLocalDateTime();
        LocalDate issueLocalDate = issueLocalDateTime.toLocalDate();  */

        LocalDate issueLocalDate = lastTransaction.getTransactionDate();

        LocalDate today = LocalDate.now();
        long totalDaysInLong = ChronoUnit.DAYS.between(issueLocalDate, today);
        int totalDays = (int) totalDaysInLong;

        int fine = 0;
        if (today.isAfter(issueLocalDate)==true && totalDays>15)
        {
            fine = (totalDays-15) * 2;     // Rs.2 fine per day after 15 days
        }
        return fine;
    }

    // only successful transactions
    public List<TransactionResponseForCardDto> getListOfTransactionsForCard(int cardId)
    {
        Card card = cardRepository.findById(cardId).get();
        List<Transactions> transactionList = card.getTransactionList();
        List<TransactionResponseForCardDto> transactionsListForCard = new ArrayList<>();

        for (Transactions transaction: transactionList)
        {
            if (transaction.getTransactionStatus() == TransactionStatus.SUCCESSFUL)
            {
                TransactionResponseForCardDto transactionResponseForCardDto = new TransactionResponseForCardDto();

                transactionResponseForCardDto.setId(transaction.getId());
                transactionResponseForCardDto.setBookName(transaction.getBook().getName());
                if (transaction.isIssueOperation()==true) {
                    transactionResponseForCardDto.setOperationType("ISSUE");
                }
               else {
                    transactionResponseForCardDto.setOperationType("RETURN");
                }
               transactionResponseForCardDto.setFine(transaction.getFine());
               transactionResponseForCardDto.setTransactionDate(transaction.getTransactionDate());

               transactionsListForCard.add(transactionResponseForCardDto);
            }
        }
        return transactionsListForCard;
    }

    // only successful transactions
    public List<TransactionResponseForBookDto> getListOfTransactionsForBook(String bookName)
    {
        Book book = bookRepository.findByName(bookName);
        List<Transactions> listOfTransactions = book.getListOfTransactions();
        List<TransactionResponseForBookDto> transactionsListForBook = new ArrayList<>();

        for (Transactions transaction: listOfTransactions)
        {
            if (transaction.getTransactionStatus() == TransactionStatus.SUCCESSFUL)
            {
                TransactionResponseForBookDto transactionResponseForBookDto = new TransactionResponseForBookDto();

                transactionResponseForBookDto.setId((transaction.getId()));
                transactionResponseForBookDto.setCardId(transaction.getCard().getId());
                if (transaction.isIssueOperation() == true) {
                    transactionResponseForBookDto.setOperationType("ISSUE");
                }
                else {
                    transactionResponseForBookDto.setOperationType("RETURN");
                }
                transactionResponseForBookDto.setFine(transaction.getFine());
                transactionResponseForBookDto.setTransactionDate(transaction.getTransactionDate());

                transactionsListForBook.add(transactionResponseForBookDto);
            }
        }
        return transactionsListForBook;
    }
}
