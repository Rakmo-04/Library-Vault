package com.LibVault.spring_boot_library.controller;

import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.LibVault.spring_boot_library.Utils.ExtractJWT;
import com.LibVault.spring_boot_library.entity.Book;
import com.LibVault.spring_boot_library.service.BookService;
import com.LibVault.spring_boot_library.responsemodels.*;
import java.util.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

	private BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@GetMapping("/secure/currentloans")
	public List<ShelfCurrentLoansResponse> currentLoans(@RequestHeader(value="Authorization")String token)throws Exception{
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		return bookService.currentLoans(userEmail);
	}

	@PutMapping("/secure/checkout")
	public Book checkoutBook(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId)
			throws Exception {
		String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
//		String userEmail = "omkar.yadav@spit.ac.in";
		return bookService.checkoutBook(userEmail, bookId);

	}

//	@GetMapping("/secure/ischeckedout/byuser")
//	public Boolean checkedoutBookByUser(@RequestHeader(value = "Authorization") String token,
//			@RequestParam Long BookId) {
//		String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
//		System.out.println(userEmail);
//		return bookService.checkoutBookByUser(userEmail, BookId);
//	}
	@GetMapping("/secure/ischeckedout/byuser")
	public Boolean checkedoutBookByUser(@RequestHeader(value = "Authorization") String token,
	                                    @RequestParam Long bookId) {
	    System.out.println("Authorization Header: " + token); // Debug log
	    System.out.println("Book ID: " + bookId); // Debug log
	    String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
	    System.out.println("User Email: " + userEmail); // Debug log
	    return bookService.checkoutBookByUser(userEmail, bookId);
	}


	@GetMapping("/secure/currentloans/count")
	public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {

		String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
		return bookService.currentLoansCount(userEmail);
	}
	
	@PutMapping("/secure/return")
	public void returnBook(@RequestHeader(value = "Authorization") String token,
			@RequestParam Long bookId) throws Exception{
		
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		
		bookService.returnBook(userEmail, bookId);
	}
	
	@PutMapping("/secure/renew/loan")
	public void renewLoan(@RequestHeader(value="Authorization")String token,@RequestParam Long bookId) throws Exception{
		
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		
		bookService.renewLoan(userEmail, bookId);
		
		
	}

}
