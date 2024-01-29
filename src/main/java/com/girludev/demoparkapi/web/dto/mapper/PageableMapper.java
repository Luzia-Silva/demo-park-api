package com.girludev.demoparkapi.web.dto.mapper;

import com.girludev.demoparkapi.web.dto.PageableDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;


public class PageableMapper {

    public static PageableDTO toDto(Page page){
        return new ModelMapper().map(page, PageableDTO.class);
    }
}
