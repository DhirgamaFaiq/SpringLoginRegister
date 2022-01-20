package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="user")
public class UserDb {
	@Id
	@Column (name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	private String created;
	
	private String modified;

}
