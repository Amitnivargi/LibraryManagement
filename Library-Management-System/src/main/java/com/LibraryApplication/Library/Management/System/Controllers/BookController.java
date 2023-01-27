package com.LibraryApplication.Library.Management.System.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.model.BorrowReturnDTO;
import com.example.library.service.BookService;
import com.example.library.service.TransactionService;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/borrow")
    public boolean borrowBook(@RequestBody BorrowReturnDTO borrowReturnDTO) {
        return bookService.borrowBook(borrowReturnDTO.getUserId(), borrowReturnDTO.getBookId(), borrowReturnDTO.getMaxBooksPerUser());
    }

    @PutMapping("/return")
    public double returnBook(@RequestBody BorrowReturnDTO borrowReturnDTO) {
        return transactionService.updateTransaction(borrowReturnDTO.getTransactionId(), borrowReturnDTO.getFinePerDay());
    }
}