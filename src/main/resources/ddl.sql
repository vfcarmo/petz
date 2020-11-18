create table tab_customer (
    id uuid not null,
    name varchar(255) not null,
    cpf varchar(255) not null,
    phone varchar(255) not null,
    email varchar(255) not null,
    status varchar(255) not null,
    dt_creation timestamp not null,
    dt_last_update timestamp not null,
    primary key (id));

create table tab_pet (
    id uuid not null,
    name varchar(255) not null,
    type varchar(255) not null,
    dt_birthdate timestamp not null,
    observation varchar(255) not null,
    customer_id uuid,
    status varchar(255) not null,
    dt_creation timestamp not null,
    dt_last_update timestamp not null,
    primary key (id));

alter table tab_pet add constraint FK7iawyi4u4awnx099qd0gikw8u foreign key (customer_id) references tab_customer;
create table tab_customer (id uuid not null, cpf varchar(255) not null, dt_creation timestamp not null, email varchar(255) not null, dt_last_update timestamp not null, name varchar(255) not null, phone varchar(255) not null, status varchar(255) not null, primary key (id))
create table tab_pet (id uuid not null, dt_birthdate timestamp not null, dt_creation timestamp not null, dt_last_update timestamp not null, name varchar(255) not null, observation varchar(255) not null, status varchar(255) not null, type varchar(255) not null, customer_id uuid, primary key (id))
alter table tab_pet add constraint FK7iawyi4u4awnx099qd0gikw8u foreign key (customer_id) references tab_customer
create table tab_customer (id uuid not null, cpf varchar(255) not null, dt_creation timestamp not null, email varchar(255) not null, dt_last_update timestamp not null, name varchar(255) not null, phone varchar(255) not null, status varchar(255) not null, primary key (id))
create table tab_pet (id uuid not null, dt_birthdate timestamp not null, dt_creation timestamp not null, dt_last_update timestamp not null, name varchar(255) not null, observation varchar(255) not null, status varchar(255) not null, type varchar(255) not null, customer_id uuid, primary key (id))
alter table tab_pet add constraint FK7iawyi4u4awnx099qd0gikw8u foreign key (customer_id) references tab_customer
create table tab_customer (id uuid not null, cpf varchar(255) not null, dt_creation timestamp not null, email varchar(255) not null, dt_last_update timestamp not null, name varchar(255) not null, phone varchar(255) not null, status varchar(255) not null, primary key (id))
create table tab_pet (id uuid not null, dt_birthdate timestamp not null, dt_creation timestamp not null, dt_last_update timestamp not null, name varchar(255) not null, observation varchar(255) not null, status varchar(255) not null, type varchar(255) not null, customer_id uuid, primary key (id))
alter table tab_pet add constraint FK7iawyi4u4awnx099qd0gikw8u foreign key (customer_id) references tab_customer
create table tab_customer (id uuid not null, cpf varchar(255) not null, dt_creation timestamp not null, email varchar(255) not null, dt_last_update timestamp not null, name varchar(255) not null, phone varchar(255) not null, status varchar(255) not null, primary key (id))
create table tab_pet (id uuid not null, dt_birthdate timestamp not null, dt_creation timestamp not null, dt_last_update timestamp not null, name varchar(255) not null, observation varchar(255) not null, status varchar(255) not null, type varchar(255) not null, customer_id uuid, primary key (id))
alter table tab_pet add constraint FK7iawyi4u4awnx099qd0gikw8u foreign key (customer_id) references tab_customer
create table tab_customer (id uuid not null, cpf varchar(255) not null, dt_creation timestamp not null, email varchar(255) not null, dt_last_update timestamp not null, name varchar(255) not null, phone varchar(255) not null, status varchar(255) not null, primary key (id))
create table tab_pet (id uuid not null, dt_birthdate timestamp not null, dt_creation timestamp not null, dt_last_update timestamp not null, name varchar(255) not null, observation varchar(255) not null, status varchar(255) not null, type varchar(255) not null, customer_id uuid, primary key (id))
alter table tab_pet add constraint FK7iawyi4u4awnx099qd0gikw8u foreign key (customer_id) references tab_customer
create table tab_customer (id uuid not null, cpf varchar(255) not null, dt_creation timestamp not null, email varchar(255) not null, dt_last_update timestamp not null, name varchar(255) not null, phone varchar(255) not null, status varchar(255) not null, primary key (id))
create table tab_pet (id uuid not null, dt_birthdate timestamp not null, dt_creation timestamp not null, dt_last_update timestamp not null, name varchar(255) not null, observation varchar(255) not null, status varchar(255) not null, type varchar(255) not null, customer_id uuid, primary key (id))
alter table tab_pet add constraint FK7iawyi4u4awnx099qd0gikw8u foreign key (customer_id) references tab_customer
create table tab_customer (id uuid not null, cpf varchar(255) not null, dt_creation timestamp not null, email varchar(255) not null, dt_last_update timestamp not null, name varchar(255) not null, phone varchar(255) not null, status varchar(255) not null, primary key (id))
create table tab_pet (id uuid not null, dt_birthdate timestamp not null, dt_creation timestamp not null, dt_last_update timestamp not null, name varchar(255) not null, observation varchar(255) not null, status varchar(255) not null, type varchar(255) not null, customer_id uuid, primary key (id))
alter table tab_pet add constraint FK7iawyi4u4awnx099qd0gikw8u foreign key (customer_id) references tab_customer
create table tab_customer (id uuid not null, cpf varchar(255) not null, dt_creation timestamp not null, email varchar(255) not null, dt_last_update timestamp not null, name varchar(255) not null, phone varchar(255) not null, status varchar(255) not null, primary key (id))
create table tab_pet (id uuid not null, dt_birthdate timestamp not null, dt_creation timestamp not null, dt_last_update timestamp not null, name varchar(255) not null, observation varchar(255) not null, status varchar(255) not null, type varchar(255) not null, customer_id uuid, primary key (id))
alter table tab_pet add constraint FK7iawyi4u4awnx099qd0gikw8u foreign key (customer_id) references tab_customer
create table tab_customer (id uuid not null, cpf varchar(255) not null, dt_creation timestamp not null, email varchar(255) not null, dt_last_update timestamp not null, name varchar(255) not null, phone varchar(255) not null, status varchar(255) not null, primary key (id))
create table tab_pet (id uuid not null, dt_birthdate timestamp not null, dt_creation timestamp not null, dt_last_update timestamp not null, name varchar(255) not null, observation varchar(255), status varchar(255) not null, type varchar(255) not null, customer_id uuid, primary key (id))
alter table tab_pet add constraint FK7iawyi4u4awnx099qd0gikw8u foreign key (customer_id) references tab_customer
create table tab_customer (id uuid not null, cpf varchar(255) not null, dt_creation timestamp not null, email varchar(255) not null, dt_last_update timestamp not null, name varchar(255) not null, phone varchar(255) not null, status varchar(255) not null, primary key (id))
create table tab_pet (id uuid not null, dt_birthdate timestamp not null, dt_creation timestamp not null, dt_last_update timestamp not null, name varchar(255) not null, observation varchar(255), status varchar(255) not null, type varchar(255) not null, customer_id uuid, primary key (id))
alter table tab_pet add constraint FK7iawyi4u4awnx099qd0gikw8u foreign key (customer_id) references tab_customer
