package com.example.loan.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

public class TimestampFactoryTest 
{

	private LocalDateTime timestamp;
	private TimestampFactory timestampFactory = new TimestampFactory();
	
	@Before
	public void init()
	{	timestamp = LocalDateTime.now();
	}
	
	@Test
	public void testGetCurrentDateTime() 
	{	LocalDateTime receivedDateTime = timestampFactory.getCurrentDateTime();
		
		assertThat(receivedDateTime, allOf(greaterThanOrEqualTo(timestamp), lessThan(timestamp.plusSeconds(1))));
	}

	@Test
	public void testGetCurrentDate() 
	{	LocalDate receivedDate = timestampFactory.getCurrentDate();
		
		assertThat(receivedDate, is(timestamp.toLocalDate()));
	}

	@Test
	public void testGetCurrentTime() 
	{	LocalTime receivedTime = timestampFactory.getCurrentTime();
	
		assertThat(receivedTime, allOf(greaterThanOrEqualTo(timestamp.toLocalTime()), lessThan(timestamp.toLocalTime().plusSeconds(1))));
	}
}
