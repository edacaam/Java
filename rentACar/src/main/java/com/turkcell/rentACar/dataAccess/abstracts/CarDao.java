package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.Car;

@Repository
public interface CarDao extends JpaRepository<Car, Integer> {

	List<Car> findByDailyPriceLessThanEqual(double dailyPrice);
}
