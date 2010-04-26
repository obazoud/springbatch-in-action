CREATE TABLE invoice
(
  id character(9) NOT NULL,
  customer_id integer NOT NULL,
  description character varying(50),
  issue_date date,
  amount float,
  CONSTRAINT invoice_pkey PRIMARY KEY (id)
);