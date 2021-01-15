package com.java.proiect.mapper;

import com.java.proiect.dto.LicitatieDto;
import com.java.proiect.entity.Licitatie;
import org.mapstruct.Mapper;

@Mapper
public interface LicitatieMapper  extends  EntityMapper<LicitatieDto, Licitatie> {
}
