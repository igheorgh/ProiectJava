package com.java.proiect.repository;

import com.java.proiect.entity.ObiectVanzare;
import com.java.proiect.entity.Portofel;
import com.java.proiect.entity.User;
import com.java.proiect.exception.EntityNotFoundException;
import com.sun.tools.jconsole.JConsoleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Repository
public class ObiectVanzareRepository {

    private JdbcTemplate jdbcTemplate;
    private UserRepository userRepository;
    @Autowired
    public ObiectVanzareRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    public ObiectVanzare createVanzare(ObiectVanzare obiectVanzare){
        String createProdus="INSERT INTO obiect_vanzare  VALUES (?,?,?,?,?,?,?,?);";
        KeyHolder holder = new GeneratedKeyHolder();
        Optional<User> userOptional = userRepository.findby(obiectVanzare.getUser_id());
        if(!userOptional.isPresent()){
            throw new EntityNotFoundException(String.format("Nu exista user cu id %s", obiectVanzare.getUser_id().toString()));
        }
        else {
            Date data_vanzare= new Date(System.currentTimeMillis());
            Date data_expirare= new Date(System.currentTimeMillis()+(4320*60*1000));
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(createProdus, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setObject(1, null);
                preparedStatement.setDate(2, data_vanzare);
                preparedStatement.setDate(3, data_expirare);
                preparedStatement.setString(4, obiectVanzare.getDescriere());
                preparedStatement.setString(5, obiectVanzare.getImagine());
                preparedStatement.setDouble(6, obiectVanzare.getPret());
                preparedStatement.setObject(7, obiectVanzare.getUser_id());
                preparedStatement.setInt(8,0);
                return preparedStatement;
            }, holder);
            obiectVanzare.setId(holder.getKey().longValue());
            return obiectVanzare;
        }
    }

    public Optional<ObiectVanzare> findby(Long id){
        String selectbyid="Select * from obiect_vanzare where id=?";
        RowMapper<ObiectVanzare> mapper = getRowMapperProdus();
        return jdbcTemplate.query(selectbyid,mapper,id).stream().findAny();
    }

    public List<ObiectVanzare> findall(){
        String selectall="Select * from obiect_vanzare";
        RowMapper<ObiectVanzare> mapper = getRowMapperProdus();
        return jdbcTemplate.query(selectall,mapper);
    }


    public List<ObiectVanzare> findallordonatidupadatafinalizareDesc(){
        String selectall="Select * from obiect_vanzare order by data_expirare desc";
        RowMapper<ObiectVanzare> mapper = getRowMapperProdus();
        return jdbcTemplate.query(selectall,mapper);
    }

    public List<ObiectVanzare> findallordonatidupadatafinalizareASC(){
        String selectall="Select * from obiect_vanzare order by data_expirare asc";
        RowMapper<ObiectVanzare> mapper = getRowMapperProdus();
        return jdbcTemplate.query(selectall,mapper);
    }


    public List<ObiectVanzare> findallordonatidupadataDesc(){
        String selectall="Select * from obiect_vanzare order by data_adaugare desc";
        RowMapper<ObiectVanzare> mapper = getRowMapperProdus();
        return jdbcTemplate.query(selectall,mapper);
    }

    public List<ObiectVanzare> findallordonatidupadataASC(){
        String selectall="Select * from obiect_vanzare order by data_adaugare asc";
        RowMapper<ObiectVanzare> mapper = getRowMapperProdus();
        return jdbcTemplate.query(selectall,mapper);
    }

    public List<ObiectVanzare> findallordonatidupapretDesc(){
        String selectall="Select * from obiect_vanzare order by pret desc";
        RowMapper<ObiectVanzare> mapper = getRowMapperProdus();
        return jdbcTemplate.query(selectall,mapper);
    }

    public List<ObiectVanzare> findallordonatidupapretASC(){
        String selectall="Select * from obiect_vanzare order by pret asc";
        RowMapper<ObiectVanzare> mapper = getRowMapperProdus();
        return jdbcTemplate.query(selectall,mapper);
    }

    public List<ObiectVanzare> findallmyobj(Long id){
        String selectbyid="Select * from obiect_vanzare where user_id=?";
        RowMapper<ObiectVanzare> mapper = getRowMapperProdus();
        return jdbcTemplate.query(selectbyid,mapper,id);
    }

    public void deleteVanzare(Long id) {
        try {
            String deletePortofel="DELETE FROM obiect_vanzare WHERE (id = ?)";
            jdbcTemplate.update(deletePortofel,id);
        } catch (InvalidResultSetAccessException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProdus(Long id,int vandut) {
        try {
            String updateProdus="Update obiect_vanzare set vandut=? where id=?";
            jdbcTemplate.update(updateProdus,vandut,id);
        } catch (InvalidResultSetAccessException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDurataProdus(Long id,int zile) {
        try {
            Date finalizare= new Date(System.currentTimeMillis()+(60*60*24*zile*1000));
            String updateProdus="Update obiect_vanzare set data_expirare=? where id=?";
            System.out.println(updateProdus);
            jdbcTemplate.update(updateProdus,finalizare,id);
        } catch (InvalidResultSetAccessException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private RowMapper<ObiectVanzare>  getRowMapperProdus() {
        RowMapper<ObiectVanzare> mapper =
                ((resultSet, i) -> new ObiectVanzare(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getInt("vandut"),
                        resultSet.getDouble("pret"),
                        resultSet.getString("imagini"),
                        resultSet.getString("descriere"),
                        resultSet.getDate("data_adaugare"),
                        resultSet.getDate("data_expirare")
                ));
        return mapper;
    }


}
