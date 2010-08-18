drop table if exists staging_product;

create table staging_product
(
  id character(9) not null,
  name character varying(50),
  description character varying(255),
  price float,
  processed boolean,
  constraint staging_product_pkey primary key (id)
);