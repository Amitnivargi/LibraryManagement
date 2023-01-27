package com.LibraryApplication.Library.Management.System.Services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.model.Transaction;
import com.example.library.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public void createTransaction(long userId, long bookId) {
        Date dueDate = new Date();
        dueDate.setTime(dueDate.getTime() + (14 * 24 * 60 * 60 * 1000));
        Transaction transaction = new Transaction(userId, bookId, "borrow", new Date(), null, dueDate);
        transactionRepository.save(transaction);
    }
    public void updateTransaction(long transactionId, double finePerDay) {
        Transaction transaction = transactionRepository.findById(transactionId).orElse(null);
        if(transaction != null) {
            transaction.setType("return");
            transaction.setDateReturned(new Date());
            Date dueDate = transaction.getDueDate();
            long diff = transaction.getDateReturned().getTime() - dueDate.getTime();
            double fine = diff * (finePerDay/(24*60*60*1000));
            if(fine < 0) {
                fine = 0;
            }
            transaction.setFine(fine);
            transactionRepository.save(transaction);
        }
    }
}
