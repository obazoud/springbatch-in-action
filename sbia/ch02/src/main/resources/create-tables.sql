drop table if exists invoice;

create table invoice
(
  id character(9) not null,
  customer_id integer not null,
  description character varying(50),
  issue_date date,
  amount float,
  constraint invoice_pkey primary key (id)
);