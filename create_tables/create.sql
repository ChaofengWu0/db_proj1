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
        not null references supply_center (supply_center),
    country           varchar
        not null,
    city              varchar,
    industry          varchar
);


create table contract
(
    contract_number   varchar
        primary key,
    contract_date     varchar
                              not null,
    director          varchar,
    client_enterprise varchar
        references client_enterprise (client_enterprise)
);


create table salesman
(
    salesman_number varchar
        primary key,
    salesman_name   varchar
        not null,
    gender          varchar
        not null,
    supply_center   varchar
        not null references supply_center (supply_center),
    age             integer
        not null,
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
        not null,
    product_code  varchar
        references product (product_code)
);


create table order_table
(
    order_number    serial primary key,
    product_code    varchar
                            not null references product (product_code),
    product_model   varchar
                            not null references model (product_model),
    quantity        integer
                            not null,
    contract_number varchar not null references contract (contract_number),
    salesman_number varchar
                            not null references salesman (salesman_number),
    estimated_date  varchar,
    lodgement_date  varchar
)
