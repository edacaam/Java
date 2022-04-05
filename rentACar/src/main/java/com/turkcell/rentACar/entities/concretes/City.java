package com.turkcell.rentACar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cities")
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="city_id")
	private Integer cityId;
	
	@Column(name="name")
	private String name;	
	
	@OneToMany(mappedBy = "cityOfPickUp")
	private List<RentalCar> rentalCarCityOfPickUp;

	@OneToMany(mappedBy = "cityOfDelivery")
	private List<RentalCar> rentalCarCityOfDelivery;
}
