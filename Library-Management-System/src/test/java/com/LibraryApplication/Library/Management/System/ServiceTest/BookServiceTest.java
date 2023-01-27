package com.LibraryApplication.Library.Management.System.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.library.model.Book;
import com.example.library.model.Transaction;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.TransactionRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.BookService;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        User user = new User(1, "John Doe", "johndoe@example.com", "password");
        Book book = new Book(1, "Book Title", "Author", "1234567890123", 2);
        List<Transaction> transactions = Arrays.asList(
                new Transaction(1, user.getId(), book.getId(), "borrow", new Date(), null, new Date()),
                new Transaction(2, user.getId(), book.getId(), "return", new Date(), new Date(), new Date())
        );
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
        when(transactionRepository.findByUserIdAndType(1L, "borrow")).thenReturn(transactions);
    }

    @Test
    public void testBorrowBook() {
        assertEquals(true, bookService.borrowBook(1L, 1L, 5));
    }

    @Test
    public void testBorrowBook_UserReachMaxBooks() {
        assertEquals(false, bookService.borrowBook(1L, 1L, 0));
    }

    @Test
    public void testBorrowBook_BookNotAvailable() {
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(new Book(1, "Book Title", "Author", "1234567890123", 0)));
        assertEquals(false, bookService.borrowBook(1L, 1L, 5));
    }

    @Test
    public void testReturnBook() {
        double fine = bookService.returnBook(1L);
        assertEquals(0.0, fine);
    }

    @Test
    public void testReturnBook_BookOverdue() {
        double fine = bookService.returnBook(1L, 2, 0.25);
        assertEquals(0.5, fine);
    }

    @Test
    public void testCheckAvailability() {
        assertEquals(true, bookService.checkAvailability(1L));
    }

    @Test
    public void testCheckAvailability_BookNotAvailable() {
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(new Book(1, "Book Title", "Author", "1234567890123", 0)));
        assertEquals(false, bookService.checkAvailability(1L));
    }
}