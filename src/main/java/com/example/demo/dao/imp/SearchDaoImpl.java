package com.example.demo.dao.imp;


import com.example.demo.dao.SearchDao;
import com.example.demo.domain.api.search.searchTags.TagResp;
import com.example.demo.domain.api.search.searchTags.TagRespRowMapper;
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

//        return jdbcTemplate.query("SELECT id, text " +
//                "FROM (" +
//                "       select tag.id, text, count(tag.id) as c " +
//                "       from tag" +
//                "       join phrase_tag pt ON tag.id = pt.tag_id " +
//                "       where text like concat(lower(?), '%' " +
//                "       group by tag.id " +
//                "       order by count(tag.id) desc) t1" +
//                "UNION" +
//                "SELECT id, text " +
//                "FROM (" +
//                "       select tag.id, text, count(tag.id) as c " +
//                "       from tag " +
//                "       join phrase_tag pt ON tag.id = pt.tag_id " +
//                "       where text like concat('%', lower(?), '%') " +
//                "       group by tag.id " +
//                "       order by count(tag.id) desc) t2;"
//                , new TagRespRowMapper(), partTag, partTag);
//    }
}
