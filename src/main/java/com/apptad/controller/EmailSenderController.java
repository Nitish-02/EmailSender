package com.apptad.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apptad.scheduler.MailSchedulerService;
import com.apptad.service.ReportService;



@RestController
public class EmailSenderController {
	
	

	
	
	@Autowired
	private ReportService reportService;
	
	@GetMapping(value = "/excel")
	public String generateExcelReport(HttpServletResponse response) throws Exception{
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=employee.xls";
		
		response.setHeader(headerKey, headerValue);
		reportService.generateExcel(response);
		
		response.flushBuffer();
		
		return "Database Dowmloaded";
	}
	
	@GetMapping(value="/sentmailwithattachment")
	public String emailCheckwithattachment() {
		reportService.ExcelDBSend();
		return "Email  is Sent";
	
	}
	
	
	
}
