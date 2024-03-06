package com.example.demo.service.impl;


import com.example.demo.dao.UserDao;
import com.example.demo.domain.api.user.getMyPosts.GetMyPostsResp;
import com.example.demo.domain.api.user.getMyPosts.PostResp;
import com.example.demo.domain.api.user.login.LoginReq;
import com.example.demo.domain.api.user.login.LoginResp;
import com.example.demo.domain.api.user.publicPost.PublicPostReq;
import com.example.demo.domain.api.user.registration.RegistrationReq;
import com.example.demo.domain.api.user.registration.RegistrationResp;
import com.example.demo.domain.constant.Code;
import com.example.demo.domain.dto.User;
import com.example.demo.domain.entity.Post;
import com.example.demo.domain.response.Response;
import com.example.demo.domain.response.SuccessResponse;
import com.example.demo.domain.response.exception.CommonException;
import com.example.demo.service.UserService;

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
public class UserServiceImpl implements UserService {

//    private final ValidationUtils validationUtils;
    private final UserDao userDao;
    private final EncryptUtils encryptUtils;

    @Override
    public ResponseEntity<Response> publicPost(PublicPostReq req, String access_token) {
        long userId = userDao.getUserIdByToken(access_token);
        long phraseId = userDao.addPost(userId, req.getText());
        log.info("userId: {}, phraseId: {}", userId, phraseId);

        for (String tag : req.getTags()) {
            userDao.addTag(tag);
            userDao.addPostTag(phraseId, tag);
        }

        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    @Override
        public ResponseEntity<Response> registration(RegistrationReq req) {
            if (userDao.isExistsNickname(req.getAuthorizationReq().getNickname())){
                throw CommonException.builder().code(Code.NICKNAME_BUSY).userMessage("This nickname is taken.").httpStatus(HttpStatus.BAD_REQUEST).build();
            }
            String accessToken = UUID.randomUUID().toString().replace("-","") +
                    System.currentTimeMillis();
            String encryptPassword = encryptUtils.encryptPassword(req.getAuthorizationReq().getPassword());

            userDao.insertNewUser(User.builder().nickname(req.getAuthorizationReq().getNickname()).encryptPassword(encryptPassword).accessToken(accessToken).build());
            return new ResponseEntity<>(SuccessResponse.builder().data(RegistrationResp.builder().accessToken(accessToken).build()).build(), HttpStatus.OK);

        }

    @Override
    public ResponseEntity<Response> login(LoginReq req) {
        String encryptPassword = DigestUtils.md5DigestAsHex(req.getAuthorizationReq().getPassword().getBytes());
        String accessToken = userDao.getAccessToken(User.builder().nickname(req.getAuthorizationReq().getNickname()).encryptPassword(encryptPassword).build());
        return new ResponseEntity<>(SuccessResponse.builder().data(LoginResp.builder().accessToken(accessToken).build()).build(), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Response> getMyPosts(String accessToken) {
        long userId = userDao.getUserIdByToken(accessToken);
        List<Post> postList = userDao.getPostsByUserId(userId);

        List<PostResp> postRespList = new ArrayList<>();
        for (Post post : postList){
            List<String> tags = userDao.getTagsByPostId(post.getId());
            postRespList.add(PostResp.builder()
                    .id(post.getId())
                    .text(post.getText())
                    .timeInsert(post.getTimeInsert())
                    .tags(tags).build());
        }
        return new ResponseEntity<>(SuccessResponse.builder().data(GetMyPostsResp.builder().posts(postRespList).build()).build(), HttpStatus.OK);
    }
}
