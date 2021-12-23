/*----------------------------------Table Creation------------------------------------*/
create table project."Links"(
   movieId int,
   imdbId int,
   tmdbId int
);

create table project."Ratings"(
   userId int,
   movieId int,
   rating varchar(10),
   timestamp int
);

create table project."Ratings_Small"(
   userId int,
   movieId int,
   rating varchar(10),
   timestamp int
);

create table "project.Keywords"(
   id int,
   keywords text
);

create table "project.Credits"(
   "cast" text,
   crew text,
   id int
);

create table project."Movies_Metadata"(
   adult varchar(10),
   belongs_to_collection varchar(190),
   budget int,
   genres varchar(270),
   homepage varchar(250),
   id int,
   imdb_id varchar(10),
   original_language varchar(10),
   original_title varchar(140),
   overview varchar(1420),
   popularity varchar(10),
   poster_path varchar(40),
   production_companies varchar(1270),
   production_countries varchar(1040),
   release_date date,
   revenue bigint,
   runtime varchar(10),
   spoken_languages varchar(790),
   status varchar(20),
   tagline varchar(310),
   title varchar(140),
   video varchar(10),
   vote_average varchar(10),
   vote_count int
);




/*--------------------------------------CSV Copy-------------------------------------------*/

\copy project."Credits" FROM 'Credits.csv' DELIMITER ',' CSV HEADER;
\copy project."Keywords" FROM 'keywords.csv' DELIMITER ',' CSV HEADER;
\copy project."Links" FROM 'links.csv' DELIMITER ',' CSV HEADER;
\copy project."Movies_Metadata" FROM 'movies_metadata.csv' DELIMITER ',' CSV HEADER;
\copy project."Ratings" FROM 'ratings.csv' DELIMITER ',' CSV HEADER;



/*--------------------------------------Data manipulation---------------------------------*/

DELETE FROM project."Ratings" WHERE "movieid" NOT IN (SELECT DISTINCT "id" FROM project."Movies_Metadata");
DELETE FROM project."Links" WHERE "movieid" NOT IN (SELECT DISTINCT "id" FROM project."Movies_Metadata");

DELETE FROM project."Movies_Metadata" b
WHERE b.ctid <> (SELECT min(d.ctid)
                 FROM   project."Movies_Metadata" d
                 WHERE  b.id = d.id);

DELETE FROM project."Credits" b
WHERE b.ctid <> (SELECT min(d.ctid)
                 FROM   project."Credits" d
                 WHERE  b.id = d.id);

DELETE FROM project."Links" b
WHERE b.ctid <> (SELECT min(d.ctid)
                 FROM   project."Links" d
                 WHERE  b.movieid = d.movieid);

DELETE FROM project."Keywords" b
WHERE b.ctid <> (SELECT min(d.ctid)
                 FROM   project."Keywords" d
                 WHERE  b.id = d.id);

/*------------------------------------Key assignment-------------------------------------*/

ALTER TABLE project."Movies_Metadata"
    ADD PRIMARY KEY (id);

   ALTER TABLE project."Credits"
      ADD PRIMARY KEY (id);

ALTER TABLE project."Keywords"
    ADD PRIMARY KEY (id);

ALTER TABLE project."Ratings"
    ADD PRIMARY KEY (userid,movieid);
   
ALTER TABLE project."Links"
    ADD PRIMARY KEY (movieid);

ALTER TABLE project."Ratings"
    ADD FOREIGN KEY (movieid) REFERENCES project."Movies_Metadata"(id) ;

ALTER TABLE project."Credits" 
   ADD FOREIGN KEY (id) REFERENCES project."Movies_Metadata"(id);

ALTER TABLE project."Keywords" 
   ADD FOREIGN KEY (id) REFERENCES project."Movies_Metadata"(id);

ALTER TABLE project."Links"
    ADD FOREIGN KEY (movieid) REFERENCES project."Movies_Metadata"(id);