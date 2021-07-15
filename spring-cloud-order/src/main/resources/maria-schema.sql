create table `order` (
    id bigint auto_increment primary key,
    created_date timestamp,
    modified_date timestamp,
    order_id varchar(255) not null,
    product_id varchar(255),
    qty integer,
    total_price int,
    unit_price int,
    user_id varchar(255)
);