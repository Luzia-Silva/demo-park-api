package com.girludev.demoparkapi.repository;

import com.girludev.demoparkapi.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

   Optional<Vacancy> findByCode(String code);
}
