package com.example.loan.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

@Service
public class TimestampFactory 
{	public LocalDateTime getCurrentDateTime()
	{	return LocalDateTime.now();
	}
	
	public LocalDate getCurrentDate()
	{	return LocalDate.now();
	}
	
	public LocalTime getCurrentTime()
	{	return LocalTime.now();
	}
}


