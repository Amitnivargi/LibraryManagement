package com.LibraryApplication.Library.Management.System.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.library.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}