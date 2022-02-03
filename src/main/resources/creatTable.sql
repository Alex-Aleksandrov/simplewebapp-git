CREATE TABLE IF NOT EXISTS public.employee
(
    employee_id integer NOT NULL DEFAULT,
    first_name text,
    last_name text,
    department_id integer,
    job_title text,
    gender text,
    date_of_birth date,
    CONSTRAINT employee_pkey PRIMARY KEY (employee_id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.employee
    OWNER to postgres;