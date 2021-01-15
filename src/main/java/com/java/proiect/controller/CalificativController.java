package com.java.proiect.controller;

import com.java.proiect.dto.CalificativDto;
import com.java.proiect.dto.LicitatieDto;
import com.java.proiect.entity.Calificativ;
import com.java.proiect.entity.Licitatie;
import com.java.proiect.mapper.CalificativMapper;
import com.java.proiect.mapper.LicitatieMapper;
import com.java.proiect.service.CalificativService;
import com.java.proiect.service.LicitatieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("calificativ")
public class CalificativController {
    private CalificativMapper mapper;
    private CalificativService service;

    public CalificativController(CalificativMapper mapper, CalificativService service) {
        this.mapper = mapper;
        this.service = service;
    }
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Calificativ> create(@Valid @RequestBody CalificativDto calificativDto) {
        Calificativ calificativ = service.create(mapper.toEntity(calificativDto));
        return new ResponseEntity<>(calificativ, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalificativDto> get(@PathVariable Long id) {
        Calificativ calificativ = service.getLicitatie(id);
        return new ResponseEntity<>(mapper.toDto(calificativ), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CalificativDto>> getAll() {
        List<Calificativ> calificativList = service.getAll();
        return new ResponseEntity<>(mapper.toDtoList(calificativList), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}/{calificativ}")
    public void update(@PathVariable Long id, @PathVariable  Double calificativ) {
        service.update(calificativ,id);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping(path="/rating/{id}")
    public Double getRating(@PathVariable Long id)
    {
        List<Calificativ> calificativs= service.getAllbyUser(id);
        Double rating=0.0;
        for(int i=0;i<calificativs.size();i++){
            rating=rating+calificativs.get(i).getNota();
        }
        rating=rating/calificativs.size();
        return rating;
    }
}
