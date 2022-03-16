package com.turkcell.rentACar.entities.concretes;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {

	@OneToMany(mappedBy = "customer")
	private List<RentalCar> rents;

	@OneToMany(mappedBy = "customer")
	private List<Invoice> invoices;

}
