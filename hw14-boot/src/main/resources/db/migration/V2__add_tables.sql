create table address
(
    id   bigint not null primary key,
    street varchar(50)
);

alter table client add column address_id bigint
CONSTRAINT fk_address_id REFERENCES address(id);

create table phone
(
    id   bigint not null primary key,
    number varchar(50),
    client_id bigint REFERENCES client(id)
);
