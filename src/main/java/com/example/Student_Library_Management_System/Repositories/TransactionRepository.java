package com.example.Student_Library_Management_System.Repositories;

import com.example.Student_Library_Management_System.Model.Book;
import com.example.Student_Library_Management_System.Model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transaction;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Integer>
{
    @Query(value = "select * from transaction t where t.book_id=:bookId and t.card_id=:cardId and transaction_status = 'SUCCESSFUL'",  nativeQuery = true)
    // OR -> (value = "select * from transaction where book_id=:bookId and card_id=:cardId and is_issue_operation=true",  nativeQuery = true)
    List<Transactions> findTransactionsForBookAndCard(int bookId, int cardId);
    // there can be more than 1 transaction held for given BOOK & Card hence using List
    // used in getTransactionsForCardAndBook()  (Transaction Service)

    @Query(value = "select max(id) from transaction where book_id=:book_Id and card_id=:card_Id and is_issue_operation=true and transaction_status='SUCCESSFUL'", nativeQuery = true)
    int find_Last_Successful_Issue_Transaction_Id(int book_Id, int card_Id);
    // used in calculateFine()  (Transaction Service)  &  getIssuedBooksDetails()  (Book Service)

    @Query(value = "select sum(fine) from transaction where card_id=:cardId", nativeQuery = true)
    int findSumOfFinesPaidByStudent(int cardId);
    // used in getSumOfFinesPaid()  (Student Service)

    @Query(value = "SELECT COUNT(id), book_id FROM transaction WHERE is_issue_operation=true AND book_id IS NOT NULL AND card_id IS NOT NULL GROUP BY book_id", nativeQuery = true)
    List<List<Integer>> findCountForEachBook();
    // used in getMostDemandingBooks()  (Book Service)
    // we are considering only valid issue requests. conditions for it ->
    // 1) Transaction should be Issue operation only ( and not Return operation)
    // 2) Card should not be null (otherwise its considered as a fake request)
    // 3) Book should not be null (otherwise query will give count(id) for transactions in which book is null
    // & we don't want it in our List<List<Integer>>)
}
