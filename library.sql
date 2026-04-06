CREATE DATABASE IF NOT EXISTS library;
USE library;

-- 도서 테이블
CREATE TABLE IF NOT EXISTS books (
    id               INT           PRIMARY KEY AUTO_INCREMENT,
    title            VARCHAR(255)  NOT NULL,
    author           VARCHAR(255)  NOT NULL,
    publisher        VARCHAR(255),
    publication_year INT,
    isbn             VARCHAR(13),
    available        BOOLEAN       DEFAULT TRUE
);

-- 학생 테이블
CREATE TABLE IF NOT EXISTS students (
    id         INT          PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(100) NOT NULL,
    student_id VARCHAR(20)  NOT NULL UNIQUE
);

-- 대출 테이블
CREATE TABLE IF NOT EXISTS borrows (
    id          INT  PRIMARY KEY AUTO_INCREMENT,
    book_id     INT,
    student_id  INT,
    borrow_date DATE NOT NULL,
    return_date DATE,
    FOREIGN KEY (book_id)    REFERENCES books(id),
    FOREIGN KEY (student_id) REFERENCES students(id)
);


-- 샘플 데이터
INSERT INTO books (title, author, publisher, publication_year, isbn, available) VALUES
('자바 프로그래밍 입문', '김영훈', '한빛미디어', 2023, '9788968481234', TRUE),
('데이터베이스 기초',   '이수진', '길벗',       2022, '9788968485678', TRUE),
('알고리즘 문제 해결', '박민수', '인사이트',    2021, '9788968489012', FALSE),
('웹 개발 입문',       '최지영', '한빛아카데미', 2024, '9788968483456', TRUE),
('소프트웨어 공학',    '정현우', '생능출판사',   2020, '9788970507890', FALSE);

INSERT INTO students (name, student_id) VALUES
('홍길동', '20230001'),
('김민서', '20230002'),
('이준호', '20230003');

INSERT INTO borrows (book_id, student_id, borrow_date, return_date) VALUES
(3, 1, '2025-05-01', NULL),  -- 홍길동 → 알고리즘 문제 해결 대출 중
(5, 2, '2025-05-03', NULL);  -- 김민서 → 소프트웨어 공학 대출 중