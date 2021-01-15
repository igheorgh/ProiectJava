package com.java.proiect.service;

import com.java.proiect.dto.UserUpdateDto;
import com.java.proiect.entity.Portofel;
import com.java.proiect.entity.User;
import com.java.proiect.exception.EntityNotFoundException;
import com.java.proiect.repository.PortofelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PortofelService {
    private PortofelRepository portofelRepository;

    public PortofelService(PortofelRepository portofelRepository) {
        this.portofelRepository = portofelRepository;
    }
    @Transactional
    public Portofel create(Portofel portofel){
        return portofelRepository.createPortofel(portofel);
    }

    public Portofel getPortofelbyId(Long id){
        return portofelRepository.findby(id).orElseThrow(() -> new EntityNotFoundException(String.format("Portofelul cu id-ul %s nu exista", id.toString())));
    }

    public List<Portofel> getAll() {

        return portofelRepository.findall();
    }

    public void update(Double bani, Long id) {
        portofelRepository.updatePortofel(id,bani);
    }

    public void delete(Long id) { portofelRepository.deletePortofel(id);}
}
