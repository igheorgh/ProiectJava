package com.java.proiect.mapper;

import com.java.proiect.dto.PortofelDto;
import com.java.proiect.entity.Portofel;
import org.mapstruct.Mapper;

@Mapper
public interface PortofelMapper  extends  EntityMapper<PortofelDto, Portofel> {
}
