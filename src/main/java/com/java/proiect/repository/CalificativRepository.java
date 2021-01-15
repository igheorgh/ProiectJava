package com.java.proiect.repository;

import com.java.proiect.entity.Calificativ;
import com.java.proiect.entity.Comentariu;
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
public class CalificativRepository {
    private JdbcTemplate jdbcTemplate;
    private UserRepository userRepository;
    @Autowired

    public CalificativRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    public Calificativ createCalificativ(Calificativ calificativ){
        String createCalificativ="INSERT INTO calificativ VALUES(?,?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        Optional<User> userOptional = userRepository.findby(calificativ.getVanzator_id());
        Optional<User> userOptional2 = userRepository.findby(calificativ.getCumparator_id());
        if(!userOptional.isPresent() ){
            throw new EntityNotFoundException(String.format("Nu exista user cu id %s", calificativ.getVanzator_id().toString()));
        }
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(createCalificativ, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setObject(1, null);
                preparedStatement.setLong(2, calificativ.getVanzator_id());
                preparedStatement.setLong(3, calificativ.getCumparator_id());
                preparedStatement.setDouble(4, calificativ.getNota());
                return preparedStatement;
            }, holder);
        calificativ.setId(holder.getKey().longValue());
            return calificativ;
        }

    public Optional<Calificativ> findby(Long id){
        String selectbyid="Select * from calificativ where id=?";
        RowMapper<Calificativ> mapper = getRowMapperCalificativ();
        return jdbcTemplate.query(selectbyid,mapper,id).stream().findAny();
    }

    public List<Calificativ> findall(){
        String selectall="Select * from calificativ";
        RowMapper<Calificativ> mapper = getRowMapperCalificativ();
        return jdbcTemplate.query(selectall,mapper);
    }

    public List<Calificativ> findallbyUSer(Long id){
        String selectall="Select * from calificativ where vanzator_id=?";
        RowMapper<Calificativ> mapper = getRowMapperCalificativ();
        return jdbcTemplate.query(selectall,mapper,id);
    }

    public void deleteCalificativ(Long id) {
        try {
            String deleteCalificativ="DELETE FROM calificativ WHERE (id = ?)";
            jdbcTemplate.update(deleteCalificativ,id);
        } catch (InvalidResultSetAccessException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCalificativ(Long id,Double calificativ) {
        try {
            String upfateLicitatie = "Update calificativ set calificativ=? where id=?";
            jdbcTemplate.update(upfateLicitatie, calificativ, id);
        } catch (InvalidResultSetAccessException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private RowMapper<Calificativ>  getRowMapperCalificativ() {
        RowMapper<Calificativ> mapper =
                ((resultSet, i) -> new Calificativ(
                        resultSet.getLong("id"),
                        resultSet.getLong("vanzator_id"),
                        resultSet.getLong("cumparator_id"),
                        resultSet.getDouble("calificativ")

                ));
        return mapper;
    }
}
