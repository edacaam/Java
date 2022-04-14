package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetIndividualCustomerDto {
	private int userId;

	private String email;

	private String password;

	private String firstName;

	private String lastName;

	private String nationalIdentity;
}
