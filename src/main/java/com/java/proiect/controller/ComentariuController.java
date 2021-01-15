package com.java.proiect.controller;

import com.java.proiect.dto.ComentariuDto;
import com.java.proiect.entity.Comentariu;
import com.java.proiect.mapper.ComentariuMapper;
import com.java.proiect.service.ComentariuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("comentariu")
public class ComentariuController {

    private ComentariuMapper mapper;
    private ComentariuService service;

    public ComentariuController(ComentariuMapper mapper, ComentariuService service) {
        this.mapper = mapper;
        this.service = service;
    }


    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comentariu> create(@Valid @RequestBody ComentariuDto comentariuDto) {
        Comentariu comentariu = service.create(mapper.toEntity(comentariuDto));
        return new ResponseEntity<>(comentariu, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComentariuDto> get(@PathVariable Long id) {
        Comentariu comentariu = service.getComentariu(id);
        return new ResponseEntity<>(mapper.toDto(comentariu), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ComentariuDto>> getAll() {
        List<Comentariu> comentarius = service.getAll();
        return new ResponseEntity<>(mapper.toDtoList(comentarius), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}/{comentariu}")
    public void update(@PathVariable Long id, @PathVariable  String comentariu) {
        service.update(comentariu,id);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }


}
