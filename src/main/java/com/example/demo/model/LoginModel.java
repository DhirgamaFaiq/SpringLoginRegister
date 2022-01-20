package com.example.demo.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="login")
public class LoginModel {
	
	@Id
	@Column (name = "username")
	private String username;
	
	@Column(name = "token")
	private String token;
	
	private String created;
	
	private String modified;
	

}
