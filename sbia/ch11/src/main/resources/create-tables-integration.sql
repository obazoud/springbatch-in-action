drop table if exists product_import;

create table product_import
(
  import_id character(50) not null,
  creation_date date,
  constraint product_import_pkey primary key (import_id)
);