package com.example.spring.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring.libs.Pagination;

@Service
public class PostService {

    @Autowired
    PostDao postDao;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 비밀번호 검증
    private boolean verifyPassword(PostDto post) {
        PostDto originalPost = postDao.read(post.getId());
        return passwordEncoder.matches(post.getPassword(), originalPost.getPassword());
    }

    // 게시글 등록
    public boolean create(PostDto post) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(post.getPassword());
        post.setPassword(encodedPassword);

        // 게시글 등록
        int result = postDao.create(post);
        return result > 0;
    }

    // 게시글 목록
    public Map<String, Object> list(int currentPage, int listCountPerPage, int pageCountPerPage, String searchType, String searchKeyword) {
        // 전체 게시글 수 조회
        int totalCount = postDao.totalCount(searchType, searchKeyword);

        // 페이지네이션 정보 생성
        Pagination pagination = new Pagination(currentPage, listCountPerPage, pageCountPerPage, totalCount);

        // 페이징된 게시글 목록 조회
        List<PostDto> posts = postDao.list(pagination.offset(), listCountPerPage, searchType, searchKeyword);

        // 결과 맵 생성
        Map<String, Object> result = new HashMap<>();
        result.put("posts", posts);
        result.put("pagination", pagination);
        result.put("searchType", searchType);
        result.put("searchKeyword", searchKeyword);

        return result;
    }

    // 게시글 보기
    public PostDto read(int id) {
        return postDao.read(id);
    }

    // 게시글 수정
    public boolean update(PostDto post) {
        // 비밀번호 검증
        if (!verifyPassword(post)) {
            return false;
        }

        int result = postDao.update(post);
        return result > 0;
    }

    // 게시글 삭제
    public boolean delete(PostDto post) {
        // 비밀번호 검증
        if (!verifyPassword(post)) {
            return false;
        }

        int result = postDao.delete(post.getId());
        return result > 0;
    }
}
