package com.apptad.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.apptad.model.Employee;
import com.apptad.repository.EmployeeRepository;

@Service
public class ReportService {
	
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
	
	
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	public void generateExcel(HttpServletResponse response) throws Exception{
		
		List<Employee> employees = employeeRepository.findAll();
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Employee Info");
		HSSFRow row  = sheet.createRow(0);
		
		row.createCell(0).setCellValue("empid");
		row.createCell(1).setCellValue("fname");
		row.createCell(2).setCellValue("lname");
		row.createCell(3).setCellValue("address");
		row.createCell(4).setCellValue("contact");
		
		int dataRowIndex = 1;
		for(Employee employee : employees) {
			HSSFRow dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(employee.getEmpid());
			dataRow.createCell(1).setCellValue(employee.getFname());
			dataRow.createCell(2).setCellValue(employee.getLname());
			dataRow.createCell(3).setCellValue(employee.getAddress());
			dataRow.createCell(4).setCellValue(employee.getContact());
			
			dataRowIndex++;
			
		}
		
		ServletOutputStream ops = response.getOutputStream();
		workbook.write(ops);
		//workbook.c
		ops.close();
	}
		
	public void ExcelDBSend() {
		
		MimeMessage mimiMessage = javaMailSender.createMimeMessage();
		
	    try {
			MimeMessageHelper helper = new MimeMessageHelper(mimiMessage, true);
			
		    
		   
		    
			
		    helper.setTo(properties.get("TO_LIST").toString());
		    helper.setCc(new String[] {"deepakkumar843450@gmail.com", "nitishazad22@gmail.com","silkroute.xenon@gmail.com "});
		    helper.setSubject(properties.getProperty("SUBJECT").toString());;
		    helper.setText(properties.get("EMAIL_BODY").toString());
		    
			
			  FileSystemResource file = new FileSystemResource(new
			  File("C:\\Users\\Nitish\\Downloads\\employee.xls"));
			  helper.addAttachment(file.getFilename(), file);
			 
		   
		    

		    javaMailSender.send(mimiMessage);
			
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}
		
		}
	

}
