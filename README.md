# PART 06. 게시글 등록 및 수정 기능 개선하기

이 파트에서는 게시글 등록 및 수정 기능을 더욱 안전하고 효율적으로 개선하는 과정을 다룹니다.
비밀번호 암호화, 파일 업로드, jQuery 및 jQuery Validation Plugin 적용, 텍스트 에디터 활용 등을 통해
보안성과 사용자 경험을 향상시키는 것이 목표입니다.

## 01. 비밀번호 암호화 처리하기

게시판의 보안 강화를 위해 비밀번호를 암호화하는 방법을 설명합니다.
BCryptPasswordEncoder를 활용하여 비밀번호를 해싱하고 데이터베이스에 저장하는 방법을 다룹니다.
회원 인증 및 비밀번호 검증 과정에서 해싱된 비밀번호를 비교하는 방법을 소개합니다.

- 📖 교재: [https://wikidocs.net/267710](https://wikidocs.net/267710)
- 💻 코드: [https://github.com/spring-mvc-textbook/part06-01](https://github.com/spring-mvc-textbook/part06-01)

## 02. 파일 업로드 기능 만들기

게시글 등록 및 수정 시 이미지나 첨부 파일을 업로드하는 기능을 추가합니다.
Spring MVC의 MultipartResolver를 사용하여 파일 업로드를 처리하는 방법을 설명합니다.
업로드된 파일을 서버에 저장하는 방식과 데이터베이스에 파일 정보를 기록하는 방법을 다룹니다.

- 📖 교재: [https://wikidocs.net/267992](https://wikidocs.net/267992)
- 💻 코드: [https://github.com/spring-mvc-textbook/part06-02](https://github.com/spring-mvc-textbook/part06-02)

## 03. jQuery 적용하기

게시판에서 사용자 인터페이스(UI)를 개선하기 위해 jQuery를 적용하는 방법을 다룹니다.
버튼 클릭, 이벤트 핸들링을 활용하여 동적인 웹 페이지를 구성하는 방법을 설명합니다.

- 📖 교재: [https://wikidocs.net/268446](https://wikidocs.net/268446)
- 💻 코드: [https://github.com/spring-mvc-textbook/part06-03](https://github.com/spring-mvc-textbook/part06-03)

## 04. jQuery Validation Plugin 적용하기

입력 폼 유효성 검사를 자동으로 처리할 수 있도록 jQuery Validation Plugin을 적용합니다.
클라이언트 측에서 사용자 입력을 검증하여 서버 부담을 줄이는 방법을 설명합니다.
필수 입력 항목, 글자 수 제한 등을 다룹니다.

- 📖 교재: [https://wikidocs.net/268445](https://wikidocs.net/268445)
- 💻 코드: [https://github.com/spring-mvc-textbook/part06-04](https://github.com/spring-mvc-textbook/part06-04)

## 05. TinyMCE 적용하기

게시글 작성 시 보다 풍부한 텍스트 편집 기능을 제공하기 위해 TinyMCE를 적용합니다.
TinyMCE의 주요 기능(텍스트 포맷팅, 이미지 삽입, 링크 추가 등)을 활용하는 방법을 설명합니다.
TinyMCE를 사용하여 HTML 콘텐츠를 저장하고 출력하는 과정을 다룹니다.

- 📖 교재: [https://wikidocs.net/268435](https://wikidocs.net/268435)
- 💻 코드: [https://github.com/spring-mvc-textbook/part06-05](https://github.com/spring-mvc-textbook/part06-05)