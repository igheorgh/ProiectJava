package com.java.proiect.repository;

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
public class PortofelRepository {

    private JdbcTemplate jdbcTemplate;
    private UserRepository userRepository;
    @Autowired
    public PortofelRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    public Portofel createPortofel(Portofel portofel){
        String createportofel="INSERT INTO portofel VALUES(?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        Optional<User> userOptional = userRepository.findby(portofel.getUser_id());
        if(!userOptional.isPresent()){
            throw new EntityNotFoundException(String.format("Nu exista user cu id %s", portofel.getUser_id().toString()));
        }
        else {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(createportofel, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setObject(1, null);
                preparedStatement.setLong(3, portofel.getUser_id());
                preparedStatement.setDouble(2, portofel.getSuma_bani());
                return preparedStatement;
            }, holder);
            portofel.setId(holder.getKey().longValue());
            return portofel;
        }
    }

    public Optional<Portofel> findby(Long id){
        String selectbyid="Select * from portofel where user_id=?";
        RowMapper<Portofel> mapper = getRowMapperPortofel();
        return jdbcTemplate.query(selectbyid,mapper,id).stream().findAny();
    }

    public List<Portofel> findall(){
        String selectall="Select * from portofel";
        RowMapper<Portofel> mapper = getRowMapperPortofel();
        return jdbcTemplate.query(selectall,mapper);
    }


    public void deletePortofel(Long id) {
        try {
            String deletePortofel="DELETE FROM portofel WHERE (id = ?)";
            jdbcTemplate.update(deletePortofel,id);
        } catch (InvalidResultSetAccessException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePortofel(Long id,Double bani) {
        try {
            String deletePortofel="Update portofel set suma_bani=suma_bani+? where id=?";
            jdbcTemplate.update(deletePortofel,bani,id);
        } catch (InvalidResultSetAccessException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private RowMapper<Portofel>  getRowMapperPortofel() {
        RowMapper<Portofel> mapper =
                ((resultSet, i) -> new Portofel(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getDouble("suma_bani")

                ));
        return mapper;
    }
}
