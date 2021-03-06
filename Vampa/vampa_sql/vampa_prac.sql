select * from vam_author
where authorName='김난도';

commit;

select * from vam_bcate;

select * from vam_image
order by bookid desc;

select * from vam_bcate order by catecode;

-- 작가목록 조회 쿼리


--데이터 수 늘리기
 insert into vam_author(authorid,authorName, nationId, authorIntro)
 (SELECT AUTHOR_SEQ.nextval,authorName,nationId,'ㅎㅎㅇㅎㅇ' FROM vam_author);
-- 인덱스 테스트
  SELECT /*+ INDEX_DESC(vam_author SYS_C0010457) */
    *
    FROM vam_author
    WHERE authorid > 0
    ORDER BY authorid DESC;

select * from vam_author;
   SELECT /*+FULL(vam_author)  */
    *
    FROM vam_author
    WHERE authorid > 0
    ORDER BY authorid DESC;


-- 인덱스 명 찾기
SELECT * FROM USER_INDEXES WHERE TABLE_NAME = 'VAM_AUTHOR';
-- 외래키 추가
alter table vam_book add foreign key (authorId) references vam_author(authorId);
alter table vam_book add foreign key (cateCode) references vam_bcate(cateCode);

-- 19강,상품 목록 구현하기 
-- 재귀복사
insert into vam_book(bookId,bookName, authorId, publeYear, publisher, cateCode, bookPrice, bookStock, bookDiscount,bookIntro, bookContents)
(select vam_book_seq.nextval,bookName, authorId, publeYear, publisher, cateCode, bookPrice, bookStock, bookDiscount,bookIntro, bookContents from vam_book);

commit;

select * from USER_INDEXES WHERE TABLE_NAME = 'VAM_BOOK';
-- vam_book 인덱스 테스트
  SELECT /*+ INDEX_DESC(vam_author SYS_C0010457) */
    *
    FROM vam_book
    WHERE bookid > 0
    ORDER BY bookid DESC;

-- [27-3]업로드 이미지 등록
select vam_book_seq.currval
from dual;

select a.authorId,a.authorName
from vam_book b,vam_author a
where b.authorid=a.authorid
and a.authorName='김난도';

SELECT DISTINCT /*+ INDEX_DESC(vam_author SYS_C0010168) */ b.authorid
FROM vam_author a,vam_book b
WHERE b.authorid=a.authorid 
AND authorName LIKE '%' || '' || '%';

select *
from vam_author
where authorName LIKE '%' || '크' || '%';

select * from vam_book
order by bookid desc;

-- 상품 상세보기
SELECT b.bookId,b.bookName,b.authorId,a.authorName,b.publeYear,b.pubLisher,b.cateCode,
c.cateName,b.bookPrice,b.bookStock,b.bookDiscount,b.bookIntro,b.bookContents
FROM vam_book b LEFT OUTER JOIN vam_author a 
ON b.authorId = a.authorId
LEFT OUTER JOIN vam_bcate c 
ON b.cateCode = c.cateCode
WHERE bookId = 16781;
