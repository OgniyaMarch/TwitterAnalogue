package com.example.demo.dao.imp;


import com.example.demo.dao.SearchDao;
import com.example.demo.domain.api.common.*;
import com.example.demo.domain.api.search.searchPostsByPartWord.SearchPostsByPartWordReq;
import com.example.demo.domain.api.search.searchPostsByTag.SearchPostsByTagReq;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Repository
@Transactional
public class SearchDaoImpl extends JdbcDaoSupport implements SearchDao {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    @Override
    public List<TagResp> searchTags(String partTag) {
        return jdbcTemplate.query("SELECT id, text " +
                        "FROM (" +
                        "         SELECT tag.id, text, count(tag.id) AS c " +
                        "         FROM tag " +
                        "                  JOIN phrase_tag pt ON tag.id = pt.tag_id " +
                        "         WHERE text LIKE CONCAT(LOWER(?), '%') " +
                        "         GROUP BY tag.id " +
                        "         ORDER BY count(tag.id) DESC) t1 " +
                        "UNION " +
                        "SELECT id, text " +
                        "FROM (" +
                        "         SELECT tag.id, text, count(tag.id) AS c " +
                        "         FROM tag " +
                        "                  JOIN phrase_tag pt ON tag.id = pt.tag_id " +
                        "         WHERE text LIKE CONCAT('%', LOWER(?), '%') " +
                        "         GROUP BY tag.id " +
                        "         ORDER BY count(tag.id) DESC) t2;"
                ,  new TagRespRowMapper(), partTag, partTag);
    }

    @Override
    public List<PostResp> searchPostsByTag(SearchPostsByTagReq req, long userId) {
        return jdbcTemplate.query("SELECT phrase_id, user_id, nickname, text, time_insert " +
                "FROM ( " +
                "       SELECT phrase.id AS phrase_id, u.id AS user_id, u.nickname, phrase.text, phrase.time_insert " +
                "       FROM phrase " +
                "           JOIN user AS u ON phrase.user_id = u.id " +
                "           WHERE phrase.id IN (SELECT phrase_id FROM phrase_tag WHERE tag_id = ?)) AS t" +
                "       WHERE user_id NOT IN (SELECT user_id FROM block WHERE blocked_user_id = ?) " +
                "ORDER BY " + req.getSort().getValue() + ";", new PostRespRowMapper(), req.getTagId(), userId);
    }

    @Override
    public List<PostResp> searchPostsByPartWord(SearchPostsByPartWordReq req, long userId) {
        return jdbcTemplate.query("SELECT phrase_id, user_id, nickname, text, time_insert " +
                "FROM ( " +
                "       SELECT phrase.id AS phrase_id, u.id AS user_id, u.nickname, phrase.text, phrase.time_insert " +
                "       FROM phrase " +
                "           JOIN user AS u ON phrase.user_id = u.id " +
                "       WHERE phrase.text LIKE CONCAT('%', ?, '%')) AS t " +
                "WHERE user_id NOT IN (SELECT user_id FROM block WHERE blocked_user_id = ?) " +
                "ORDER BY " + req.getSort().getValue() + ";", new PostRespRowMapper(), req.getPartWord(), userId);
    }

    @Override
    public List<UserResp> searchUsersByPartNickname(String partNickname) {
        return jdbcTemplate.query("SELECT id, nickname " +
                "FROM (" +
                "       SELECT id, nickname " +
                "       FROM user " +
                "       WHERE nickname LIKE CONCAT(?, '%')) t1 " +
                "UNION " +
                "SELECT id, nickname " +
                "FROM (" +
                "       SELECT id, nickname " +
                "       FROM user " +
                "       WHERE nickname LIKE CONCAT('%', ?, '%')) t2; "
                        , new UserRespRowMapper(), partNickname, partNickname);
    }
}
