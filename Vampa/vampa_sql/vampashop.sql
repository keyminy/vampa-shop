DROP TABLE BOOK_MEMBER CASCADE CONSTRAINTS;

DROP TABLE vam_author CASCADE CONSTRAINTS;
DROP TABLE vam_nation CASCADE CONSTRAINTS;
DROP TABLE vam_book CASCADE CONSTRAINTS;

------ [1-2] 시퀀스 제거(순서와 상관없음)
DROP SEQUENCE author_seq;

-- 회원 테이블 만들기
CREATE TABLE BOOK_MEMBER(
  memberId VARCHAR2(50),
  memberPw VARCHAR2(100) NOT NULL,
  memberName VARCHAR2(30) NOT NULL,
  memberMail VARCHAR2(100) NOT NULL,
  memberAddr1 VARCHAR2(100) NOT NULL,
  memberAddr2 VARCHAR2(100) NOT NULL,
  memberAddr3 VARCHAR2(100) NOT NULL,
  adminCk NUMBER NOT NULL,
  regDate DATE NOT NULL,
  money number NOT NULL,
  point number NOT NULL,
  PRIMARY KEY(memberId)
);

-- 국가 테이블 생성
create table vam_nation(
   nationId varchar2(2) primary key,
    nationName varchar2(50)
);
 
 
-- 작가 테이블 생성
create table vam_author(
    authorId number primary key,
    authorName varchar2(50),
    nationId varchar2(2),
    authorIntro long,
    foreign key (nationId) references vam_nation(nationId),
    regdate date default sysdate,
    updateDate date default sysdate
);

-- 상품 테이블 
create table vam_book(
    bookId number primary key,
    bookName varchar2(50)   not null,
    authorId number, -- vam_author에 외래키
    publeYear Date not null,
    publisher varchar2(70) not null,
    cateCode varchar2(30), -- vam_bcate에 외래키
    bookPrice number not null,
    bookStock number not null, -- 재고
    bookDiscount number(2,2), -- 할인율
    bookIntro clob,
    bookContents clob,
    regDate date default sysdate,
    updateDate date default sysdate
);

-- 외래키는 : 다른 테이블의 레코드를 가리키는것,
-- 해당 테이블의 값만 등록될 수 있게 하는 것이다.

-- 카테고리 테이블
create table vam_bcate(
    tier number(1) not null, -- 카테고리 등급(1,2,3단계..)
    cateName varchar2(30) not null,
    cateCode varchar2(30) not null, -- PK
    cateParent varchar2(30) , -- 상위카테고리 : cateCode에 있는 값만 들어 갈 수 있게 외래키
    -- 상위카테고리의 역할은 어떠한 상위 카테고리의 하위 카테고리인지 알 수 있는 역할임.
    primary key(cateCode),
    foreign key(cateParent) references vam_bcate(cateCode) 
);

-- 시퀀스 만들기
CREATE SEQUENCE author_seq;
CREATE SEQUENCE vam_book_seq;

-- member 관리자 넣기
insert into book_member values('admin23', 'admin', 'admin', 'admin', 'admin', 'admin', 'admin', 1, sysdate, 1000000, 1000000);

-- 국가 테이블 데이터 삽입
insert into vam_nation values ('01', '국내');
insert into vam_nation values ('02', '국외');

-- 작가 테이블 삽입
insert into vam_author(authorId,authorName, nationId, authorIntro) values(author_seq.nextval,'유홍준', '01', '작가 소개입니다' );
insert into vam_author(authorId,authorName, nationId, authorIntro) values(author_seq.nextval,'김난도', '01', '작가 소개입니다' );
insert into vam_author(authorId,authorName, nationId, authorIntro) values(author_seq.nextval,'폴크루그먼', '02', '작가 소개입니다' );

-- 카테고리 삽입
insert into vam_bcate(tier, cateName, cateCode) values (1, '국내', '100000');
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '소설', '101000','100000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '한국소설', '101001','101000');
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '영미소설', '101002','101000');
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '일본소설', '101003','101000');
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '시/에세이', '102000','100000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '한국시', '102001','102000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '해외시', '102002','102000');    
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '경제/경영', '103000','100000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '경영일반', '103001','103000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '경영이론', '103002','103000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '경제일반', '103003','103000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '경제이론', '103004','103000');    
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '자기계발', '104000','100000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '성공/처세', '104001','104000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '자기능력계발', '104002','104000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '인간관계', '104003','104000');    
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '인문', '105000','100000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '심리학', '105001','105000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '교육학', '105002','105000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '철학', '105003','105000');    
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '역사/문화', '106000','100000');
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '역사일반', '106001','106000');
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '세계사', '106002','106000');
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '한국사', '106003','106000');
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '과학', '107000','100000');
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '과학이론', '107001','107000');
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '수학', '107002','107000');
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '물리학', '107003','107000');
insert into vam_bcate(tier, cateName, cateCode) values (1, '국외', '200000');
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '문학', '201000','200000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '소설', '201001','201000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '시', '201002','201000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '희곡', '201003','201000');    
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '인문/사회', '202000','200000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '교양', '202001','202000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '철학', '202002','202000');    
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '경제/경영', '203000','200000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '경제학', '203001','203000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '경영학', '203002','203000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '투자', '203003','203000');    
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '과학/기술', '204000','200000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '교양과학', '204001','204000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '물리학', '204002','204000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '수학', '204003','204000');  