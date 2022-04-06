create table supply_center
(
    supply_center varchar
        primary key
);


create table client_enterprise
(
    client_enterprise varchar
        primary key,
    supply_center     varchar
        not null unique references supply_center (supply_center),
    country           varchar
        not null unique,
    city              varchar,
    industry          varchar
);


create table contract
(
    contract_number   varchar
        primary key,
    contract_date     varchar
                              not null unique,
    director          varchar,
    client_enterprise varchar
        references client_enterprise (client_enterprise),
    order_number      integer not null
);


create table salesman
(
    salesman_number varchar
        primary key,
    salesman_name   varchar
        not null unique,
    gender          varchar
        not null unique,
    supply_center   varchar
        not null unique references supply_center (supply_center),
    age             integer
        not null unique,
    mobile_phone    varchar
        not null unique
);


create table product
(
    product_code varchar
        primary key,
    product_name varchar
        not null unique
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


create table order_table
(
    order_number    serial primary key,
    product_code    varchar
        not null unique references product (product_code),
    product_model   varchar
        not null unique references model (product_model),
    quantity        integer
        not null unique,
        contract_number varchar not null unique references contract(contract_number),
    salesman_number varchar
        not null unique references salesman (salesman_number),
    estimated_date  varchar,
    lodgement_date  varchar
)
