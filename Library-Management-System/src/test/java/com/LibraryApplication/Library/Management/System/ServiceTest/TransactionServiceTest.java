import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.library.model.Transaction;
import com.example.library.repository.TransactionRepository;
import com.example.library.service.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        Date dueDate = new Date();
        dueDate.setTime(dueDate.getTime() + (14 * 24 * 60 * 60 * 1000));
        Transaction transaction = new Transaction(1, 1, 1, "borrow", new Date(), null, dueDate);
        when(transactionRepository.findById(1L)).thenReturn(java.util.Optional.of(transaction));
    }

    @Test
    public void testUpdateTransaction() {
        double fine = transactionService.updateTransaction(1L, 0.25);
        assertEquals(0.0, fine);
    }

    @Test
    public void testUpdateTransaction_BookOverdue() {
        Date dateReturned = new Date();
        dateReturned.setTime(dateReturned.getTime() + (15 * 24 * 60 * 60 * 1000));
        Transaction transaction = new Transaction(1, 1, 1, "borrow", new Date(), dateReturned, new Date());
        when(transactionRepository.findById(1L)).thenReturn(java.util.Optional.of(transaction));
        double fine = transactionService.updateTransaction(1L, 0.25);
        assertEquals(0.25, fine);
    }
}