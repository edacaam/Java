package com.turkcell.rentACar.business.outServices;

import org.springframework.stereotype.Service;

@Service
public class IsBankPosManager {
	
	 public boolean makePayment(String cardHolder,String cardNo,String cvv,String month, String year ){
	        return true;
	    }
}
