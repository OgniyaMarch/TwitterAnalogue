package com.example.demo.dao.imp;

import com.example.demo.dao.Dao;
import com.example.demo.domen.constant.Code;
import com.example.demo.domen.dto.User;
import com.example.demo.domen.response.exception.CommonException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Slf4j
@Repository
@Transactional
public class DaoImpl  extends JdbcDaoSupport implements Dao {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    @Override
    public boolean isExistsNickname(String nickname) {
        return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT * FROM user WHERE nickname = ?);", Integer.class, nickname) != 0;
    }

    @Override
    public void insertNewUser(User user) {
        jdbcTemplate.update("INSERT INTO user(nickname,password,access_token) VALUES (?,?,?);",
                user.getNickname(), user.getEncryptPassword(), user.getAccessToken());

    }

    @Override
    public String getAccessToken(User user) {
        try{
            return jdbcTemplate.queryForObject("SELECT access_token FROM user WHERE nickname = ? AND password = ?;",
                    String.class, user.getNickname(), user.getEncryptPassword());
        } catch (EmptyResultDataAccessException ex){
            log.error(ex.toString());
            throw CommonException.builder().code(Code.USER_NOT_FOUND).message("Користувач не знайден").httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    public void addPostTag(long postId, String tag) {
        try {
            jdbcTemplate.update("INSERT IGNORE INTO phrase_tag(phrase_id,tag_id) VALUES (?, (SELECT id FROM tag WHERE text = LOWER(?)));", postId, tag);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
    }

    @Override
    public void addTag(String tag) {
        try {
            jdbcTemplate.update("INSERT INTO tag(text) SELECT DISTINCT LOWER(?) FROM tag WHERE NOT EXISTS (SELECT text FROM tag WHERE text = LOWER(?));", tag, tag);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
    }


//    @Override
//    public long addPost(long userId, String text) {
//        jdbcTemplate.update("INSERT INTO phrase(user_id,text) VALUES (?,?);", userId, text);
//        return jdbcTemplate.queryForObject("SELECT id FROM phrase WHERE id = LAST_INSERT_ID();", Long.class);
//    }
//
//    @Override
//    public long getIdByToken(String token) {
//        try {
//            return jdbcTemplate.queryForObject("SELECT id FROM user WHERE access_token = ?;", Long.class, token);
//        } catch (EmptyResultDataAccessException ex) {
//            log.error(ex.toString());
//            throw CommonException.builder().code(Code.AUTHORIZATION_ERROR).message("Ошибка авторизации").httpStatus(HttpStatus.BAD_REQUEST).build();
//        }
//    }
    @Override
    public long addPost(long userId, String text) {
        jdbcTemplate.update("INSERT INTO phrase(user_id,text) VALUES (?,?);", userId, text);
        return jdbcTemplate.queryForObject("SELECT id FROM phrase WHERE id = LAST_INSERT_ID();", Long.class);
    }

    @Override
    public long getIdByToken(String token) {
        try {
            return jdbcTemplate.queryForObject("SELECT id FROM user WHERE access_token = ?;", Long.class, token);
        } catch (EmptyResultDataAccessException ex) {
            log.error(ex.toString());
            throw CommonException.builder().code(Code.AUTHORIZATION_ERROR).message("Ошибка авторизации").httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }
}
