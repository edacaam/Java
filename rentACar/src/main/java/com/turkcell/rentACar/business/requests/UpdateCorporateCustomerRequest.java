package com.turkcell.rentACar.business.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {
	@NotNull
	@Positive
	private int userId;

	@NotNull
	@Email
	private String email;

	@NotNull
	private String password;

	@NotNull
	@Size(min = 2, max = 30)
	private String companyName;

	@NotNull
	@Size(min = 2, max = 30)
	private String taxNumber;
}
