package com.example.demo.dao.imp;


import com.example.demo.dao.SubscriptionDao;
import com.example.demo.domain.api.common.PostResp;
import com.example.demo.domain.api.common.PostRespRowMapper;
import com.example.demo.domain.api.common.UserResp;
import com.example.demo.domain.api.common.UserRespRowMapper;
import com.example.demo.domain.constant.Code;
import com.example.demo.domain.response.exception.CommonException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Repository
@Transactional
public class SubcriptionDaoImpl extends JdbcDaoSupport implements SubscriptionDao {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    @Override
    public List<UserResp> getMySubscribers(long userId) {
        return jdbcTemplate.query("SELECT id, nickname FROM user WHERE id IN (SELECT sub_user_id FROM subscription WHERE pub_user_id = ?);"
        ,new UserRespRowMapper(), userId);
    }

    @Override
    public List<UserResp> getMyPublishers(long userId) {
        return jdbcTemplate.query("SELECT id, nickname FROM user WHERE id IN (SELECT pub_user_id FROM subscription WHERE sub_user_id = ?);",
                new UserRespRowMapper(), userId);
    }

    @Override
    public void unsubscription(long subUserId, long pubUserId) {
        jdbcTemplate.update("DELETE FROM subscription WHERE sub_user_id = ? AND pub_user_id = ?;",
                subUserId, pubUserId);
    }

    @Override
    public void subscription(long subUserId, long pubUserId) {
        try {
            jdbcTemplate.update("INSERT INTO subscription(sub_user_id, pub_user_id) VALUES (?, ?);",
                    subUserId, pubUserId);
        } catch (DuplicateKeyException ex){
            log.error(ex.toString());
            throw CommonException.builder()
                    .code(Code.ALREADY_SUBSCRIBED)
                    .userMessage("You have subscribed this user")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        } catch (DataIntegrityViolationException ex){
            log.error(ex.toString());
            throw CommonException.builder()
                    .code(Code.PUBLISHER_NOT_FOUND)
                    .userMessage("The publisher isn't found")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @Override
    public List<PostResp> getMyPublishersPosts(long userId, int from, int limit) {
        return jdbcTemplate.query("SELECT phrase.id AS phrase_id, phrase.text, phrase.time_insert, phrase.user_id, u.nickname AS nickname " +
                "FROM phrase " +
                "       JOIN user AS u ON u.id = phrase.user_id " +
                "WHERE user_id IN (" +
                "   SELECT pub_user_id " +
                "   FROM subscription " +
                "   WHERE sub_user_id = ?) " +
                "ORDER BY phrase.time_insert DESC " +
                "LIMIT ?,?;", new PostRespRowMapper(), userId, from, limit);
    }
}
