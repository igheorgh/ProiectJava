package com.java.proiect.service;

import com.java.proiect.entity.Calificativ;
import com.java.proiect.entity.Licitatie;
import com.java.proiect.exception.EntityNotFoundException;
import com.java.proiect.repository.CalificativRepository;
import com.java.proiect.repository.LicitatieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CalificativService {
    private CalificativRepository calificativRepository;

    public CalificativService(CalificativRepository calificativRepository) {
        this.calificativRepository = calificativRepository;
    }
    @Transactional
    public Calificativ create(Calificativ calificativ){
        return calificativRepository.createCalificativ(calificativ);
    }

    public Calificativ getLicitatie(Long id){
        return calificativRepository.findby(id).orElseThrow(() -> new EntityNotFoundException(String.format("Calificativul cu id-ul %s nu exista", id.toString())));
    }

    public List<Calificativ> getAll() {

        return calificativRepository.findall();
    }

    public void update(Double calificativ, Long id) {
        calificativRepository.updateCalificativ(id,calificativ);
    }

    public void delete(Long id) { calificativRepository.deleteCalificativ(id);}

    public List<Calificativ> getAllbyUser(Long id) {

        return calificativRepository.findallbyUSer(id);
    }
}
