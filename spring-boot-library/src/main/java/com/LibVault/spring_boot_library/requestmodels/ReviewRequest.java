package com.LibVault.spring_boot_library.requestmodels;

import java.util.Optional;

import lombok.Data;

@Data
public class ReviewRequest {
	
	private double rating;
	
	private Long bookId;
	
	private Optional<String> reviewDescription;
	
	

}
