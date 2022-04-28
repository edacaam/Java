package com.turkcell.rentACar.business.dtos.cardInformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCardInformationDto {
	
	private Integer id;

	private String cardNo;

	private String cardHolder;

	private String expirationMonth;

	private String expirationYear;

	private String cvv;

	private Integer customerId;
}
