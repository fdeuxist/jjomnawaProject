--===============================================================
/*
윈도우키+R
cmd 우클릭 관리자권한 실행
sqlplus /nolog
conn sys as sysdba;
create user c##jjom identified by nawa;
grant connect, resource, dba to c##jjom;
conn c##jjom/nawa;
show user;
exit
exit

sql developer 실행
접속 밑에 녹색 십자가 아이콘 클릭
Name : jjomnawa
사용자 이름 : c##jjom
비밀번호 : nawa
비밀번호 오른쪽에 저장 체크박스 체크
테스트 눌러서 상태에 성공 뜨는거 확인하고 저장
*/
--===============================================================

drop table categories;
create table categories (
    id number GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR2(255) NOT NULL unique,
    p_id number, -- 부모 카테고리를 참조
    depth number NOT NULL -- 대/중/소 카테고리 레벨
    --CONSTRAINT fk_parent FOREIGN KEY (p_id) REFERENCES categories(id) ON DELETE CASCADE
);


-- 대 카테고리
INSERT INTO categories (name, p_id, depth) VALUES ('컴퓨터부품', NULL, 1);   --카테고리명,부모id,대중소123
INSERT INTO categories (name, p_id, depth) VALUES ('가전제품', NULL, 1);

-- 중 카테고리
INSERT INTO categories (name, p_id, depth) 
    VALUES ('CPU', 
            (SELECT id FROM categories WHERE name = '컴퓨터부품'), 
            (SELECT depth + 1 FROM categories WHERE name = '컴퓨터부품'));
INSERT INTO categories (name, p_id, depth) VALUES ('스마트폰', (SELECT id FROM categories WHERE name = '전자제품'), 2);
INSERT INTO categories (name, p_id, depth) VALUES ('TV', (SELECT id FROM categories WHERE name = '가전제품'), 2);

-- 소 카테고리
INSERT INTO categories (name, p_id, depth) VALUES ('게이밍 노트북', (SELECT id FROM categories WHERE name = '노트북'), 3);
INSERT INTO categories (name, p_id, depth) VALUES ('비즈니스 노트북', (SELECT id FROM categories WHERE name = '노트북'), 3);
INSERT INTO categories (name, p_id, depth) VALUES ('안드로이드 스마트폰', (SELECT id FROM categories WHERE name = '스마트폰'), 3);
INSERT INTO categories (name, p_id, depth) VALUES ('아이폰', (SELECT id FROM categories WHERE name = '스마트폰'), 3);
INSERT INTO categories (name, p_id, depth) VALUES ('OLED TV', (SELECT id FROM categories WHERE name = 'TV'), 3);
INSERT INTO categories (name, p_id, depth) VALUES ('LCD TV', (SELECT id FROM categories WHERE name = 'TV'), 3);


select * from categories;

--대중소분류
select d.name, j.name, s.name, d.id, j.id, s.id, (d.id || '_' || j.id || '_' || s.id) AS 카테고리_id
    from categories d 
    join categories j on d.id = j.p_id
    join categories s on j.id = s.p_id;

--대분류 밑 중분류
select d.name, j.name, d.id, j.id, (d.id || '_' || j.id) AS 카테고리_id
    from categories d 
    join categories j on d.id = j.p_id
    where d.depth = 1;
    
--중분류 밑 소분류
select j.name, s.name, j.id, s.id, (j.id || '_' || s.id) AS 카테고리_id
    from categories d 
    join categories j on d.id = j.p_id
    join categories s on j.id = s.p_id
    where j.depth = 2;

select * from categories;
commit;

--어떤 카테고리 밑에 새 카테고리 삽입하기(스마트폰 선택, 테스트폰 입력)
--새 소분류 입력
INSERT INTO categories (name, p_id, depth) VALUES (
    '일반용', 
    (SELECT id FROM categories WHERE name = '세제류'), 
    (SELECT depth+1 FROM categories WHERE name = '세제류'));
    
--새 중분류 입력
INSERT INTO categories (name, p_id, depth) VALUES (
    '세제류', 
    (SELECT id FROM categories WHERE name = '주방용품'), 
    (SELECT depth+1 FROM categories WHERE name = '주방용품'));

--새 대분류 입력
INSERT INTO categories (name, p_id, depth) VALUES (
    '주방용품', null, 1);

    
commit;
select * from categories;
rollback;


--해당 카테고리의 모든 부모 소중대
SELECT id, name, p_id, depth
FROM categories
START WITH id = 21
CONNECT BY PRIOR p_id = id
ORDER BY depth DESC;

--해당 카테고리의 모든 부모 대중소
SELECT id, name, p_id, depth
FROM categories
START WITH id = 4
CONNECT BY PRIOR p_id = id
ORDER BY depth ASC;

select * from categories order by depth, id;

--대분류만
select id, name, p_id, depth
    from categories
    where p_id is null;

--어떤 중분류의 모든 소분류 (p_id에 중분류)
select id, name, p_id, depth 
    from categories 
    where p_id=17 and depth = 3;

--어떤 대분류의 모든 중분류 (p_id에 대분류)
select id, name, p_id, depth 
    from categories 
    where p_id=15 and depth = 2;

--어떤 분류의 모든 하위분류 (p_id, id 에 어떤 해당 분류)
select id, name, p_id, depth 
    from categories 
    where p_id=7 and depth =(SELECT depth + 1 FROM categories WHERE id=7);

select id, name, p_id, depth 
    from categories 
    where p_id=7;
    
--=========================================================================================================

--대분류 최초 등록
INSERT INTO categories (name, p_id, depth) VALUES ('컴퓨터부품', NULL, 1);   --카테고리명,부모id,대중소123

--하위(중,소 혼용가능)분류 등록
INSERT INTO categories (name, p_id, depth) 
    VALUES ('메모리',  --카테고리명
            (SELECT id FROM categories WHERE name = '컴퓨터부품'),   --부모id
            (SELECT depth + 1 FROM categories WHERE name = '컴퓨터부품'));   --depth (대중소 1 2 3)

--하위(중,소 혼용가능)분류 등록
INSERT INTO categories (name, p_id, depth) 
    VALUES ('PC(데스크탑)용',  --카테고리명
            (SELECT id FROM categories WHERE name = '메모리'),   --부모id
            (SELECT depth + 1 FROM categories WHERE name = '메모리'));   --depth (대중소 1 2 3)
            
select * from categories;
select name from categories;

--카테고리 관리페이지 등록메뉴에 보여줄것 대,중 분류만 셀렉 소분류 제외 셀렉 소분류에 소분류 등록은 안되기 때문
select * from categories where depth not in '3';



