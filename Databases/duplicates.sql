DELETE FROM project."Movies_Metadata" b
WHERE b.ctid <> (SELECT min(d.ctid)
                 FROM   project."Movies_Metadata" d
                 WHERE  b.id = d.id);

DELETE FROM project."Links" b   
WHERE b.ctid <> (SELECT min(d.ctid)
                 FROM   project."Links" d
                 WHERE  b.movieId = d.movieId);

DELETE FROM project."Keywords" b
WHERE b.ctid <> (SELECT min(d.ctid)
                 FROM   project."Keywords" d
                 WHERE  b.id = d.id);

DELETE FROM project."Credits" b
WHERE b.ctid <> (SELECT min(d.ctid)
                 FROM   project."Credits" d
                 WHERE  b.id = d.id);