package com.example.demo.domain.api.common;

import com.example.demo.domain.api.common.TagResp;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagRespRowMapper implements RowMapper<TagResp> {
    @Override
    public TagResp mapRow(ResultSet row, int rowNum) throws SQLException {
        return TagResp.builder()
                .tagId(row.getLong("id"))
                .text(row.getString("text"))
                .build();
    }
}
