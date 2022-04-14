package com.turkcell.rentACar.business.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndividualCustomerRequest {
	@Positive
	@NotNull
	private int userId;

	@Email
	private String email;

	@NotNull
	private String password;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	private String nationalIdentity;
}
