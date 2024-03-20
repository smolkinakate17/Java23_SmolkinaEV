CREATE SCHEMA IF NOT EXISTS public;
DROP TABLE IF EXISTS public.payment_category;
DROP TABLE IF EXISTS public.category;
DROP TABLE IF EXISTS public.payment;
DROP TABLE IF EXISTS public.payment_method;

CREATE TABLE IF NOT EXISTS public.payment_method
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    title character varying NOT NULL UNIQUE
);

ALTER TABLE IF EXISTS public.payment_method
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.category(
	id BIGSERIAL NOT NULL PRIMARY KEY,
	title character varying NOT NULL UNIQUE,
	description character varying,
	color character varying NOT NULL UNIQUE
);
ALTER TABLE IF EXISTS public.category
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.payment(
	id BIGSERIAL NOT NULL PRIMARY KEY,
	payment_date_time timestamp without time zone NOT NULL,
    supplier character varying NOT NULL,
	amount decimal(10,2) NOT NULL,
	payment_method bigint,
	foreign key (payment_method) references public.payment_method(id) on delete restrict
);
ALTER TABLE IF EXISTS public.payment
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.payment_category(
	payment_id bigint NOT NULL,
	category_ig bigint NOT NULL,
	CONSTRAINT payment_category_pkey PRIMARY KEY (payment_id, category_id),
	foreign key (payment_id) references public.payment(id) on delete cascade,
	foreign key (category_ig) references public.category(id) on delete cascade
);
ALTER TABLE IF EXISTS public.payment_category
    OWNER to postgres;