package com.example.Student_Library_Management_System.Controllers;

import com.example.Student_Library_Management_System.EntryDTOs.BookEntryDto;
import com.example.Student_Library_Management_System.ResponseDTOs.*;
import com.example.Student_Library_Management_System.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController
{
    @Autowired
    BookService bookService;


    @PostMapping("/add")
    public String addBook(@RequestBody BookEntryDto bookEntryDto)
    {
        return bookService.addBook(bookEntryDto);
    }

    @GetMapping("/getAllBooksOfAuthor")
    public List<BookResponseForAuthorDto> getAllBooksOfAuthor(String authorName)
    {
        return bookService.getAllBooksOfAuthor(authorName);
    }

    @GetMapping("/getAllBooksFromGenre")
    public List<BookResponseForGenreDto> getAllBooksFromGenre(@RequestParam ("genre") String genre)
    {
        return bookService.getAllBooksFromGenre(genre);
    }

    @GetMapping("/getAllBooks")
    public List<BookResponseALLDto> getAllBooks()
    {
        return bookService.getAllBooks();
    }

    @GetMapping("/getAllAvailableBooks")
    public List<BookResponseAvailableDto> getAllAvailableBooks()
    {
        return bookService.getAllAvailableBooks();
    }

    @GetMapping("/getIssuedBooksDetails")
    public List<IssuedBooksResponseDto> getIssuedBooksDetails()
    {
        return bookService.getIssuedBooksDetails();
    }

    @GetMapping("/getMostDemandingBook")
    public List<String> getMostDemandingBooks()
    {
        return bookService.getMostDemandingBooks();
    }
    // list of book names who has the highest no. of valid issue requests
    // there can be more than 1 boo having no. of valid issue requests == the highest no. of
    // valid issue requests
}
