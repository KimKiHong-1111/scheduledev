# 일정관리 프로젝트

#요구사항 확인하기
#비기능적 요구사항
#요구사항이 아닌것
-탈퇴는 구현하지 않는다.
-로그인 로그아웃 구현 / 탈퇴는 미지원
#API Design 
-400 Bad request
-404 Not found
kakao.api명세서 확인하기
-api path
-api query param(type,예시)
-api response(status code, 예시)

text
POST api/v1/user/login
header: authorization
예시:Authorization{key}


#ERD mermaid문법
사진으로 캡처하기보다는 sql 쿼리작성하듯이 erd를 하면된다.



### Lv 0. API 명세 및 ERD 작성   `필수`

- [ ]  **API 명세서 작성하기**
    - [ ]  API명세서는 프로젝트 root(최상위) 경로의 `README.md` 에 작성
    - 참고) API 명세서 작성 가이드
        - API 명세서란 API명, 요청 값(파라미터), 반환 값, 인증/인가 방식, 데이터 및 전달 형식 등 API를 정확하게 호출하고 그 결과를 명확하게 해석하는데 필요한 정보들을 일관된 형식으로 기술한 것을 의미합니다.
        - request 및 response는 [json(링크)](https://namu.wiki/w/JSON) 형태로 작성합니다.
        
        [예) [서점] 책 API 설계하기](https://www.notion.so/1832dc3ef5148117b04eff90ef7264e7?pvs=21)
        
        > API 명세서 추천 무료 Tool
        > 
        > 
        > [Document your APIs in Postman | Postman Learning Center](https://learning.postman.com/docs/publishing-your-api/api-documentation-overview/)

Schedule

|  기능                |Method |URL        |request |response|상태코드|
|------                 |-------|-----      |------- |------- |-------  |
| 일정등록              |POST   |schedules/create   |요청body|등록정보|200:정상등록|
| 전체일정 조회         |GET    |schedules/   |요청param|다건 응답정보|200:정상조회|
| 선택일정조회          |GET    |schedules/{id} |요청param|단건 응답 정보|200:정상조회|
| 선택한 일정 수정하기  |PUT    |schedules/{id} |요청body|수정정보|200:정상수정|
| 선택한 일정 삭제하기  |DELETE |schedules/{id} |요청param|-|200:정상삭제|



Member

|  기능                |Method |URL        |request |response|상태코드|
|------                 |-------|-----      |------- |------- |-------  |
| 회원가입              |POST   |members/signup   |요청body|등록정보|200:정상등록|
|로그인             |POST   |members/login   |요청body|등록정보|200:정상등록|
| 회원전체 조회         |GET    |members   |요청param|다건 응답정보|200:정상조회|
| 선택회원조회          |GET    |members/{members/id} |요청param|단건 응답 정보|200:정상조회|
| 비밀번호 수정하기  |PUT    |members/{members/id} |요청body|수정정보|200:정상수정|
| 회원 삭제하기  |DELETE |members/{members/id} |요청param|-|200:정상삭제|


Comment
|  기능                |Method |URL        |request |response|상태코드|
|------                 |-------|-----      |------- |------- |-------  |
| 댓글작성              |POST   |comments   |요청body|등록정보|200:정상등록|
| 댓글작성 조회         |GET    |comments   |요청param|다건 응답정보|200:정상조회|
| 선택댓글작성조회          |GET    |comments/{comments/id} |요청param|단건 응답 정보|200:정상조회|
| 선택한 댓글 수정하기  |PUT    |comments/{comments/id} |요청body|수정정보|200:정상수정|
| 선택한 댓글 삭제하기  |DELETE |comments/{comments/id} |요청param|-|200:정상삭제|

        
        
  - ERD 작성간에 다음과 같은 항목들을 학습합니다.
    - E(Entity. 개체)
        - 구현 할 서비스의 영역에서 필요로 하는 데이터를 담을 개체를 의미합니다.
            - ex) 할일,작성자명,비밀번호,작성/수정일
            - 작성 수정일은 날짜와 시간을 모두 포함한 형태.
    - A(Attribute. 속성)
        - 각 개체가 가지는 속성을 의미합니다.
            - ex) 일정은 `할일`, `작성자명`, `비밀번호`, `작성/수정일`등의 속성을 가질 수 있습니다.
    - R(Relationship. 관계)
        - 개체들 사이의 관계를 정의합니다.
            - ex) `작성자`는 여러 개의 `일정`을 등록할 수 있습니다. 이때, 작성자와 일정의 관계는 일대다(1:N) 관계입니다.      
