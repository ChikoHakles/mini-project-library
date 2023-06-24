--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2023-06-25 02:45:10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE library;
--
-- TOC entry 3363 (class 1262 OID 16499)
-- Name: library; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE library WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_Indonesia.1252';


ALTER DATABASE library OWNER TO postgres;

\connect library

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 16524)
-- Name: mst_books; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mst_books (
    book_id integer NOT NULL,
    book_author character varying(255),
    book_copy integer NOT NULL,
    book_description character varying(255),
    book_isbn character varying(17),
    book_max_day_borrowed integer,
    book_number_of_reading integer,
    book_pages integer NOT NULL,
    book_publisher character varying(255),
    book_ready integer,
    book_title character varying(255),
    book_unreturned integer,
    book_wished_by integer,
    category_id character varying(255),
    CONSTRAINT mst_books_book_copy_check CHECK ((book_copy >= 1)),
    CONSTRAINT mst_books_book_pages_check CHECK ((book_pages >= 1))
);


ALTER TABLE public.mst_books OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16523)
-- Name: mst_books_book_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.mst_books ALTER COLUMN book_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.mst_books_book_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 215 (class 1259 OID 16516)
-- Name: mst_readers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mst_readers (
    reader_id integer NOT NULL,
    reader_address character varying(255),
    reader_email character varying(255),
    reader_name character varying(255),
    reader_phone character varying(255)
);


ALTER TABLE public.mst_readers OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16515)
-- Name: mst_readers_reader_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.mst_readers ALTER COLUMN reader_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.mst_readers_reader_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 221 (class 1259 OID 16576)
-- Name: mst_return; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mst_return (
    return_id uuid NOT NULL,
    return_code character varying(255),
    return_date timestamp without time zone,
    return_is_late boolean,
    return_penalty integer,
    borrow uuid
);


ALTER TABLE public.mst_return OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16561)
-- Name: trx_borrows; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trx_borrows (
    borrow_id uuid NOT NULL,
    borrow_code character varying(255),
    borrow_date timestamp without time zone,
    borrow_estimated_return_date date,
    book_book_id integer,
    reader_reader_id integer
);


ALTER TABLE public.trx_borrows OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16546)
-- Name: trx_wishlist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trx_wishlist (
    wishlist_id integer NOT NULL,
    book_book_id integer,
    reader_reader_id integer
);


ALTER TABLE public.trx_wishlist OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16545)
-- Name: trx_wishlist_wishlist_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.trx_wishlist ALTER COLUMN wishlist_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.trx_wishlist_wishlist_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3353 (class 0 OID 16524)
-- Dependencies: 217
-- Data for Name: mst_books; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.mst_books (book_id, book_author, book_copy, book_description, book_isbn, book_max_day_borrowed, book_number_of_reading, book_pages, book_publisher, book_ready, book_title, book_unreturned, book_wished_by, category_id) VALUES (2, 'Ijat', 1, 'Buku atas arahan cikgu jasmine', NULL, 1, 0, 33, 'Malaysia Airline', 1, 'Tutorial Bahasa Qalbu', 0, 0, NULL);
INSERT INTO public.mst_books (book_id, book_author, book_copy, book_description, book_isbn, book_max_day_borrowed, book_number_of_reading, book_pages, book_publisher, book_ready, book_title, book_unreturned, book_wished_by, category_id) VALUES (1, 'Ryuk', 1, 'Alias Death Note', NULL, 7, 8, 666, 'Ryuk', 0, 'Buku Kematian', 1, 0, NULL);
INSERT INTO public.mst_books (book_id, book_author, book_copy, book_description, book_isbn, book_max_day_borrowed, book_number_of_reading, book_pages, book_publisher, book_ready, book_title, book_unreturned, book_wished_by, category_id) VALUES (5, 'saleh', 2, 'Buku masya Allah', NULL, 3, 2, 212, 'MUI', 0, 'Cara jadi guru ngaji', 2, 0, NULL);
INSERT INTO public.mst_books (book_id, book_author, book_copy, book_description, book_isbn, book_max_day_borrowed, book_number_of_reading, book_pages, book_publisher, book_ready, book_title, book_unreturned, book_wished_by, category_id) VALUES (4, 'saleh', 2, 'Buku masya Allah', NULL, 3, 2, 212, 'MUI', 0, 'Cara jadi guru ngaji', 2, 0, NULL);
INSERT INTO public.mst_books (book_id, book_author, book_copy, book_description, book_isbn, book_max_day_borrowed, book_number_of_reading, book_pages, book_publisher, book_ready, book_title, book_unreturned, book_wished_by, category_id) VALUES (7, 'aku', 1, 'Keren', NULL, 4, 1, 345, 'gramed', 0, 'Ibu ku pahlawan ku', 1, 1, NULL);
INSERT INTO public.mst_books (book_id, book_author, book_copy, book_description, book_isbn, book_max_day_borrowed, book_number_of_reading, book_pages, book_publisher, book_ready, book_title, book_unreturned, book_wished_by, category_id) VALUES (8, 'Diwan', 2, 'Makan bakso sekebon', NULL, 2, 4, 143, 'Youtube Books', 0, 'Diwan Makan Bakso', 2, 0, NULL);
INSERT INTO public.mst_books (book_id, book_author, book_copy, book_description, book_isbn, book_max_day_borrowed, book_number_of_reading, book_pages, book_publisher, book_ready, book_title, book_unreturned, book_wished_by, category_id) VALUES (14, 'Pleaides', 1, 'Veni Vidi Vici', NULL, 7, 1, 632, 'Roman', 0, 'Banyak Jalan Menuju Roma', 1, 0, NULL);
INSERT INTO public.mst_books (book_id, book_author, book_copy, book_description, book_isbn, book_max_day_borrowed, book_number_of_reading, book_pages, book_publisher, book_ready, book_title, book_unreturned, book_wished_by, category_id) VALUES (6, 'saleh', 2, 'Buku masya Allah', NULL, 3, 3, 212, 'MUI', 0, 'Cara jadi guru ngaji 2', 2, 0, NULL);
INSERT INTO public.mst_books (book_id, book_author, book_copy, book_description, book_isbn, book_max_day_borrowed, book_number_of_reading, book_pages, book_publisher, book_ready, book_title, book_unreturned, book_wished_by, category_id) VALUES (9, 'Diwan', 1, 'Makan somay sekebon', NULL, 2, 5, 143, 'Youtube Books', 0, 'Diwan Makan Somay', 1, 6, NULL);


