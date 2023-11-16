INSERT INTO member_table (userId, userPass, userName, userauthority)
VALUES
    ('111', '222', 'User 1', 'USER'),
    ('333', '444', 'Admin', 'ADMIN');

-- Company 테이블에 데이터를 추가하는 SQL 쿼리
INSERT INTO Company (companyName, companyVip, companyTel, status,money,postcode,roadAddress,detailAddress)
VALUES ('김회사', '김사장', '053-1111-1111', 'receive', 0 , '42829','대구 달서구 화암로 342','관리사무소');

INSERT INTO Company (companyName, companyVip, companyTel, status,  money,postcode,roadAddress,detailAddress)
VALUES ('주회사', '주사장', '053-2222-2222', 'send', 0 ,'42672','대구 달서구 공원순환로 36','관리사무소');

INSERT INTO Company (companyName, companyVip, companyTel, status,  money,postcode,roadAddress,detailAddress)
VALUES ('상회사', '상사장', '02-3334-3333', 'send',0 ,'61187','광주 북구 우치로 77','2층');

INSERT INTO Company (companyName, companyVip, companyTel, status,  money,postcode,roadAddress,detailAddress)
VALUES ('박회사', '박사장', '02-4444-4444', 'send',0 '44701','울산 남구 돋질로 233','3층');

INSERT INTO Product (productName, productCode, price, makeDay, company_id)
VALUES
    ('밀빵', '김회사1', 1000, '2023-02-01', 1),
    ('식빵', '김회사2', 155, '2023-02-01', 1),
    ('밀죽', '주회사3', 513, '2023-02-01', 2),
    ('고기죽', '주회사4', 1234123, '2023-04-01', 2),
    ('밀밥', '상회사5', 24124, '2023-08-01', 3),
    ('쌀밥', '상회사6', 213123, '2023-04-01', 3);