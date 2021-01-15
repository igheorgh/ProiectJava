package com.java.proiect.service;

import com.java.proiect.entity.Licitatie;
import com.java.proiect.entity.Portofel;
import com.java.proiect.exception.EntityNotFoundException;
import com.java.proiect.repository.LicitatieRepository;
import com.java.proiect.repository.PortofelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LicitatieService {
    private LicitatieRepository licitatieRepository;

    public LicitatieService(LicitatieRepository licitatieRepository) {
        this.licitatieRepository = licitatieRepository;
    }
    @Transactional
    public Licitatie create(Licitatie licitatie){
        return licitatieRepository.createLicitatie(licitatie);
    }

    public Licitatie getLicitatie(Long id){
        return licitatieRepository.findby(id).orElseThrow(() -> new EntityNotFoundException(String.format("Portofelul cu id-ul %s nu exista", id.toString())));
    }

    public List<Licitatie> getAll() {

        return licitatieRepository.findall();
    }

    public void update(Double bani, Long id) {
        licitatieRepository.updateLicitatie(id,bani);
    }

    public void delete(Long id) { licitatieRepository.deleteLicitatie(id);}

    public void Achitare(Long id)
    {
        licitatieRepository.Achitare(id);
    }
}
