package com.example.spring.libs;

import lombok.Data;

@Data
public class Pagination {
    private int currentPage;        // 현재 페이지
    private int totalCount;         // 전체 게시글 수
    private int listCountPerPage;   // 한 페이지에서 불러올 게시글 수
    private int pageCountPerPage;   // 한 페이지에서 보여질 페이지 수
    private int totalPages;         // 전체 페이지 수
    private int startPage;          // 시작 페이지 번호
    private int endPage;            // 끝 페이지 번호

    public Pagination(int currentPage, int listCountPerPage, int pageCountPerPage, int totalCount) {
        this.currentPage = currentPage;
        this.listCountPerPage = listCountPerPage;
        this.pageCountPerPage = pageCountPerPage;
        this.totalCount = totalCount;

        // 전체 페이지 수 계산
        this.totalPages = (int) Math.ceil((double) totalCount / listCountPerPage);

        // 현재 페이지 기준으로 화면에 보여질 시작 페이지와 끝 페이지 계산
        this.startPage = ((currentPage - 1) / pageCountPerPage) * pageCountPerPage + 1;
        this.endPage = Math.min(startPage + (pageCountPerPage - 1), totalPages);
    }

    // 테이블에서 몇 번째 데이터부터 조회할지 계산
    public int offset() { 
        return (currentPage - 1) * listCountPerPage; 
    }
} 
