create table client
(
    client_enterprise varchar
        primary key,
    supply_center     varchar
        not null unique,
    country           varchar
        not null unique,
    city              varchar,
    industry          varchar
);


create table contract
(
    contract_number    varchar
        primary key,
    contract_date      varchar
        not null unique,
    estimated_date     varchar
        not null unique,
    lodgement_date     varchar,
    director   varchar,
    client_enterprise  varchar
        references client (client_enterprise)
);


--这里要先建立salesman和order表格，因为model和contract_model都需要设置外键
create table salesman
(
    salesman_number integer
        primary key,
    salesman_name   varchar
        not null unique,
    gender          varchar
        not null unique,
    age             integer
        not null unique,
    mobile_phone    varchar
        not null unique
);


create table product--order用不了
(
    product_code varchar
        primary key,
    product_name varchar
        not null unique
);


create table product_salesman
(
    product_code    varchar
        references product (product_code),
    salesman_number integer
        references salesman (salesman_number),
    primary key (product_code, salesman_number)
);


create table model
(
    product_model varchar
        primary key,
    unit_price    integer
        not null unique,
    product_code  varchar
        references product (product_code)
);


create table contract_model
(
    contract_number varchar
        references contract (contract_number),
    product_model   varchar
        references model (product_model),
    quantity        integer
        not null unique,
    primary key (contract_number, product_model)
);
C:\Users\86139\AppData\Roaming\JetBrains\DataGrip2021.3\consoles\db\f5501d61-76ef-4191-be55-5ade9d28234b\lab7\exercise7.sql