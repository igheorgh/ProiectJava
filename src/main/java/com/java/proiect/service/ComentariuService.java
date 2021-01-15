package com.java.proiect.service;

import com.java.proiect.entity.Comentariu;
import com.java.proiect.exception.EntityNotFoundException;
import com.java.proiect.repository.ComentariuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ComentariuService {

    private ComentariuRepository comentariuRepository;

    public ComentariuService(ComentariuRepository comentariuRepository) {
        this.comentariuRepository = comentariuRepository;
    }
    @Transactional
    public Comentariu create(Comentariu comentariu){
        return comentariuRepository.createComentariu(comentariu);
    }

    public Comentariu getComentariu(Long id){
        return comentariuRepository.findby(id).orElseThrow(() -> new EntityNotFoundException(String.format("Comentariul cu id-ul %s nu exista", id.toString())));
    }

    public List<Comentariu> getAll() {

        return comentariuRepository.findall();
    }

    public void update(String comentariu, Long id) {
        comentariuRepository.updateComentariu(id,comentariu);
    }

    public void delete(Long id) { comentariuRepository.deleteComentariu(id);}
}
