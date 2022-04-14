package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.City;

@Repository
public interface CityDao extends JpaRepository<City, Integer> {

	City findByName(String name);

	boolean existsByName(String name);
}
