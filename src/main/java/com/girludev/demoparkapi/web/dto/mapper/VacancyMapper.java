package com.girludev.demoparkapi.web.dto.mapper;


import com.girludev.demoparkapi.entity.Vacancy;
import com.girludev.demoparkapi.web.dto.vacancy.VacancyCreateDTO;
import com.girludev.demoparkapi.web.dto.vacancy.VacancyResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VacancyMapper {
    public static Vacancy toVacancyCreateDTO(VacancyCreateDTO vacancyCreateDTO){
        return new ModelMapper().map(vacancyCreateDTO, Vacancy.class);
    }

    public static VacancyResponseDTO toVacancyResponseDTO(Vacancy vacancy){
        return new ModelMapper().map(vacancy, VacancyResponseDTO.class);
    }
}
