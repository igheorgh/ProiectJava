package com.java.proiect.repository;

import com.java.proiect.dto.UserInformationDto;
import com.java.proiect.dto.UserLoginDto;
import com.java.proiect.dto.UserUpdateDto;
import com.java.proiect.entity.User;
import com.java.proiect.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
@Repository
public class UserRepository {

    private JdbcTemplate jdbcTemplate;
    @Autowired

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User createAndSaveUser(User user) {
        String createUser="INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(createUser, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setString(7, user.getUsername());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setString(3, user.getEmail() );
            preparedStatement.setString(4, user.getFirstname());
            preparedStatement.setString(5, user.getLastname());
            preparedStatement.setInt(2, user.getAge());

            return preparedStatement;
        }, holder);

        user.setId(holder.getKey().longValue());
        return user;
    }


    public Optional<User> findby(Long id){
        String selectbyid="Select * from user where id=?";
        RowMapper<User> mapper = getRowMapperUser();
        return jdbcTemplate.query(selectbyid,mapper,id).stream().findAny();
    }

    public List<User> findall(){
        String selectall="Select * from user";
        RowMapper<User> mapper = getRowMapperUser();
        return jdbcTemplate.query(selectall,mapper);
    }

    public void deleteUser(Long id) {
        try {
            String deleteUser="DELETE FROM user WHERE (id = ?)";
            jdbcTemplate.update(deleteUser,id);
        } catch (InvalidResultSetAccessException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(UserUpdateDto user, Long id){
        try {
            Optional <User> userOptional= findby(id);
            if(!userOptional.isPresent()){
                throw new EntityNotFoundException(String.format("No user exist with id %s", id.toString()));
            }else {
                String updateUser = "UPDATE user SET age = ?, email = ?, firstname= ?, lastname = ?, password = ?, username = ? WHERE (id = ?)";
                int age;
                String email, firstname, lastname, password, username;
                User userbyid=userOptional.get();
                if(user.getAge()==null) { age= userbyid.getAge(); }
                else{ age= user.getAge(); }
                if(user.getFirstname()==null) { firstname= userbyid.getFirstname(); }
                else{ firstname= user.getFirstname(); }

                if(user.getLastname()==null) { lastname= userbyid.getLastname(); }
                else{ lastname= user.getLastname(); }

                if(user.getPassword()==null) { password= userbyid.getPassword(); }
                else{ password= user.getPassword(); }

                if(user.getUsername()==null) { username= userbyid.getUsername(); }
                else{ username= user.getUsername(); }

                if(user.getEmail()==null) { email= userbyid.getEmail(); }
                else{ email= user.getEmail(); }

                jdbcTemplate.update(updateUser, age, email,firstname, lastname, password, username, id);
            }
            } catch (InvalidResultSetAccessException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> Login(UserLoginDto userLoginDto) {
        String selectbyid="Select * from user where username=? and password=?";
        RowMapper<User> mapper = getRowMapperUser();
        return   jdbcTemplate.query(selectbyid,mapper,userLoginDto.getUsername(),userLoginDto.getPassword()).stream().findAny();
    }

    private RowMapper<User>  getRowMapperUser() {
        RowMapper<User> mapper =
                ((resultSet, i) -> new User(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getInt("age")
                ));
        return mapper;
    }


}
