package com.LibraryApplication.Library.Management.System.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "date_borrowed", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateBorrowed;

    @Column(name = "date_returned", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date dateReturned;

    @Column(name = "due_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    public Transaction() {}

    public Transaction(Long userId, Long bookId, String type, Date dateBorrowed, Date dateReturned, Date dueDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.type = type;
        this.dateBorrowed = dateBorrowed;
        this.dateReturned = dateReturned;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(Date dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
