package com.LibVault.spring_boot_library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LibVault.spring_boot_library.entity.Message;
import com.LibVault.spring_boot_library.requestmodels.AdminQuestionRequest;
import com.LibVault.spring_boot_library.service.MessagesService;
import com.luv2code.springbootlibrary.utils.ExtractJWT;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/messages")
public class MessageController {
	
	private MessagesService messageService;
	
	@Autowired
	public MessageController(MessagesService messageService) {
		this.messageService = messageService;
	}
	
	@PostMapping("/secure/add/message")
	public void postMessage(@RequestHeader(value="Authorization")String token,@RequestBody Message messageRequest) {
		
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		messageService.postMessage(messageRequest, userEmail);
	}
	
	@PutMapping("/secure/admin/message")
	public void putMessage(@RequestHeader(value="Authorization")String token,
			@RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception{
		
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
		
		if(admin == null || !admin.equals("admin")) {
			throw new Exception("Administration Page Only.");
			
		}
		messageService.putMessage(adminQuestionRequest, userEmail);
	}
	

}
