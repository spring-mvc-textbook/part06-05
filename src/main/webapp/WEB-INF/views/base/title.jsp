<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${page_title != null}">
<%-- 페이지 제목 --%>
<div class="row">
    <div class="col-12">
        <h1>${page_title}</h1>
    </div>
</div>
<%--// 페이지 제목 --%>
</c:if>