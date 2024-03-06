package com.example.demo.dao.imp;

import com.example.demo.dao.UserDao;
import com.example.demo.domain.constant.Code;
import com.example.demo.domain.dto.User;
import com.example.demo.domain.entity.Post;
import com.example.demo.domain.entity.PostRowMapper;
import com.example.demo.domain.response.exception.CommonException;
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
import java.util.List;

@Slf4j
@Repository
@Transactional
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {

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
            throw CommonException.builder().code(Code.USER_NOT_FOUND).userMessage("Користувач не знайден").httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    public void addPostTag(long postId, String tag) {
        jdbcTemplate.update("INSERT IGNORE INTO phrase_tag(phrase_id,tag_id) VALUES (?, (SELECT id FROM tag WHERE text = LOWER(?)));", postId, tag);
    }


    /*
    INSERT INTO tag(text) SELECT DISTINCT LOWER("погода") FROM tag WHERE NOT EXISTS (SELECT text FROM tag WHERE text = LOWER("погода"));
    Цей запит не виконує запис до бази даних через те,
    що він намагається вибрати текст з таблиці tag, якого не існує.
    Проблема полягає у тому, що у підзапиті також використовується функція LOWER("погода"),
    що вже є константою, і вона завжди повертає одне і те саме значення.
    Тому підзапит завжди повертає результат, а саме, що текст "погода" вже існує в таблиці tag,
    і тому головний запит не вставляє новий рядок.
     */
    @Override
    public void addTag(String tag) {
        jdbcTemplate.update("INSERT INTO tag(text) " +
                "SELECT DISTINCT LOWER(?) " +
                "FROM dual " +
                "WHERE NOT EXISTS (" +
                "SELECT text " +
                "FROM tag " +
                "WHERE text = LOWER(?));", tag, tag);
    }

    @Override
    public long addPost(long userId, String text) {
        jdbcTemplate.update("INSERT INTO phrase(user_id,text) VALUES (?,?);", userId, text);
        return jdbcTemplate.queryForObject("SELECT id FROM phrase WHERE id = LAST_INSERT_ID();", Long.class);
    }

    @Override
    public long getUserIdByToken(String accessToken) {
        try {
            return jdbcTemplate.queryForObject("SELECT id FROM user WHERE access_token = ?;", Long.class, accessToken);
        } catch (EmptyResultDataAccessException ex) {
            log.error(ex.toString());
            throw CommonException.builder().code(Code.AUTHORIZATION_ERROR).userMessage("Ошибка авторизации").httpStatus(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    public List<String> getTagsByPostId(long postId) {
        return jdbcTemplate.queryForList("SELECT text FROM tag WHERE id IN(SELECT tag_id FROM phrase_tag WHERE phrase_id = ?);", String.class, postId);
    }


    @Override
    public List<Post> getPostsByUserId(long userId) {
        return jdbcTemplate.query("SELECT * FROM phrase WHERE user_id = ? ORDER BY time_insert DESC;", new PostRowMapper(), userId);
    }
}
