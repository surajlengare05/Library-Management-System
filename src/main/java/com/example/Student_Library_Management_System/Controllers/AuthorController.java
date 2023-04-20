package com.example.Student_Library_Management_System.Controllers;

import com.example.Student_Library_Management_System.EntryDTOs.AuthorEntryDto;
import com.example.Student_Library_Management_System.ResponseDTOs.AuthorResponseDto;
import com.example.Student_Library_Management_System.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
public class AuthorController
{
    @Autowired
    AuthorService authorService;


    @PostMapping("/add")
    public String addAuthor(@RequestBody AuthorEntryDto authorEntryDto)
    {
        return authorService.addAuthor(authorEntryDto);
    }

    @GetMapping("/getAuthor")
    public AuthorResponseDto getAuthor(@RequestParam("authorId") int authorId)
    {
        return authorService.getAuthor(authorId);
    }
}
