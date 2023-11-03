INSERT INTO member_table (userId, userPass, userName, userauthority)
VALUES
    ('111', '222', 'User 1', 'USER'),
    ('333', '444', 'Admin', 'ADMIN');


-- Company 테이블에 데이터를 추가하는 SQL 쿼리
INSERT INTO Company (companyName, companyVip, companyTel, status, moneyRecieve, money)
VALUES ('김회사', 'ㅈㅈ', '333', 'receive', 1, 0);

INSERT INTO Company (companyName, companyVip, companyTel, status, moneyRecieve, money)
VALUES ('주회사', 'ㄱ22', '2222', 'send', 1, 0);

INSERT INTO Company (companyName, companyVip, companyTel, status, moneyRecieve, money)
VALUES ('상회사', 'ㄷㄷㄷ', '23', 'send', 1, 0);


INSERT INTO Product (productName, productCode, stockAmount, productPrice, manufacturer, manufacturingYearMonth, companyId)
VALUES
    ('밀빵', '김회사1', 1, 1000, '김회사', '2023-02-01', 1),
    ('식빵', '김회사2', 56, 155, '김회사', '2023-02-01', 1),
    ('밀죽', '주회사3', 14, 513, '주회사', '2023-02-01', 2),
    ('고기죽', '주회사4', 515124, 1234123, '주회사', '2023-04-01', 2),
    ('밀밥', '상회사5', 513213, 24124, '상회사', '2023-08-01', 3),
    ('쌀밥', '상회사6', 51, 213123, '상회사', '2023-04-01', 3);