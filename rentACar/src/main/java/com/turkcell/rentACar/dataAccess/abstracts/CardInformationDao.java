package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.CarMaintenance;
import com.turkcell.rentACar.entities.concretes.CardInformation;

@Repository
public interface CardInformationDao extends JpaRepository<CardInformation, Integer>{
}
