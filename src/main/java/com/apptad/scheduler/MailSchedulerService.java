package com.apptad.scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSchedulerService {
	
	@Autowired
	JavaMailSender javaMailSender;
	
	public static Properties properties = new Properties();
	
	static {
		try {
			FileReader filereader = new FileReader("C:\\Users\\Nitish\\git\\repository\\EmailProperties.properties");
			properties.load(filereader);
			System.out.println(properties.get("TO_LIST"));
			System.out.println(properties.get("EMAIL_BODY"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void emailSent() {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		//simpleMailMessage.setTo("Nitishazad22@gmail.com");
		
		simpleMailMessage.setTo(properties.get("TO_LIST").toString());
		//simpleMailMessage.setCc(new String[] {"santosh.mahto@apptadinc.com", "nitishazad22@gmail.com"});
		simpleMailMessage.setSubject(properties.getProperty("SUBJECT").toString());
		simpleMailMessage.setText(properties.get("EMAIL_BODY").toString());
		//simpleMailMessage.
		javaMailSender.send(simpleMailMessage);
		}
	
	public void emailSend() {
		
		MimeMessage mimiMessage = javaMailSender.createMimeMessage();
		
	    try {
			MimeMessageHelper helper = new MimeMessageHelper(mimiMessage, true);
			
		    
		   
		    
			
		    helper.setTo(properties.get("TO_LIST").toString());
		    helper.setCc(new String[] {"deepak.kumar@apptadinc.com", "nitishazad22@gmail.com"});
		    helper.setSubject(properties.getProperty("SUBJECT").toString());;
		    helper.setText(properties.get("EMAIL_BODY").toString());
		    
			/*
			 * FileSystemResource file = new FileSystemResource(new
			 * File("C:\\Users\\Nitish\\git\\repository\\EmployeeRecord\\pom.xml"));
			 * helper.addAttachment(file.getFilename(), file);
			 */
		    
		    helper.setText("<html><body><p>Happy Birthday</p><img src='cid:identifier1234'></body></html>", true);
		    FileSystemResource res = new FileSystemResource(new File("C:\\Users\\Nitish\\Desktop\\Htlnm\\Birthday_wish.png"));
			//helper.addInline("identifier1234", null);
			helper.addInline("identifier1234", res);
		    

		    javaMailSender.send(mimiMessage);
			
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}
		
	}


}
