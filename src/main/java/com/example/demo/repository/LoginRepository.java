package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.LoginModel;

public interface LoginRepository extends CrudRepository<LoginModel, String>  {
	LoginModel findByUsername(String username);
}
