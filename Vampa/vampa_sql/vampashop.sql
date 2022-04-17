DROP TABLE BOOK_MEMBER CASCADE CONSTRAINTS;

DROP TABLE vam_author CASCADE CONSTRAINTS;
DROP TABLE vam_nation CASCADE CONSTRAINTS;
DROP TABLE vam_book CASCADE CONSTRAINTS;

------ [1-2] ������ ����(������ �������)
DROP SEQUENCE author_seq;

-- ȸ�� ���̺� �����
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

-- ���� ���̺� ����
create table vam_nation(
   nationId varchar2(2) primary key,
    nationName varchar2(50)
);
 
 
-- �۰� ���̺� ����
create table vam_author(
    authorId number primary key,
    authorName varchar2(50),
    nationId varchar2(2),
    authorIntro long,
    foreign key (nationId) references vam_nation(nationId)
);

-- ��ǰ ���̺� 
create table vam_book(
    bookId number primary key,
    bookName varchar2(50)   not null,
    authorId number, -- vam_author�� �ܷ�Ű
    publeYear Date not null,
    publisher varchar2(70) not null,
    cateCode varchar2(30), -- vam_bcate�� �ܷ�Ű
    bookPrice number not null,
    bookStock number not null, -- ���
    bookDiscount number(2,2), -- ������
    bookIntro clob,
    bookContents clob,
    regDate date default sysdate,
    updateDate date default sysdate
);

-- �ܷ�Ű�� : �ٸ� ���̺��� ���ڵ带 ����Ű�°�,
-- �ش� ���̺��� ���� ��ϵ� �� �ְ� �ϴ� ���̴�.

-- ī�װ� ���̺�
create table vam_bcate(
    tier number(1) not null, -- ī�װ� ���(1,2,3�ܰ�..)
    cateName varchar2(30) not null,
    cateCode varchar2(30) not null, -- PK
    cateParent varchar2(30) , -- ����ī�װ� : cateCode�� �ִ� ���� ��� �� �� �ְ� �ܷ�Ű
    -- ����ī�װ��� ������ ��� ���� ī�װ��� ���� ī�װ����� �� �� �ִ� ������.
    primary key(cateCode),
    foreign key(cateParent) references vam_bcate(cateCode) 
);

-- ������ �����
CREATE SEQUENCE author_seq;
CREATE SEQUENCE vam_book_seq;

-- member ������ �ֱ�
insert into book_member values('admin23', 'admin', 'admin', 'admin', 'admin', 'admin', 'admin', 1, sysdate, 1000000, 1000000);

-- ���� ���̺� ������ ����
insert into vam_nation values ('01', '����');
insert into vam_nation values ('02', '����');

-- �۰� ���̺� ����
insert into vam_author(authorId,authorName, nationId, authorIntro) values(author_seq.nextval,'��ȫ��', '01', '�۰� �Ұ��Դϴ�' );
insert into vam_author(authorId,authorName, nationId, authorIntro) values(author_seq.nextval,'�賭��', '01', '�۰� �Ұ��Դϴ�' );
insert into vam_author(authorId,authorName, nationId, authorIntro) values(author_seq.nextval,'��ũ��׸�', '02', '�۰� �Ұ��Դϴ�' );

-- ī�װ� ����
insert into vam_bcate(tier, cateName, cateCode) values (1, '����', '100000');
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '�Ҽ�', '101000','100000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�ѱ��Ҽ�', '101001','101000');
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '���̼Ҽ�', '101002','101000');
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�Ϻ��Ҽ�', '101003','101000');
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '��/������', '102000','100000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�ѱ���', '102001','102000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�ؿܽ�', '102002','102000');    
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '����/�濵', '103000','100000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�濵�Ϲ�', '103001','103000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�濵�̷�', '103002','103000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�����Ϲ�', '103003','103000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�����̷�', '103004','103000');    
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '�ڱ���', '104000','100000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '����/ó��', '104001','104000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�ڱ�ɷ°��', '104002','104000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�ΰ�����', '104003','104000');    
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '�ι�', '105000','100000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�ɸ���', '105001','105000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '������', '105002','105000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, 'ö��', '105003','105000');    
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '����/��ȭ', '106000','100000');
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�����Ϲ�', '106001','106000');
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�����', '106002','106000');
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�ѱ���', '106003','106000');
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '����', '107000','100000');
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�����̷�', '107001','107000');
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '����', '107002','107000');
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '������', '107003','107000');
insert into vam_bcate(tier, cateName, cateCode) values (1, '����', '200000');
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '����', '201000','200000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�Ҽ�', '201001','201000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '��', '201002','201000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '���', '201003','201000');    
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '�ι�/��ȸ', '202000','200000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '����', '202001','202000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, 'ö��', '202002','202000');    
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '����/�濵', '203000','200000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '������', '203001','203000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�濵��', '203002','203000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '����', '203003','203000');    
    insert into vam_bcate(tier, cateName, cateCode, cateParent) values (2, '����/���', '204000','200000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '�������', '204001','204000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '������', '204002','204000');    
        insert into vam_bcate(tier, cateName, cateCode, cateParent) values (3, '����', '204003','204000');  