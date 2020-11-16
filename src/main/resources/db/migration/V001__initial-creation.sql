create table tab_customer (
    id binary(255) not null,
    cpf varchar(11) not null,
    name varchar(150) not null,
    email varchar(150) not null,
    phone varchar(11) not null,
    status varchar(10) not null,
    dt_creation datetime(6) not null,
    dt_last_update datetime(6) not null,
    primary key (id)
) engine=InnoDB;

create table tab_pet (
    id binary(255) not null,
    name varchar(150) not null,
    type varchar(10) not null,
    observation varchar(255) not null,
    dt_birthdate datetime(6) not null,
    customer_id binary(255),
    status varchar(255) not null,
    dt_creation datetime(6) not null,
    dt_last_update datetime(6) not null,
    primary key (id)
) engine=InnoDB;

alter table tab_pet add constraint FK_owner foreign key (customer_id) references tab_customer (id);
