package com.example.loan.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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


