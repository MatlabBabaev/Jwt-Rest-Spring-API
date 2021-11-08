DATABASE CREATION

CREATE DATABASE jwtappquery
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1252'
    LC_CTYPE = 'Russian_Russia.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;



Postgre SQL  

1. TABLE USERS------------

CREATE TABLE IF NOT EXISTS public.users
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    first_name character varying(200) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(200) COLLATE pg_catalog."default" NOT NULL,
    login character varying(50) COLLATE pg_catalog."default" NOT NULL,
    age integer NOT NULL,
    address character varying(300) COLLATE pg_catalog."default" NOT NULL,
    password character varying(500) COLLATE pg_catalog."default" NOT NULL,
    role character varying(100) COLLATE pg_catalog."default" NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;

2.  TABLE QUERIES(This is the table for APPLICATIONS, somehow I mistakenly named it queries)----------------------------------

CREATE TABLE IF NOT EXISTS public.queries
(
    user_id integer NOT NULL,
    header character varying(500) COLLATE pg_catalog."default" NOT NULL,
    body character varying(500) COLLATE pg_catalog."default" NOT NULL,
    created_date date NOT NULL,
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    CONSTRAINT queries_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.queries
    OWNER to postgres;


3.  TABLE FEEDBACK----------------------------------

CREATE TABLE IF NOT EXISTS public.feedback
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    application_id integer NOT NULL,
    admin_id integer NOT NULL,
    text character varying(800) COLLATE pg_catalog."default" NOT NULL,
    date date NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE public.feedback
    OWNER to postgres;

---------------------------------------------------------------

Before using please insert at least one user into users table with role of "admin".
Example: 
insert into users(first_name, last_name, login, age, address, password, role) 
values('Alesandro', 'Nesta', 'alex', 45, 'Italy milan', '$2a$10$RFT42eyVFiyCoXX/4MtfIOqyUwNHcdXtfGuSO58J8AVJ1JMf1Kp2S', 'admin' )

Here login is: alex, and pass: 1234.

Queries to test:
1. http://localhost:8080/auth/login      POST query.  please pul login and password in Authorization tab. The response will be user details in JSON format.

Get the Token values of the user from headers tab of the respons. example key is: x-csrf-token, and value is eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjZTBhMzE3N2VkYWE0YTMzOTI0NTMyODhkZjVjMmM3NSIsImlhdCI6MTYzNjM5Nzg1NSwibmJmIjoxNjM2Mzk3ODU1LCJleHAiOjE2MzYzOTk2NTV9.j2nmTjTD8yS57oUaQAa2eBgsBFX3xpgY0NuJKvlceDo.
These values are unique for each user so every time you are querying use these key values along with login and password. You have to include token in headers tab.


2. http://localhost:8080/users    GET query. getting list of users. only admins can get response.

3. http://localhost:8080/adduser   POST. adding new user. any user can do it.
Put JSON format text in body tab to send the query. Example:
[{    "login": "Ota",
    "password": "1234",
    "firstname": "Otabek",
    "lastname": "Shermatov",
    "age": 29,
    "address": "Roseberry 25",
    "role": "user"
    }]

4. http://localhost:8080/upduser   POST. Only authenticated or admin user can edit his/her details.
Put JSON format text in body tab to send the query. Example:
[    {
    "id": 7,
    "login": "Mamur",
    "password": 1234,
    "firstname": "Mashennik",
    "lastname": "Batirov",
    "age": 25,
    "address": "Sagban",
    "role": "user"
}

5. http://localhost:8080/createquery POST. Creating new query for application register. Anyone allowed tot do it.
Put JSON format text in body tab to send the query. Example:

[{
        "header": "New project",
        "body": "Hey guys I am gonna to run new project. Who is gonna fund the project?"
    }
]

6. http://localhost:8080/queries  GET. Getting all application queries(admins can do it). If it is user then only created application by this user will be gotten


7. http://localhost:8080/updquery POST. Updating application. Only the owners can update it.

[   {
        "id": 6,
        "user_id": 7,
        "header": "New project2",
        "body": "Hey2 guys I am gonna to run new project. Who is gonna fund the project?",
        "createdDate": "2021-11-06"
    }
]

8. http://localhost:8080/deletequery  POST. Deleting applications. Only owners of the deleting applications can do it.  
Example:

[
    {
        "id": 5,
        "user_id": 6,
        "header": "Job seeking",
        "body": "Hey I need a job, give me a job",
        "createdDate": "2021-11-04"
    }
]

9. http://localhost:8080/addcomment  POST. Adding comment to any application. By Id.
Example in body tab: 

[   {
        "application_id": 6,
        "text": "Hey mate how you are going? Have the same issue"
    }
]

10. http://localhost:8080/admin/getallcomments. GET. Only admins can get all applications. Response is JSON.

11. http://localhost:8080/user/applications/getcomments. GET. Getting comment for specified or choosen application. By id.
example of query: 8.  and send query.  If that application id is not belong to the authenticated user, then response will be empty.   
