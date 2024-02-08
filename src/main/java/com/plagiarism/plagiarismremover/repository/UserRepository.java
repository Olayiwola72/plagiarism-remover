package com.plagiarism.plagiarismremover.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plagiarism.plagiarismremover.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	//Derived Query Methods
	Optional<User> findByUsername(String username);
}