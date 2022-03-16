package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.Brand;
import com.turkcell.rentACar.entities.concretes.City;

@Repository
public interface CityDao extends JpaRepository<City, Integer> {
	City findById(int id);
	City findByName(String name);
	boolean existsByName(String name);
}