--
-- TOC entry 3351 (class 0 OID 16516)
-- Dependencies: 215
-- Data for Name: mst_readers; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.mst_readers (reader_id, reader_address, reader_email, reader_name, reader_phone) VALUES (2, 'Jalan gonjang ganjing', NULL, 'Amir', NULL);
INSERT INTO public.mst_readers (reader_id, reader_address, reader_email, reader_name, reader_phone) VALUES (3, 'Jalan pake sepatu super', NULL, 'Alfredo', '081240956079');
INSERT INTO public.mst_readers (reader_id, reader_address, reader_email, reader_name, reader_phone) VALUES (4, 'Jalan 216', 'Ngokok', 'Arturo', NULL);
INSERT INTO public.mst_readers (reader_id, reader_address, reader_email, reader_name, reader_phone) VALUES (6, 'Jalan Jalan Ke Dupan', NULL, 'Yusuf', NULL);
INSERT INTO public.mst_readers (reader_id, reader_address, reader_email, reader_name, reader_phone) VALUES (5, 'Kayumanis', 'chiko@gmail.com', 'Chiko Hakles', '098765329578');


--
-- TOC entry 3357 (class 0 OID 16576)
-- Dependencies: 221
-- Data for Name: mst_return; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.mst_return (return_id, return_code, return_date, return_is_late, return_penalty, borrow) VALUES ('c38bb65a-1d10-4e23-8625-1abf7f005c45', 'R22062023NBC', '2023-06-22 13:55:50.896', false, NULL, 'aaeb08b5-666d-4a27-93dc-81113b06a42c');
INSERT INTO public.mst_return (return_id, return_code, return_date, return_is_late, return_penalty, borrow) VALUES ('c5880f39-7828-44b9-ab6f-41e5dc95af42', 'R22062023TPL', '2023-06-22 14:01:28.033', false, NULL, 'aaeb08b5-666d-4a27-93dc-81113b06a42c');
INSERT INTO public.mst_return (return_id, return_code, return_date, return_is_late, return_penalty, borrow) VALUES ('e349c5c5-ec74-4166-af05-2ca87355f157', 'R22062023QGB', '2023-06-22 17:58:52.775', false, NULL, '5ea935e8-8732-4153-bec9-262b6c7546f7');
INSERT INTO public.mst_return (return_id, return_code, return_date, return_is_late, return_penalty, borrow) VALUES ('3341faba-e67a-4529-a983-66ff1ae9c441', 'R22062023AKM', '2023-06-22 19:23:26.292', false, NULL, '5b660a4d-a000-4162-81d4-5857ca4cb223');
INSERT INTO public.mst_return (return_id, return_code, return_date, return_is_late, return_penalty, borrow) VALUES ('8cb8c0d6-c84d-4e4a-94e8-6ea8b8d6d715', 'R22062023RWO', '2023-06-22 19:24:39.529', false, NULL, '52561536-8050-46fb-a478-31e32cd30280');
INSERT INTO public.mst_return (return_id, return_code, return_date, return_is_late, return_penalty, borrow) VALUES ('923a98bc-c9ed-46df-b5e6-9bd68a601ec1', 'R24062023RIK', '2023-06-24 23:42:54.7', false, NULL, 'f66a0d9a-9ea9-4a0c-9b04-403fa9b03306');
INSERT INTO public.mst_return (return_id, return_code, return_date, return_is_late, return_penalty, borrow) VALUES ('ee44e962-b630-4caf-883e-4cdeb0a2a377', 'R24062023VID', '2023-06-24 23:48:51.656', false, NULL, '6860acaf-fb33-4f5c-8dc0-3491ca5a9011');
INSERT INTO public.mst_return (return_id, return_code, return_date, return_is_late, return_penalty, borrow) VALUES ('59880016-1fab-4334-9101-c27b26bc6e1a', 'R24062023WBE', '2023-06-24 23:49:37.485853', false, NULL, '611a6c1b-0e5e-4ea0-93f9-87f424f45f86');
INSERT INTO public.mst_return (return_id, return_code, return_date, return_is_late, return_penalty, borrow) VALUES ('50b596ce-f5df-4abb-ba22-bb86e3ea9a92', 'R25062023IXQ', '2023-06-25 00:08:01.424753', false, NULL, '0ef46b5e-3918-4a96-8bc4-78115d8fb092');
INSERT INTO public.mst_return (return_id, return_code, return_date, return_is_late, return_penalty, borrow) VALUES ('656db733-13fa-4594-99ef-d2b64560d163', 'R25062023SZZ', '2023-06-25 00:13:14.974756', false, NULL, 'e75e5cad-9d10-4cb7-87dd-43e8164a7738');


