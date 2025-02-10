use scheduledev;

-- user 테이블 생성
CREATE TABLE member(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    username VARCHAR(255) NOT NULL ,
    email VARCHAR(255) NOT NULL ,
    password VARCHAR(255) NOT NULL ,
    created_at TIMESTAMP NOT NULL ,
    modified_at TIMESTAMP NOT NULL
);

-- schedule 테이블 생성 (member_id 추가)
CREATE TABLE schedule(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    member_id BIGINT not null ,-- FK로 사용할 컬럼 추가
    username VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL ,
    contents VARCHAR(255) NOT NULL ,
    created_at TIMESTAMP NOT NULL ,
    modified_at TIMESTAMP NOT NULL ,
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES member(id)-- FK 설정
);

-- 테이블 삭제 시 주의: FK 제약이 있어 순서대로 삭제해야 함
DROP TABLE schedule;
DROP TABLE member;
