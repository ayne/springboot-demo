--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = pg_catalog;

--
-- Name: CAST (text AS json); Type: CAST; Schema: pg_catalog; Owner: 
--

CREATE CAST (text AS json) WITHOUT FUNCTION AS IMPLICIT;


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: orders; Type: TABLE; Schema: public; Owner: usr; Tablespace: 
--

CREATE TABLE orders (
    id integer NOT NULL,
    user_id integer,
    details text
);


ALTER TABLE orders OWNER TO usr;

--
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: usr
--

CREATE SEQUENCE orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE orders_id_seq OWNER TO usr;

--
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: usr
--

ALTER SEQUENCE orders_id_seq OWNED BY orders.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: usr; Tablespace: 
--

CREATE TABLE users (
    id integer NOT NULL,
    email text,
    password text,
    name text,
    gender text
);


ALTER TABLE users OWNER TO usr;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: usr
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO usr;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: usr
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: usr
--

ALTER TABLE ONLY orders ALTER COLUMN id SET DEFAULT nextval('orders_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: usr
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: usr
--

COPY orders (id, user_id, details) FROM stdin;
\.


--
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: usr
--

SELECT pg_catalog.setval('orders_id_seq', 1, false);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: usr
--

COPY users (id, email, password, name, gender) FROM stdin;
1	charmane.santiago@gmail.com	$2a$10$MhHHh83fvCMEvqr0H5Ad1.ttIUjaoA6NNqGzQBpkASdMELiL71ULO	\N	\N
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: usr
--

SELECT pg_catalog.setval('users_id_seq', 1, false);


--
-- Name: orders_pkey; Type: CONSTRAINT; Schema: public; Owner: usr; Tablespace: 
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: usr; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: usr
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: charmanesantiago
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM charmanesantiago;
GRANT ALL ON SCHEMA public TO charmanesantiago;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

