-- Создание таблицы товаров
create table product (
    id serial unique not null,
    name char(50) not null,
    cost integer check(cost > -1)
);

-- Добавление товаров
insert into product (name, cost) values ('milk', 129);
insert into product (name, cost) values ('bread', 59);
insert into product (name, cost) values ('chocolate', 119);

-- Вывод всех товаров
select * from product;

-- Результат запроса:
-- id | name      | cost
-- 1  | milk      | 129
-- 2  | bread     | 59
-- 3  | chocolate | 119
