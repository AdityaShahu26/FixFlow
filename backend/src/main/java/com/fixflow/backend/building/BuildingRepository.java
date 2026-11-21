package com.fixflow.backend.building;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building , Long> {

    List<Building> findBySociety(Long societyId);


}
