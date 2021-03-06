package com.pjt2.lb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="Search_Log")
public class SearchLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="log_id")
	int logId;
	
//	@Column(name="book_isbn")
//	String bookIsbn;
//	
//	@Column(name="user_email")
//	String userEmail;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name="log_date")
	Date logDate;
	
	
	// search_log - user
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name ="user_email")
	User user;
	
	// search_log - book
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name ="book_isbn")
	Book book;
}
