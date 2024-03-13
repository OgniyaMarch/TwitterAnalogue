package com.example.demo.service.impl;

import com.example.demo.dao.CommonDao;
import com.example.demo.dao.ReactionDao;
import com.example.demo.dao.SubscriptionDao;
import com.example.demo.domain.api.communication.comment.CommentPostReq;
import com.example.demo.domain.api.communication.getBlockUsers.GetBlockUsersResp;
import com.example.demo.domain.constant.Code;
import com.example.demo.domain.dto.WhoseComment;
import com.example.demo.domain.response.Response;
import com.example.demo.domain.response.SuccessResponse;
import com.example.demo.domain.response.error.Error;
import com.example.demo.domain.response.error.ErrorResponse;
import com.example.demo.service.CommonService;
import com.example.demo.service.ReactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {
    private final CommonDao commonDao;
    private final ReactionDao reactionDao;
    private final SubscriptionDao subscriptionDao;
    private final CommonService commonService;


    @Override
    public ResponseEntity<Response> deleteLikePost(String accessToken, long postId) {
        long userId = commonDao.getUserIdByToken(accessToken);
        reactionDao.deleteLikePost(userId, postId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), OK);
    }

    @Override
    public ResponseEntity<Response> likePost(String accessToken, long postId) {
        long userId = commonDao.getUserIdByToken(accessToken);
        commonService.checkBlockByPostId(userId, postId);
        reactionDao.likePost(userId, postId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), OK);
    }

    @Override
    public ResponseEntity<Response> commentPost(String accessToken, CommentPostReq req) {
        long userId = commonDao.getUserIdByToken(accessToken);
        commonService.checkBlockByPostId(userId, req.getPostId());
        reactionDao.commentPost(userId, req);
        return new ResponseEntity<>(SuccessResponse.builder().build(), OK);
    }

    @Override
    public ResponseEntity<Response> deleteCommentPost(String accessToken, long commentId) {
        long userId = commonDao.getUserIdByToken(accessToken);

        WhoseComment whoseComment = reactionDao.whoseComment(commentId);
        log.info("userId: {}, whoseComment: {}", userId, whoseComment);

        if (whoseComment.getCommentUserId() == userId || whoseComment.getPostUserId() == userId){
            reactionDao.deleteComment(commentId);
            return new ResponseEntity<>(SuccessResponse.builder().build(), OK);
        } else {
            return new ResponseEntity<>(ErrorResponse.builder()
                    .error(Error.builder()
                            .code(Code.NOT_YOUR_COMMENT)
                            .userMessage("Це не ваш коментарій та не коментар вашого посту")
                            .build()).build(), BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Response> unblockUser(String accessToken, long blockUserId) {
        long userId = commonDao.getUserIdByToken(accessToken);
        reactionDao.unblockUser(userId, blockUserId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), OK);
    }

    @Override
    public ResponseEntity<Response> getBlockUsers(String accessToken) {
        long userId = commonDao.getUserIdByToken(accessToken);
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(GetBlockUsersResp.builder()
                        .blockUsers(reactionDao
                                .getBlockUsers(userId))
                        .build())
                .build(), OK);
    }

    @Override
    public ResponseEntity<Response> blockUser(String accessToken, long blockingUserId) {
        long userId = commonDao.getUserIdByToken(accessToken);

        if (userId == blockingUserId){
            return new ResponseEntity<>(ErrorResponse.builder()
                    .error(Error.builder()
                            .code(Code.NOT_BLOCK_YOURSELF)
                            .userMessage("You can't block yourself")
                            .build())
                    .build(), BAD_REQUEST);
        }
        reactionDao.blockUser(userId, blockingUserId);
        subscriptionDao.unsubscription(blockingUserId, userId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), OK);
    }
}
