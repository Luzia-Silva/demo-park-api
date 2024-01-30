package com.girludev.demoparkapi.service;

import com.girludev.demoparkapi.entity.Vacancy;
import com.girludev.demoparkapi.exception.CodeUniqueViolationException;
import com.girludev.demoparkapi.exception.EntityNotFoundException;
import com.girludev.demoparkapi.repository.VacancyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VacancyService {
    private final VacancyRepository vacancyRepository;

    @Transactional
    public Vacancy save(Vacancy vacancy){
        try{
            return vacancyRepository.save(vacancy);
        }catch (DataIntegrityViolationException ex){
            throw new CodeUniqueViolationException(String.format("This vacancy code '%s' exists in database", vacancy.getCode()));
        }
    }
    @Transactional
    public Vacancy searchByCode(String code){
        return vacancyRepository.findByCode(code).orElseThrow(
                () -> new EntityNotFoundException(String.format("The vacancy with the code '%s' was not found", code))
        );
    }
}
