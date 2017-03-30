--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.4
-- Dumped by pg_dump version 9.5.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: directors; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE directors (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE directors OWNER TO "Guest";

--
-- Name: directors_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE directors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE directors_id_seq OWNER TO "Guest";

--
-- Name: directors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE directors_id_seq OWNED BY directors.id;


--
-- Name: movies; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE movies (
    id integer NOT NULL,
    title character varying,
    directorid integer,
    description text,
    genre character varying,
    releasedate character varying
);


ALTER TABLE movies OWNER TO "Guest";

--
-- Name: movies_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE movies_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE movies_id_seq OWNER TO "Guest";

--
-- Name: movies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE movies_id_seq OWNED BY movies.id;


--
-- Name: reviews; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE reviews (
    id integer NOT NULL,
    movieid integer,
    rating integer,
    review text
);


ALTER TABLE reviews OWNER TO "Guest";

--
-- Name: reviews_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE reviews_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reviews_id_seq OWNER TO "Guest";

--
-- Name: reviews_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE reviews_id_seq OWNED BY reviews.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY directors ALTER COLUMN id SET DEFAULT nextval('directors_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY movies ALTER COLUMN id SET DEFAULT nextval('movies_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY reviews ALTER COLUMN id SET DEFAULT nextval('reviews_id_seq'::regclass);


--
-- Data for Name: directors; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY directors (id, name) FROM stdin;
2	Wes Anderson
5	Quinten Tarentino
6	Ben Affleck
7	Coen Brothers
8	JJ Abrams
9	Sofia Coppola
10	Spike Jonze
11	Alejandro González Iñárritu
12	Morten Tyldum
\.


--
-- Name: directors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('directors_id_seq', 12, true);


--
-- Data for Name: movies; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY movies (id, title, directorid, description, genre, releasedate) FROM stdin;
4	Fargo	7	Jerry works in his father-in-law's car dealership and has gotten himself in financial problems. He tries various schemes to come up with money needed for a reason that is never really explained. It has to be assumed that his huge embezzlement of money from the dealership is about to be discovered by father-in-law. When all else falls through, plans he set in motion earlier for two men to kidnap his wife for ransom to be paid by her wealthy father (who doesn't seem to have the time of day for son-in-law). From the moment of the kidnapping, things go wrong and what was supposed to be a non-violent affair turns bloody with more blood added by the minute. Jerry is upset at the bloodshed, which turns loose a pregnant sheriff from Brainerd, MN who is tenacious in attempting to solve the three murders in her jurisdiction.	Crime	1996-04-05
6	The Big Lebowski	7	"The Dude" Lebowski, mistaken for a millionaire Lebowski, seeks restitution for his ruined rug and enlists his bowling buddies to help get it.	Comedy	1998-03-06
5	Argo	6	Acting under the cover of a Hollywood producer scouting a location for a science fiction film, a CIA agent launches a dangerous operation to rescue six Americans in Tehran during the U.S. hostage crisis in Iran in 1980.	Biography	2012-10-12
8	Star Wars: The Force Awakens	8	Three decades after the defeat of the Galactic Empire, a new threat arises. The First Order attempts to rule the galaxy and only a ragtag group of heroes can stop them, along with the help of the Resistance.	Action	2015-12-18
9	The Royal Tenenbaums	2	An estranged family of former child prodigies reunites when their father announces he is terminally ill.	Comedy	2002-01-04
10	Rushmore	2	The extracurricular king of Rushmore preparatory school is put on academic probation.	Comedy	1999-02-19
11	Lost in Translation	9	A faded movie star and a neglected young woman form an unlikely bond after crossing paths in Tokyo.	Drama	2003-10-03
12	Her	10	A lonely writer develops an unlikely relationship with an operating system designed to meet his every need.	Drama	2014-01-10
13	Birdman	11	Illustrated upon the progress of his latest Broadway play, a former popular actor's struggle to cope with his current life as a wasted actor is shown.	Comedy	2014-11-14
14	The Imitation Game	12	During World War II, mathematician Alan Turing tries to crack the enigma code with help from fellow mathematicians.	Biography	2014-12-25
15	Pulp Fiction	5	The lives of two mob hit men, a boxer, a gangster's wife, and a pair of diner bandits intertwine in four tales of violence and redemption.	Crime	1994-10-14
\.


--
-- Name: movies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('movies_id_seq', 15, true);


--
-- Data for Name: reviews; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY reviews (id, movieid, rating, review) FROM stdin;
10	4	9	BEST MOVIE EVER
12	4	6	wkjqwtw\r\n
13	4	7	ok\r\n
14	4	5	hey not good
15	5	8	movie was great
17	5	4	not good
18	5	8	great
19	6	8	good
20	6	9	really good
9	4	9	Best ever ever
23	4	8	good
25	5	4	ok
27	6	2	really bad
28	6	7	ok\r\n
8	4	9	Best ever ever
11	4	3	lkhjsfv
30	6	10	10/10 would watch again
26	6	10	hated it. but im an idiot so ...
31	14	9	great movie love the math 
\.


--
-- Name: reviews_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('reviews_id_seq', 31, true);


--
-- Name: directors_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY directors
    ADD CONSTRAINT directors_pkey PRIMARY KEY (id);


--
-- Name: movies_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY movies
    ADD CONSTRAINT movies_pkey PRIMARY KEY (id);


--
-- Name: reviews_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY reviews
    ADD CONSTRAINT reviews_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

