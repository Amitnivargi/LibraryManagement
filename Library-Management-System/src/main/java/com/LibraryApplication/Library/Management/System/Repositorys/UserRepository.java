package com.LibraryApplication.Library.Management.System.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.library.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}