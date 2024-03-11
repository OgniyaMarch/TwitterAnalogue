package com.example.demo.domain.api.common;


import com.example.demo.domain.api.common.PostResp;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostRespRowMapper  implements RowMapper<PostResp> {
    @Override
    public PostResp mapRow(ResultSet row, int rowNum) throws SQLException {
        return PostResp.builder()
                .postId(row.getLong("phrase_id"))
                .userId(row.getLong("user_id"))
                .nickname(row.getString("nickname"))
                .text(row.getString("text"))
                .timeInsert(row.getString("time_insert"))
                .build();
    }
}
