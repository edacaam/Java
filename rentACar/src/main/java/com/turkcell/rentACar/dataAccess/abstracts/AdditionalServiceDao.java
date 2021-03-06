package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.AdditionalService;

@Repository
public interface AdditionalServiceDao extends JpaRepository<AdditionalService, Integer> {

	boolean existsByName(String name);

	boolean existsByNameAndDailyPrice(String name, double dailyPrice);

	AdditionalService findByName(String name);
}
