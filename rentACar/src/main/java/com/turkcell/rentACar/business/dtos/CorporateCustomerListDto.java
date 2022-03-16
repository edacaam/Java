package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateCustomerListDto {
	private int userId;

	private String eMail;

	private String password;
	
	private String companyName;
	
	private String taxNumber;
}
