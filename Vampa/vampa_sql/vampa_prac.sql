
select * from book_member;

select * from vam_author;

commit;

select * from vam_bcate;

select * from vam_book;

select * from vam_bcate order by catecode;

-- �۰���� ��ȸ ����


--������ �� �ø���
 insert into vam_author(authorid,authorName, nationId, authorIntro)
 (SELECT AUTHOR_SEQ.nextval,authorName,nationId,'����������' FROM vam_author);
-- �ε��� �׽�Ʈ
  SELECT /*+ INDEX_DESC(vam_author SYS_C0010168) */
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


-- �ε��� �� ã��
SELECT * FROM USER_INDEXES;
-- �ܷ�Ű �߰�
alter table vam_book add foreign key (authorId) references vam_author(authorId);
alter table vam_book add foreign key (cateCode) references vam_bcate(cateCode);

-- 19��,��ǰ ��� �����ϱ� 
-- ��ͺ���
insert into vam_book(bookId,bookName, authorId, publeYear, publisher, cateCode, bookPrice, bookStock, bookDiscount,bookIntro, bookContents)
(select vam_book_seq.nextval,bookName, authorId, publeYear, publisher, cateCode, bookPrice, bookStock, bookDiscount,bookIntro, bookContents from vam_book);

commit;

select * from USER_INDEXES WHERE TABLE_NAME = 'VAM_BOOK';
-- vam_book �ε��� �׽�Ʈ
  SELECT /*+ INDEX_DESC(vam_author SYS_C0010457) */
    *
    FROM vam_book
    WHERE bookid > 0
    ORDER BY bookid DESC;