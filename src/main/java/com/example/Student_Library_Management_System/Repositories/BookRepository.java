package com.example.Student_Library_Management_System.Repositories;

import com.example.Student_Library_Management_System.Model.Book;
import com.example.Student_Library_Management_System.ResponseDTOs.BookResponseALLDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>
{
    // since .get() of default method 'findById().get()' wont work on null object.  Hence creating manually
    @Query(value = "select * from book where id=:bookId", nativeQuery = true)
    Book findBookById(int bookId);
    // used in issueBook()   (Transaction Service)

    Book findByName(String bookName);
    // used in getListOfTransactionsForBook()  (Transaction Service)

    @Query(value = "select * from book where genre=:genre", nativeQuery = true)
    List<Book> findAllBooksFromGenre(String genre);
    // used in getAllBooksFromGenre()   (Book Service)

    @Query(value = "select * from book where is_issued = false", nativeQuery = true)
    List<Book> findAllAvailableBooks();
    // used in getAllAvailableBooks()   (Book Service)

    @Query(value = "select * from book where is_issued = true", nativeQuery = true)
    List<Book> findAllIssuedBooks();
    // used in getIssuedBooksDetails()   (Book Service)

}
