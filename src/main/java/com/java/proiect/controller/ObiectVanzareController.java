package com.java.proiect.controller;

import com.java.proiect.dto.ObiectVanzareDto;
import com.java.proiect.dto.PortofelDto;
import com.java.proiect.entity.ObiectVanzare;
import com.java.proiect.entity.Portofel;
import com.java.proiect.mapper.ObiectVanzareMapper;
import com.java.proiect.mapper.PortofelMapper;
import com.java.proiect.service.ObiectVanzareService;
import com.java.proiect.service.PortofelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("produs")
public class ObiectVanzareController {
    private ObiectVanzareMapper mapper;
    private ObiectVanzareService service;

    public ObiectVanzareController(ObiectVanzareMapper mapper, ObiectVanzareService service) {
        this.mapper = mapper;
        this.service = service;
    }


    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObiectVanzare> create(@Valid @RequestBody ObiectVanzareDto obiectVanzareDto) {
        ObiectVanzare obiectVanzare = service.create(mapper.toEntity(obiectVanzareDto));
        return new ResponseEntity<>(obiectVanzare, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObiectVanzareDto> get(@PathVariable Long id) {
        ObiectVanzare obiectVanzare = service.getProdusbyId(id);
        return new ResponseEntity<>(mapper.toDto(obiectVanzare), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ObiectVanzareDto>> getAll() {
        List<ObiectVanzare> obiectVanzareList = service.getAll();
        return new ResponseEntity<>(mapper.toDtoList(obiectVanzareList), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}/{vandut}")
    public void update(@PathVariable Long id, @PathVariable  int vandut) {
        service.update(vandut,id);
    }

    @PutMapping(path = "/duratazile/{id}/{zile}")
    public void updateDuratazile(@PathVariable Long id, @PathVariable  int zile) {
        service.updateZIle(zile,id);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping(path = "myobj/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ObiectVanzareDto>> getAllMyoBJ(@PathVariable Long id) {
        List<ObiectVanzare> obiectVanzareList = service.getallmyprod(id);
        return new ResponseEntity<>(mapper.toDtoList(obiectVanzareList), HttpStatus.OK);
    }


        @GetMapping(path = "bydedesc", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ObiectVanzareDto>> getbyDEDesc() {
        List<ObiectVanzare> obiectVanzareList = service.getAllDFdesc();
        return new ResponseEntity<>(mapper.toDtoList(obiectVanzareList), HttpStatus.OK);
    }

    @GetMapping(path = "bydedasc", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ObiectVanzareDto>> getbyDEAsc() {
        List<ObiectVanzare> obiectVanzareList = service.getAllDFasc();
        return new ResponseEntity<>(mapper.toDtoList(obiectVanzareList), HttpStatus.OK);
    }

    @GetMapping(path = "bydpdesc", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ObiectVanzareDto>> getbyDPDesc() {
        List<ObiectVanzare> obiectVanzareList = service.getAllDPdesc();
        return new ResponseEntity<>(mapper.toDtoList(obiectVanzareList), HttpStatus.OK);
    }

    @GetMapping(path = "bydepdasc", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ObiectVanzareDto>> getbyDPAsc() {
        List<ObiectVanzare> obiectVanzareList = service.getAllDPasc();
        return new ResponseEntity<>(mapper.toDtoList(obiectVanzareList), HttpStatus.OK);
    }


    @GetMapping(path = "bydpresc", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ObiectVanzareDto>> getbyDPresc() {
        List<ObiectVanzare> obiectVanzareList = service.getAllPRdesc();
        return new ResponseEntity<>(mapper.toDtoList(obiectVanzareList), HttpStatus.OK);
    }

    @GetMapping(path = "bydeprasc", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ObiectVanzareDto>> getbyPrAsc() {
        List<ObiectVanzare> obiectVanzareList = service.getAllPRasc();
        return new ResponseEntity<>(mapper.toDtoList(obiectVanzareList), HttpStatus.OK);
    }
}
