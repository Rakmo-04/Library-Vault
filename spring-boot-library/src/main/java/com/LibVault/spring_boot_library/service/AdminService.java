package com.LibVault.spring_boot_library.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.LibVault.spring_boot_library.dao.BookRepository;
import com.LibVault.spring_boot_library.dao.CheckoutRepository;
import com.LibVault.spring_boot_library.dao.ReviewRepository;
import com.LibVault.spring_boot_library.entity.Book;
import com.LibVault.spring_boot_library.requestmodels.AddBookRequest;

@Service
@Transactional
public class AdminService {
	
	private BookRepository bookRepository;
	
	private ReviewRepository reviewRepository;
	
	private CheckoutRepository checkoutRepository;
	
	@Autowired
	public AdminService(BookRepository bookRepository,CheckoutRepository checkoutRepository,ReviewRepository reviewRepository) {
		this.bookRepository = bookRepository;
		this.checkoutRepository=checkoutRepository;
		this.reviewRepository = reviewRepository;
	}
	
	public void increaseBookQuantity(Long bookId) throws Exception{
		Optional<Book> book = bookRepository.findById(bookId);
			
		if(book.isPresent()== false) {
			throw new Exception("Book wasn't found");
		}
		
		book.get().setCopiesAvailable(book.get().getCopiesAvailable()+1);
		book.get().setCopies(book.get().getCopies()+1);
		
		bookRepository.save(book.get());
	}	
	
	public void decreaseBookQuantity(Long bookId) throws Exception{
		Optional<Book> book = bookRepository.findById(bookId);
		if(book.isPresent() == false || book.get().getCopiesAvailable() <= 0 || book.get().getCopies() <= 0) {
			throw new Exception("Book wasn't found");
		}
		 book.get().setCopiesAvailable(book.get().getCopiesAvailable()-1);
		 book.get().setCopies(book.get().getCopies()-1);
		 
		 bookRepository.save(book.get());
	}
	
	public void postBook(AddBookRequest addBookRequest) {
		Book book = new Book();
		book.setTitle(addBookRequest.getTitle());
		book.setAuthor(addBookRequest.getAuthor());
		book.setCategory(addBookRequest.getCategory());
		book.setCopies(addBookRequest.getCopies());
		book.setCopiesAvailable(addBookRequest.getCopies());
		book.setDescription(addBookRequest.getDescription());
		book.setImg(addBookRequest.getImg());
		bookRepository.save(book);
	}
	
	public void deleteBook(Long bookId) throws Exception{
		Optional<Book> book = bookRepository.findById(bookId);
		if(book.isPresent() == false) {
			throw new Exception("Book not found");
		}
		bookRepository.delete(book.get());
		checkoutRepository.deleteAllByBookId(bookId);
		reviewRepository.deleteAllByBookId(bookId);
	}
	
	

}
