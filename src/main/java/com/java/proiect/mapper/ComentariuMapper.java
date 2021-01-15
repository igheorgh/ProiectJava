package com.java.proiect.mapper;

import com.java.proiect.dto.ComentariuDto;
import com.java.proiect.entity.Comentariu;
import org.mapstruct.Mapper;

@Mapper
public interface ComentariuMapper extends EntityMapper<ComentariuDto, Comentariu> {
}
