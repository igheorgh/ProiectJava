package com.java.proiect.repository;


import com.java.proiect.entity.Licitatie;
import com.java.proiect.entity.ObiectVanzare;
import com.java.proiect.entity.Portofel;
import com.java.proiect.entity.User;
import com.java.proiect.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class LicitatieRepository {
    private JdbcTemplate jdbcTemplate;
    private UserRepository userRepository;
    private ObiectVanzareRepository obiectVanzareRepository;
    private PortofelRepository portofelRepository;
    @Autowired

    public LicitatieRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository, ObiectVanzareRepository obiectVanzareRepository,PortofelRepository portofelRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.obiectVanzareRepository = obiectVanzareRepository;
        this.portofelRepository=portofelRepository;
    }

    public Licitatie createLicitatie(Licitatie licitatie){
        String createLicitatie="INSERT INTO licitatie VALUES(?,?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        Optional<User> userOptional = userRepository.findby(licitatie.getUser_id());
        if(!userOptional.isPresent()){
            throw new EntityNotFoundException(String.format("Nu exista user cu id %s", licitatie.getUser_id().toString()));
        }
        Optional<ObiectVanzare> obiectVanzare = obiectVanzareRepository.findby(licitatie.getProdus_id());
        if(!obiectVanzare.isPresent()){
            throw new EntityNotFoundException(String.format("Nu exista produsul cu id %s", licitatie.getUser_id().toString()));
        }
        else {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(createLicitatie, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setObject(1, null);
                preparedStatement.setLong(3, licitatie.getUser_id());
                preparedStatement.setLong(4, licitatie.getProdus_id());
                preparedStatement.setDouble(2, licitatie.getOferta());
                return preparedStatement;
            }, holder);
            licitatie.setId(holder.getKey().longValue());
            return licitatie;
        }
    }

    public Optional<Licitatie> findby(Long id){
        String selectbyid="Select * from licitatie where id=?";
        RowMapper<Licitatie> mapper = getRowMapperLicitatiel();
        return jdbcTemplate.query(selectbyid,mapper,id).stream().findAny();
    }

    public List<Licitatie> findall(){
        String selectall="Select * from licitatie";
        RowMapper<Licitatie> mapper = getRowMapperLicitatiel();
        return jdbcTemplate.query(selectall,mapper);
    }


    public void deleteLicitatie(Long id) {
        try {
            String deleteLicitatie="DELETE FROM licitatie WHERE (id = ?)";
            jdbcTemplate.update(deleteLicitatie,id);
        } catch (InvalidResultSetAccessException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateLicitatie(Long id,Double oferta) {
        try {
            Optional<Licitatie> licitatie = findby(id);
            if(licitatie.get().getOferta()<oferta)
            {
                String upfateLicitatie = "Update licitatie set oferta=? where id=?";
                jdbcTemplate.update(upfateLicitatie, oferta, id);
            }else{
                throw new RuntimeException(String.format("Oferta este prea mica"));
            }
        } catch (InvalidResultSetAccessException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void Achitare(Long id) {
        Optional<Licitatie> licitatie= findby(id);
        if(!licitatie.isPresent()){
            throw new EntityNotFoundException(String.format("Nu exista licitatia cu id %s", licitatie.get().getId()));
        }
        else{
            Optional<ObiectVanzare> obiectVanzare =obiectVanzareRepository.findby(licitatie.get().getProdus_id());
            if(!obiectVanzare.isPresent())
            {
                throw new EntityNotFoundException(String.format("Nu exista produsul cu id %s", obiectVanzare.get().getId()));
            }
            else
            {
                Optional<User> userVanzator = userRepository.findby(obiectVanzare.get().getUser_id());
                if(!userVanzator.isPresent()){
                    throw new EntityNotFoundException(String.format("Nu exista userul cu id %s", userVanzator.get().getId()));
                }
                else
                {
                    Optional<User> userCumparator = userRepository.findby(licitatie.get().getUser_id());
                    if(!userCumparator.isPresent()){
                        throw new EntityNotFoundException(String.format("Nu exista userul cu id %s", userCumparator.get().getId()));
                    }
                    else{
                        Optional<Portofel> portofelVanzator = portofelRepository.findby(userVanzator.get().getId());
                        if(!portofelVanzator.isPresent()){
                            throw new EntityNotFoundException(String.format("Nu exista portofelul cu id %s", portofelVanzator.get().getId().toString()));
                        }
                        else{
                            Optional<Portofel> portofelCumparator = portofelRepository.findby(userCumparator.get().getId());
                            if(!portofelCumparator.isPresent()){
                                throw new EntityNotFoundException(String.format("Nu exista portofelul cu id %s", portofelCumparator.get().getId().toString()));
                            }
                            else{
                                portofelRepository.updatePortofel(portofelVanzator.get().getId(), licitatie.get().getOferta());
                                portofelRepository.updatePortofel(portofelCumparator.get().getId(), (-1)*licitatie.get().getOferta());
                                obiectVanzareRepository.updateProdus(obiectVanzare.get().getId(),1);
                            }
                        }
                    }
                }
            }
        }
    }

    private RowMapper<Licitatie>  getRowMapperLicitatiel() {
        RowMapper<Licitatie> mapper =
                ((resultSet, i) -> new Licitatie(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("produs_id"),
                        resultSet.getDouble("oferta")

                ));
        return mapper;
    }

}
