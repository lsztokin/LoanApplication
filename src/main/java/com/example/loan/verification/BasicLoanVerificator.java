package com.example.loan.verification;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.loan.model.LoanApplication;
import com.example.loan.param.LoanParameters;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BasicLoanVerificator implements LoanVerificaticator 
{
	@Autowired
	private LoanParameters loanParams;
	
	@Override
	public boolean accept(LoanApplication application) 
	{	
		return isApplicationAmmountInRange(application) 
				&& isApplicationTermInRange(application) 
				&& isNightConditionFulfilled(application);
	}

	private boolean isApplicationAmmountInRange(LoanApplication application) 
	{
		if(application.getAmount() >= loanParams.getMinLoanAmount() 
				&& application.getAmount() <= loanParams.getMaxLoanAmount())
		{	return true;
		}
		
		return false;
	}

	private boolean isApplicationTermInRange(LoanApplication application) 
	{
		if(application.getTermInDays() >= loanParams.getMinLoanTermInDays() 
				&& application.getTermInDays() <= loanParams.getMaxLoanTermInDays())
		{	return true;
		}
		
		return false;
	}

	private boolean isNightConditionFulfilled(LoanApplication application) 
	{
		if(isApplicationWithinNightPeriod(application))
		{	if(application.getAmount() == loanParams.getMaxLoanAmount())
			{	return false;
			}
		}
		
		return true;
	}

	private boolean isApplicationWithinNightPeriod(LoanApplication application) 
	{	
		LocalTime applicationTime = application.getCreationTimestamp().toLocalTime();
		
		if(loanParams.getNightPeriodStartHour().isAfter(applicationTime) 
				|| loanParams.getNightPeriodEndHour().isBefore(applicationTime))
		{	return false;
		}
		
		return true;
	}

}
