package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.UserDb;

public interface UserRepository extends CrudRepository<UserDb, String> {
	UserDb findByUsername(String username);
}
