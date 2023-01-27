package com.LibraryApplication.Library.Management.System.Services;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.example.library.model.Book;
import com.example.library.model.Transaction;
import com.example.library.repository.BookRepository;
import com.example.library.repository.TransactionRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final TransactionRepository transactionRepository;

    public BookService(BookRepository bookRepository, TransactionRepository transactionRepository) {
        this.bookRepository = bookRepository;
        this.transactionRepository = transactionRepository;
    }

    public Book borrowBook(int bookId, int userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book id"));
        if (book.getQuantity() <= 0) {
            throw new IllegalArgumentException("Book is not available");
        }

        List<Transaction> transactions = transactionRepository.findByUserIdAndType(userId,"borrow");
        if (transactions.size() >= 5) {
            throw new IllegalArgumentException("User has reached maximum limit of borrowed books");
        }
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        Date dueDate = new Date();
        dueDate.setTime(dueDate.getTime() + (14 * 24 * 60 * 60 * 1000));
        Transaction transaction = new Transaction(userId, bookId, "borrow", new Date(), null, dueDate);
        transactionRepository.save(transaction);
        return book;
    }

    public long calculateFine(Transaction transaction) {
        long diffInMillies = Math.abs(transaction.getDateReturned().getTime() - transaction.getDueDate().getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diffInDays * 10;
    }
    public Book returnBook(int transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid transaction id"));
        if (transaction.getType().equals("return")) {
            throw new IllegalArgumentException("Book is already returned");
        }
        Book book = bookRepository.findById(transaction.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid book id"));
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        transaction.setType("return");
        transaction.setDateReturned(new Date());
        long fine = 0;
        if (transaction.getDateReturned().after(transaction.getDueDate())) {
            fine = calculateFine(transaction);
        }
        transaction.setFine(fine);
        transactionRepository.save(transaction);
        return book;
    }
    public boolean checkAvailability(int bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book id"));
        return book.getQuantity() > 0;
    }

    public List<Transaction> getBorrowedBooksByUser(int userId) {
        return transactionRepository.findByUserIdAndType(userId, "borrow");
    }

    public List<Transaction> getReturnedBooksByUser(int userId) {
        return transactionRepository.findByUserIdAndType(userId, "return");
    }
}
