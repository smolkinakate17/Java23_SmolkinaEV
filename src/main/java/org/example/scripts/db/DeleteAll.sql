TRUNCATE TABLE public.payment_method CASCADE;
TRUNCATE TABLE public.category CASCADE;
TRUNCATE TABLE public.payment CASCADE;
TRUNCATE TABLE public.payment_category CASCADE;
ALTER SEQUENCE public.category_id_seq RESTART WITH 1;
ALTER SEQUENCE public.payment_id_seq RESTART WITH 1;
ALTER SEQUENCE public.payment_method_id_seq RESTART WITH 1;
