package com.java.proiect.repository;

import com.java.proiect.entity.Comentariu;
import com.java.proiect.entity.Licitatie;
import com.java.proiect.entity.ObiectVanzare;
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
public class ComentariuRepository {
    private JdbcTemplate jdbcTemplate;
    private UserRepository userRepository;
    private ObiectVanzareRepository obiectVanzareRepository;
    @Autowired

    public ComentariuRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository, ObiectVanzareRepository obiectVanzareRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.obiectVanzareRepository = obiectVanzareRepository;
    }

    public Comentariu createComentariu(Comentariu comentariu){
        String createComenatriu="INSERT INTO comentariu VALUES(?,?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        Optional<User> userOptional = userRepository.findby(comentariu.getUser_id());
        if(!userOptional.isPresent()){
            throw new EntityNotFoundException(String.format("Nu exista user cu id %s", comentariu.getUser_id().toString()));
        }
        Optional<ObiectVanzare> obiectVanzare = obiectVanzareRepository.findby(comentariu.getProdus_id());
        if(!obiectVanzare.isPresent()){
            throw new EntityNotFoundException(String.format("Nu exista produsul cu id %s", comentariu.getUser_id().toString()));
        }
        else {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(createComenatriu, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setObject(1, null);
                preparedStatement.setLong(3, comentariu.getUser_id());
                preparedStatement.setLong(4, comentariu.getProdus_id());
                preparedStatement.setString(2, comentariu.getComentariu());
                return preparedStatement;
            }, holder);
            comentariu.setId(holder.getKey().longValue());
            return comentariu;
        }
    }

    public Optional<Comentariu> findby(Long id){
        String selectbyid="Select * from comentariu where id=?";
        RowMapper<Comentariu> mapper = getRowMapperComentariu();
        return jdbcTemplate.query(selectbyid,mapper,id).stream().findAny();
    }

    public List<Comentariu> findall(){
        String selectall="Select * from comentariu";
        RowMapper<Comentariu> mapper = getRowMapperComentariu();
        return jdbcTemplate.query(selectall,mapper);
    }


    public void deleteComentariu(Long id) {
        try {
            String deleteComentariu="DELETE FROM comentariu WHERE (id = ?)";
            jdbcTemplate.update(deleteComentariu,id);
        } catch (InvalidResultSetAccessException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateComentariu(Long id,String comentariu) {
        try {
                String upfateLicitatie = "Update comentariu set comentariu=? where id=?";
                jdbcTemplate.update(upfateLicitatie, comentariu, id);
        } catch (InvalidResultSetAccessException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private RowMapper<Comentariu>  getRowMapperComentariu() {
        RowMapper<Comentariu> mapper =
                ((resultSet, i) -> new Comentariu(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("produs_id"),
                        resultSet.getString("comentariu")

                ));
        return mapper;
    }

}
