package com.example.demo.service.impl;


import com.example.demo.dao.Dao;
import com.example.demo.domen.api.*;
import com.example.demo.domen.constant.Code;
import com.example.demo.domen.dto.User;
import com.example.demo.domen.entity.Post;
import com.example.demo.domen.response.Response;
import com.example.demo.domen.response.SuccessResponse;
import com.example.demo.domen.response.exception.CommonException;
import com.example.demo.service.TwitterAnalogueService;

import com.example.demo.util.EncryptUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TwitterAnalogueServiceImpl  implements TwitterAnalogueService {

//    private final ValidationUtils validationUtils;
    private final Dao dao;
    private final EncryptUtils encryptUtils;

    @Override
    public ResponseEntity<Response> publicPost(PublicPostReq req, String access_token) {
        long userId = dao.getUserIdByToken(access_token);
        long phraseId = dao.addPost(userId, req.getText());
        log.info("userId: {}, phraseId: {}", userId, phraseId);

        for (String tag : req.getTags()) {
            dao.addTag(tag);
            dao.addPostTag(phraseId, tag);
        }

        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    @Override
        public ResponseEntity<Response> registration(RegistrationReq req) {
            if (dao.isExistsNickname(req.getAuthorization().getNickname())){
                throw CommonException.builder().code(Code.NICKNAME_BUSY).userMessage("This nickname is taken.").httpStatus(HttpStatus.BAD_REQUEST).build();
            }
            String accessToken = UUID.randomUUID().toString().replace("-","") +
                    System.currentTimeMillis();
            String encryptPassword = encryptUtils.encryptPassword(req.getAuthorization().getPassword());

            dao.insertNewUser(User.builder().nickname(req.getAuthorization().getNickname()).encryptPassword(encryptPassword).accessToken(accessToken).build());
            return new ResponseEntity<>(SuccessResponse.builder().data(RegistrationResp.builder().accessToken(accessToken).build()).build(), HttpStatus.OK);

        }

    @Override
    public ResponseEntity<Response> login(LoginReq req) {
        String encryptPassword = DigestUtils.md5DigestAsHex(req.getAuthorization().getPassword().getBytes());
        String accessToken = dao.getAccessToken(User.builder().nickname(req.getAuthorization().getNickname()).encryptPassword(encryptPassword).build());
        return new ResponseEntity<>(SuccessResponse.builder().data(LoginResp.builder().accessToken(accessToken).build()).build(), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Response> getMyPosts(String accessToken) {
        long userId = dao.getUserIdByToken(accessToken);
        List<Post> postList = dao.getPostsByUserId(userId);

        List<PostResp> postRespList = new ArrayList<>();
        for (Post post : postList){
            List<String> tags = dao.getTagsByPostId(post.getId());
            postRespList.add(PostResp.builder()
                    .id(post.getId())
                    .text(post.getText())
                    .timeInsert(post.getTimeInsert())
                    .tags(tags).build());
        }
        return new ResponseEntity<>(SuccessResponse.builder().data(GetMyPostsResp.builder().posts(postRespList).build()).build(), HttpStatus.OK);
    }
}
