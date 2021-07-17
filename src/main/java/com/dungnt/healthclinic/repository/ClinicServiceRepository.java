package com.dungnt.healthclinic.repository;

import com.dungnt.healthclinic.model.ClinicService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicServiceRepository extends JpaRepository<ClinicService, Long> {
//    ClinicService findByRoom(String room);
}
