package com.java.proiect.controller;

import com.java.proiect.dto.PortofelDto;
import com.java.proiect.dto.UserInformationDto;
import com.java.proiect.dto.UserUpdateDto;
import com.java.proiect.entity.Portofel;
import com.java.proiect.entity.User;
import com.java.proiect.mapper.PortofelMapper;
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
@RequestMapping("portofel")
public class PortofelController {
    private PortofelMapper mapper;
    private PortofelService service;

    public PortofelController(PortofelMapper mapper, PortofelService service) {
        this.mapper = mapper;
        this.service = service;
    }
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Portofel> create(@Valid @RequestBody PortofelDto portofelDto) {
        Portofel portofel = service.create(mapper.toEntity(portofelDto));
        return new ResponseEntity<>(portofel, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PortofelDto> get(@PathVariable Long id) {
        Portofel portofel = service.getPortofelbyId(id);
        return new ResponseEntity<>(mapper.toDto(portofel), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PortofelDto>> getAll() {
        List<Portofel> portofels = service.getAll();
        return new ResponseEntity<>(mapper.toDtoList(portofels), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}/{suma_bani}")
    public void update(@PathVariable Long id, @PathVariable  Double suma_bani) {
        service.update(suma_bani,id);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
