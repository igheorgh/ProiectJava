package com.java.proiect.controller;

import com.java.proiect.dto.LicitatieDto;
import com.java.proiect.dto.PortofelDto;
import com.java.proiect.entity.Licitatie;
import com.java.proiect.entity.Portofel;
import com.java.proiect.mapper.LicitatieMapper;
import com.java.proiect.mapper.PortofelMapper;
import com.java.proiect.service.LicitatieService;
import com.java.proiect.service.PortofelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("licitatie")
public class LicitatieController {
    private LicitatieMapper mapper;
    private LicitatieService service;

    public LicitatieController(LicitatieMapper mapper, LicitatieService service) {
        this.mapper = mapper;
        this.service = service;
    }
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Licitatie> create(@Valid @RequestBody LicitatieDto licitatiedto) {
        Licitatie licitatie = service.create(mapper.toEntity(licitatiedto));
        return new ResponseEntity<>(licitatie, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LicitatieDto> get(@PathVariable Long id) {
        Licitatie licitatie = service.getLicitatie(id);
        return new ResponseEntity<>(mapper.toDto(licitatie), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LicitatieDto>> getAll() {
        List<Licitatie> licitaties = service.getAll();
        return new ResponseEntity<>(mapper.toDtoList(licitaties), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}/{oferta}")
    public void update(@PathVariable Long id, @PathVariable  Double oferta) {
        service.update(oferta,id);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping(path = "/achitare/{id}")
    public void Achitare(@PathVariable Long id) {
       service.Achitare(id);
    }
}
