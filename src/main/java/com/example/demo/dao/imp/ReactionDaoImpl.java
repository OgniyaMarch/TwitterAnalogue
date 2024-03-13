package com.example.demo.dao.imp;

import com.example.demo.dao.ReactionDao;
import com.example.demo.domain.api.common.UserResp;
import com.example.demo.domain.api.common.UserRespRowMapper;
import com.example.demo.domain.api.communication.comment.CommentPostReq;
import com.example.demo.domain.dto.WhoseComment;
import com.example.demo.domain.dto.WhoseCommentRowMapper;
import com.example.demo.domain.response.exception.CommonException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.util.List;

import static com.example.demo.domain.constant.Code.COMMENT_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Repository
@Transactional
public class ReactionDaoImpl extends JdbcDaoSupport implements ReactionDao {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    @Override
    public void deleteLikePost(long userId, long postId) {
        jdbcTemplate.update("DELETE FROM like_phrase WHERE phrase_id = ? AND user_id = ?;",
                postId, userId);
    }

    @Override
    public void likePost(long userId, long postId) {
        jdbcTemplate.update("INSERT IGNORE INTO like_phrase(phrase_id, user_id) VALUES (?,?);",
                postId, userId);
    }

    @Override
    public void commentPost(long userId, CommentPostReq req) {
        jdbcTemplate.update("INSERT IGNORE INTO comment(user_id, phrase_id, text) VALUES (?,?,?);",
                userId, req.getPostId(), req.getText());
    }

    @Override
    public void deleteComment(long commentId) {
        jdbcTemplate.update("DELETE FROM comment WHERE id = ?;", commentId);
    }

    @Override
    public WhoseComment whoseComment(long commentId) {
        try {
            return jdbcTemplate.queryForObject("SELECT comment.user_id AS comment_user_id, p.user_id AS phrase_user_id " +
                    "FROM comment JOIN phrase AS p ON p.id = comment.phrase_id " +
                    "WHERE comment.id = ?;", new WhoseCommentRowMapper(), commentId);
        } catch (EmptyResultDataAccessException e){
            throw CommonException.builder()
                    .code(COMMENT_NOT_FOUND)
                    .userMessage("Comment isn't found")
                    .httpStatus(BAD_REQUEST).build();
        }
    }

    @Override
    public void unblockUser(long userId, long blockUserId) {
        jdbcTemplate.update("DELETE FROM block WHERE user_id = ? AND block_user_id = ?;", userId, blockUserId);
    }

    @Override
    public List<UserResp> getBlockUsers(long userId) {
        return jdbcTemplate.query("SELECT id, nickname FROM user WHERE id IN (SELECT block_user_id FROM block WHERE user_id = ?);", new UserRespRowMapper(), userId);
    }

    @Override
    public void blockUser(long userId, long blockUserId) {
        jdbcTemplate.update("INSERT IGNORE INTO block(user_id, block_user_id) VALUES (?,?);", userId, blockUserId);
    }
}
