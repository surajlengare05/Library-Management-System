package com.example.Student_Library_Management_System.Services;

import com.example.Student_Library_Management_System.EntryDTOs.BookEntryDto;
import com.example.Student_Library_Management_System.Enum.Genre;
import com.example.Student_Library_Management_System.Model.Author;
import com.example.Student_Library_Management_System.Model.Book;
import com.example.Student_Library_Management_System.Model.Transactions;
import com.example.Student_Library_Management_System.Repositories.AuthorRepository;
import com.example.Student_Library_Management_System.Repositories.BookRepository;
import com.example.Student_Library_Management_System.Repositories.TransactionRepository;
import com.example.Student_Library_Management_System.ResponseDTOs.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class BookService
{
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    TransactionRepository transactionRepository;

    public String addBook(BookEntryDto bookEntryDto)
    {
        int author_Id = bookEntryDto.getAuthorId();
        Author author = authorRepository.findById(author_Id).get();    //find Author Entity in DB by above Id.
                                                                      // If not present in DB then, we can throw an exception
        Book book = new Book();

        book.setName(bookEntryDto.getName());
        book.setPages(bookEntryDto.getPages());
        book.setGenre(bookEntryDto.getGenre());
        book.setIssued(false);
        // setting Foreign key attr of child class(Book)
        book.setAuthor(author);     // set entire Author object to current book

        // no need to set foreign key card since we have not issued this book yet

        // add current book into List of booksWritten attribute of above author
        List<Book> currentWrittenBooks = author.getBooksWritten();
        currentWrittenBooks.add(book);
        author.setBooksWritten(currentWrittenBooks);

        // since we have use Bidirectional mapping hence, we can only save parent entity(Author) & child(book)
        // will get saved in its table in Db automatically
        authorRepository.save(author);        // Resaving/upating author entity. changes-> new book is added in List of booksWritten
        return ("Book added successfully");
    }

    public List<BookResponseForAuthorDto> getAllBooksOfAuthor(String authorName)
    {
        Author author = authorRepository.findByName(authorName);
        List<Book> booksWritten = author.getBooksWritten();
        List<BookResponseForAuthorDto> listOfBooksWritten = new ArrayList<>();

        for (Book book: booksWritten)
        {
            BookResponseForAuthorDto bookResponseForAuthorDto = new BookResponseForAuthorDto();

            bookResponseForAuthorDto.setName(book.getName());
            bookResponseForAuthorDto.setGenre(book.getGenre());
            bookResponseForAuthorDto.setAvailable( ! book.isIssued());     // opposite of isIssued

            listOfBooksWritten.add(bookResponseForAuthorDto);
        }

        return listOfBooksWritten;
    }

    public List<BookResponseForGenreDto> getAllBooksFromGenre(String genre)
    {
        List<Book> bookList = bookRepository.findAllBooksFromGenre(genre);
        List<BookResponseForGenreDto> bookListForGenre = new ArrayList<>();

        for (Book book: bookList)
        {
            BookResponseForGenreDto bookResponseForGenreDto = new BookResponseForGenreDto();

            bookResponseForGenreDto.setName(book.getName());
            bookResponseForGenreDto.setAuthorName(book.getAuthor().getName());
            bookResponseForGenreDto.setAvailable( ! book.isIssued());

            bookListForGenre.add(bookResponseForGenreDto);
        }

        return bookListForGenre;
    }

    public List<BookResponseALLDto> getAllBooks()
    {
        List<Book> bookList = bookRepository.findAll();
        List<BookResponseALLDto> allBooksList = new ArrayList<>();

        for (Book book: bookList)
        {
            BookResponseALLDto bookResponseALLDto = new BookResponseALLDto();

            bookResponseALLDto.setName(book.getName());
            bookResponseALLDto.setGenre(book.getGenre());
            bookResponseALLDto.setAuthorName(book.getAuthor().getName());
            bookResponseALLDto.setAvailable( ! book.isIssued());

            allBooksList.add(bookResponseALLDto);
        }

        return allBooksList;
    }

    public List<BookResponseAvailableDto> getAllAvailableBooks()
    {
        List<Book> bookList = bookRepository.findAllAvailableBooks();
        List<BookResponseAvailableDto> availableBooksList = new ArrayList<>();

        for (Book book: bookList)
        {
            BookResponseAvailableDto bookResponseAvailableDto = new BookResponseAvailableDto();

            bookResponseAvailableDto.setName(book.getName());
            bookResponseAvailableDto.setAuthorName(book.getAuthor().getName());
            bookResponseAvailableDto.setGenre(book.getGenre());

            availableBooksList.add(bookResponseAvailableDto);
        }

        return availableBooksList;
    }

    public List<IssuedBooksResponseDto> getIssuedBooksDetails()
    {
        List<Book> bookList = bookRepository.findAllIssuedBooks();
        List<IssuedBooksResponseDto> issuedBooksList = new ArrayList<>();

        for (Book book: bookList)
        {
            IssuedBooksResponseDto issuedBooksResponseDto = new IssuedBooksResponseDto();

            issuedBooksResponseDto.setBookId(book.getId());
            issuedBooksResponseDto.setBookName(book.getName());
            issuedBooksResponseDto.setCardId(book.getCard().getId());
            issuedBooksResponseDto.setStudentName(book.getCard().getStudent().getName());

            int id = transactionRepository.find_Last_Successful_Issue_Transaction_Id(book.getId(), book.getCard().getId());
            Transactions transaction = transactionRepository.findById(id).get();
            LocalDate issueDate = transaction.getTransactionDate();
            LocalDate dueDate = issueDate.plusDays(15);

            issuedBooksResponseDto.setIssueDate(issueDate);
            issuedBooksResponseDto.setDueDate(dueDate);

            issuedBooksList.add(issuedBooksResponseDto);
        }

        return issuedBooksList;
    }

    // book having most no. of issue requests.
    // it can be 1 or more than 1 (with same no. of valid issue requests)
    public List<String> getMostDemandingBooks()
    {
        // Using Custom Query ->
        List<List<Integer>> countList = transactionRepository.findCountForEachBook();  //Refer this method for description
        Collections.sort(countList, (x,y) -> (x.get(0) < y.get(0)) ? 1 : -1);      // sort decreasing
        int maxCount = countList.get(0).get(0);             // count(id) of 1st arraylist
        List<Integer> bookIds = new ArrayList<>();       // to store all book's Ids whose count == max
        List<String> bookNames = new ArrayList<>();      // Answer- to store all book's names whose count == max

        for (int i=0; i<countList.size(); i++)
        {
            if (countList.get(i).get(0) == maxCount)
            {
                bookIds.add(countList.get(i).get(1));
            }
        }
       for (int i=0; i<bookIds.size(); i++)
       {
           int id = bookIds.get(i);
           String name = bookRepository.findById(id).get().getName();
           bookNames.add(name);
       }
       return bookNames;

       // without custom query ->
      /*  List<Book> bookList = bookRepository.findAll();
        int max = -1;
        List<String> bookNames = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for (Book book: bookList)
        {
            int validCount = 0;
            List<Transactions> allTransactions = book.getListOfTransactions();
            for (Transactions transaction: allTransactions)
            {
                if (transaction.isIssueOperation()==true && transaction.getBook()!=null && transaction.getCard()!=null)
                {
                    validCount++;
                }
            }
            map.put(book.getName(), validCount);
            max = Math.max(max, validCount);
        }
        for (String name: map.keySet())
        {
            if (map.get(name) == max) {
                bookNames.add(name);
            }
        }
        return bookNames; */
    }
}
