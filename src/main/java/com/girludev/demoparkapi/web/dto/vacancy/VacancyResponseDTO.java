package com.girludev.demoparkapi.web.dto.vacancy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacancyResponseDTO {
    private String id;
    private String code;
    private String status;
}
