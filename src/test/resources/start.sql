INSERT INTO member_table (userId, userPass, userName, userauthority)
VALUES
    ('111', '222', 'User 1', 'USER'),
    ('333', '444', 'Admin', 'ADMIN');

-- Company 테이블에 데이터를 추가하는 SQL 쿼리
INSERT INTO Company (companyName, companyVip, companyTel, status, moneyRecieve, money)
VALUES ('김회사', '김사장', '333', 'receive', 1, 0);

INSERT INTO Company (companyName, companyVip, companyTel, status, moneyRecieve, money)
VALUES ('주회사', '주사장', '2222', 'send', 1, 0);

INSERT INTO Company (companyName, companyVip, companyTel, status, moneyRecieve, money)
VALUES ('상회사', '상사장', '23', 'send', 1, 0);

INSERT INTO Product (productName, productCode, price, makeDay, company_id)
VALUES
    ('밀빵', '김회사1', 1000, '2023-02-01', 1),
    ('식빵', '김회사2', 155, '2023-02-01', 1),
    ('밀죽', '주회사3', 513, '2023-02-01', 2),
    ('고기죽', '주회사4', 1234123, '2023-04-01', 2),
    ('밀밥', '상회사5', 24124, '2023-08-01', 3),
    ('쌀밥', '상회사6', 213123, '2023-04-01', 3);