--
-- TOC entry 3356 (class 0 OID 16561)
-- Dependencies: 220
-- Data for Name: trx_borrows; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('bbeb3057-e69b-4ab5-affb-73035e48aafb', 'B21062023ZKX', '2023-06-21 23:46:13.332', '2023-06-24', 4, 4);
INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('e4053277-b75c-4eb4-868f-4d4a6946121b', 'B21062023PLZ', '2023-06-21 23:47:01.259', '2023-06-24', 5, 3);
INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('5ea935e8-8732-4153-bec9-262b6c7546f7', 'B22062023ECB', '2023-06-22 00:03:29.216', '2023-06-25', 6, 3);
INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('a655b0e6-2e25-4c29-9f53-d8f8122e43fb', 'B22062023YZW', '2023-06-22 00:03:36.399', '2023-06-25', 6, 2);
INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('bd2fe814-e128-4702-9811-16c1db209d7f', 'B22062023WOE', '2023-06-22 00:46:07.141', '2023-06-26', 7, 4);
INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('aaeb08b5-666d-4a27-93dc-81113b06a42c', 'B22062023867766', '2023-06-22 13:54:22.197', '2023-06-24', 8, 5);
INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('81c72a8c-c384-414e-9b5e-596b5902516f', 'B22062023TAM', '2023-06-22 14:01:44.278', '2023-06-24', 8, 5);
INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('a03271d4-0176-4466-ad5a-14c569480ea5', 'B22062023ZZD', '2023-06-22 18:02:23.384', '2023-06-25', 6, 2);
INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('5b660a4d-a000-4162-81d4-5857ca4cb223', 'B22062023YEL', '2023-06-22 19:22:04.404', '2023-06-24', 9, 5);
INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('52561536-8050-46fb-a478-31e32cd30280', 'B22062023MEC', '2023-06-22 19:23:47.073', '2023-06-24', 9, 4);
INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('f66a0d9a-9ea9-4a0c-9b04-403fa9b03306', 'B24062023TJG', '2023-06-24 23:38:53.124185', '2023-06-26', 9, 5);
INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('6860acaf-fb33-4f5c-8dc0-3491ca5a9011', 'B24062023SWW', '2023-06-24 23:43:31.633441', '2023-06-26', 9, 5);
INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('611a6c1b-0e5e-4ea0-93f9-87f424f45f86', 'B24062023MAY', '2023-06-24 23:49:37.33226', '2023-06-26', 9, 5);
INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('0ef46b5e-3918-4a96-8bc4-78115d8fb092', 'B25062023UUT', '2023-06-25 00:08:01.310073', '2023-06-27', 8, 5);
INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('e75e5cad-9d10-4cb7-87dd-43e8164a7738', 'B25062023ZWN', '2023-06-25 00:13:14.822734', '2023-06-27', 8, 5);
INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('0db27807-f4df-43d9-8ea8-cfc0f81a1391', 'B25062023ABS', '2023-06-25 01:15:34.568299', '2023-07-02', 16, 5);
INSERT INTO public.trx_borrows (borrow_id, borrow_code, borrow_date, borrow_estimated_return_date, book_book_id, reader_reader_id) VALUES ('8d715f1e-59ae-401c-bee2-fa76beabac01', 'B25062023FBF', '2023-06-25 01:16:57.383995', '2023-07-02', 17, 5);


