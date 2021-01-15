package com.java.proiect.mapper;

import com.java.proiect.dto.ObiectVanzareDto;
import com.java.proiect.entity.ObiectVanzare;
import org.mapstruct.Mapper;

@Mapper
public interface ObiectVanzareMapper extends  EntityMapper<ObiectVanzareDto, ObiectVanzare> {
}
