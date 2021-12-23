/* erotima 1 */
SELECT DISTINCT COUNT(1), EXTRACT(YEAR FROM release_date) FROM project."Movies_Metadata"
GROUP BY EXTRACT(YEAR FROM release_date)
ORDER BY EXTRACT(YEAR FROM release_date)


/*erotima 2*/
select count(1), y.x->>'name' "name" from project."Movies_Metadata" pm,
lateral (Select json_array_elements(pm.genres)x)y group by "name"

/*erotima 3*/
select count(1), y.x->>'name' "name", EXTRACT(YEAR FROM release_date) from project."Movies_Metadata" pm,
lateral (Select json_array_elements(pm.genres)x)y group by "name", EXTRACT(YEAR FROM release_date)

/*erotima 4*/
select TO_CHAR(AVG(rating),'FM999.000' ) AS average, y.x->>'name' "name"
from project."Ratings" r inner join project."Movies_Metadata" pm on r.movieid = pm.id ,
lateral (Select json_array_elements(pm.genres)x)y 
group by "name"

/*erotima 5*/
SELECT COUNT(1), userid FROM project."Ratings" GROUP BY userid;

/*erotima 6*/
SELECT TO_CHAR(AVG(rating),'FM999.000')AS average , userid FROM project."Ratings" GROUP BY userid

/* view table insight: Pairnoume os pliroforia to poses kritikes exei grapsei enas xristis kai to meso oro ton ratings pou exei valei.
Etsi mporoume na provlepsoume peripoy to vathmo tis epomenis kritikis tou*/

CREATE TABLE project."ViewTable" AS (SELECT userid, COUNT(1) AS NoOfRatings ,TO_CHAR(AVG(rating),'FM999.000' ) AS average
FROM project."Ratings" GROUP BY userid )