--
-- TOC entry 3355 (class 0 OID 16546)
-- Dependencies: 219
-- Data for Name: trx_wishlist; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.trx_wishlist (wishlist_id, book_book_id, reader_reader_id) VALUES (4, 2, 3);
INSERT INTO public.trx_wishlist (wishlist_id, book_book_id, reader_reader_id) VALUES (23, 6, 4);;
INSERT INTO public.trx_wishlist (wishlist_id, book_book_id, reader_reader_id) VALUES (26, 7, 2);
INSERT INTO public.trx_wishlist (wishlist_id, book_book_id, reader_reader_id) VALUES (28, 4, 2);
INSERT INTO public.trx_wishlist (wishlist_id, book_book_id, reader_reader_id) VALUES (31, 7, 3);


--
-- TOC entry 3364 (class 0 OID 0)
-- Dependencies: 216
-- Name: mst_books_book_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.mst_books_book_id_seq', 28, true);


--
-- TOC entry 3365 (class 0 OID 0)
-- Dependencies: 214
-- Name: mst_readers_reader_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.mst_readers_reader_id_seq', 11, true);


--
-- TOC entry 3366 (class 0 OID 0)
-- Dependencies: 218
-- Name: trx_wishlist_wishlist_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.trx_wishlist_wishlist_id_seq', 44, true);


--
-- TOC entry 3196 (class 2606 OID 16532)
-- Name: mst_books mst_books_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mst_books
    ADD CONSTRAINT mst_books_pkey PRIMARY KEY (book_id);


--
-- TOC entry 3194 (class 2606 OID 16522)
-- Name: mst_readers mst_readers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mst_readers
    ADD CONSTRAINT mst_readers_pkey PRIMARY KEY (reader_id);


--
-- TOC entry 3202 (class 2606 OID 16580)
-- Name: mst_return mst_return_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mst_return
    ADD CONSTRAINT mst_return_pkey PRIMARY KEY (return_id);


--
-- TOC entry 3200 (class 2606 OID 16565)
-- Name: trx_borrows trx_borrows_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trx_borrows
    ADD CONSTRAINT trx_borrows_pkey PRIMARY KEY (borrow_id);


--
-- TOC entry 3198 (class 2606 OID 16550)
-- Name: trx_wishlist trx_wishlist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trx_wishlist
    ADD CONSTRAINT trx_wishlist_pkey PRIMARY KEY (wishlist_id);


--
-- TOC entry 3205 (class 2606 OID 16566)
-- Name: trx_borrows fkfl5v701y5up9w1lsnl93try5u; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trx_borrows
    ADD CONSTRAINT fkfl5v701y5up9w1lsnl93try5u FOREIGN KEY (book_book_id) REFERENCES public.mst_books(book_id);


--
-- TOC entry 3203 (class 2606 OID 16556)
-- Name: trx_wishlist fkixwksnxv0grhshoatrd4o2l2b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trx_wishlist
    ADD CONSTRAINT fkixwksnxv0grhshoatrd4o2l2b FOREIGN KEY (reader_reader_id) REFERENCES public.mst_readers(reader_id);


--
-- TOC entry 3204 (class 2606 OID 16551)
-- Name: trx_wishlist fkjw44kn4xuwdwxfhhw470nhxjo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trx_wishlist
    ADD CONSTRAINT fkjw44kn4xuwdwxfhhw470nhxjo FOREIGN KEY (book_book_id) REFERENCES public.mst_books(book_id);


--
-- TOC entry 3207 (class 2606 OID 16581)
-- Name: mst_return fkkc17maj06bj67c4ed0xmadij3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mst_return
    ADD CONSTRAINT fkkc17maj06bj67c4ed0xmadij3 FOREIGN KEY (borrow) REFERENCES public.trx_borrows(borrow_id);


--
-- TOC entry 3206 (class 2606 OID 16571)
-- Name: trx_borrows fkor8pjabw8f0xwwpe28povqlv3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trx_borrows
    ADD CONSTRAINT fkor8pjabw8f0xwwpe28povqlv3 FOREIGN KEY (reader_reader_id) REFERENCES public.mst_readers(reader_id);


-- Completed on 2023-06-25 02:45:10

--
-- PostgreSQL database dump complete
--
