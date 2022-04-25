package com.turkcell.rentACar.entities.concretes;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ordered_additional_services")
public class OrderedAdditionalService {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ordered_additional_service_id")
	private Integer id;
	
	@ManyToOne
	private RentalCar rentalCar;

	@ManyToOne
	private AdditionalService additionalService;
}
