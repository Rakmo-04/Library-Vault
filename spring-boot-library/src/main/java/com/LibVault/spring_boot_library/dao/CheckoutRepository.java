package com.LibVault.spring_boot_library.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.LibVault.spring_boot_library.entity.Checkout;
import java.util.*;

public interface CheckoutRepository extends JpaRepository<Checkout,Long> {
	
	Checkout findByUserEmailAndBookId(String userEmail,Long bookId);
	
	List<Checkout>findBooksByUserEmail(String userEmail);
	
	@Modifying
	@Query("delete from checkout where book_id in :book_id")
	void deleteAllByBookId(@Param("book_id") Long bookId);
	
}
