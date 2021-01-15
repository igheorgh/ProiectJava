package com.java.proiect.service;

import com.java.proiect.entity.ObiectVanzare;
import com.java.proiect.entity.Portofel;
import com.java.proiect.exception.EntityNotFoundException;
import com.java.proiect.repository.ObiectVanzareRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ObiectVanzareService {
    private ObiectVanzareRepository obiectVanzareRepository;

    public ObiectVanzareService(ObiectVanzareRepository obiectVanzareRepository) {
        this.obiectVanzareRepository = obiectVanzareRepository;
    }

    @Transactional
    public ObiectVanzare create(ObiectVanzare obiectVanzare){
        return obiectVanzareRepository.createVanzare(obiectVanzare);
    }

    public ObiectVanzare getProdusbyId(Long id){
        return obiectVanzareRepository.findby(id).orElseThrow(() -> new EntityNotFoundException(String.format("Obiectul cu id-ul %s nu exista", id.toString())));
    }

    public List<ObiectVanzare> getAll() {

        return obiectVanzareRepository.findall();
    }
    public List<ObiectVanzare> getallmyprod(Long id) {

        return obiectVanzareRepository.findallmyobj(id);
    }
    public void update(int vandut, Long id) {
        obiectVanzareRepository.updateProdus(id,vandut);
    }

    public void delete(Long id) { obiectVanzareRepository.deleteVanzare(id);}

    public void updateZIle(int zile, Long id)
    {
        obiectVanzareRepository.updateDurataProdus(id,zile);
    }


    public List<ObiectVanzare> getAllDFdesc() {

        return obiectVanzareRepository.findallordonatidupadatafinalizareDesc();
    }
    public List<ObiectVanzare> getAllDFasc() {

        return obiectVanzareRepository.findallordonatidupadatafinalizareASC();
    }

    public List<ObiectVanzare> getAllDPdesc() {

        return obiectVanzareRepository.findallordonatidupadataDesc();
    }
    public List<ObiectVanzare> getAllDPasc() {

        return obiectVanzareRepository.findallordonatidupadataASC();
    }

    public List<ObiectVanzare> getAllPRdesc() {

        return obiectVanzareRepository.findallordonatidupapretDesc();
    }
    public List<ObiectVanzare> getAllPRasc() {

        return obiectVanzareRepository.findallordonatidupapretASC();
    }
}
