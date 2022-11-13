-- Get duplicate short-url.
SELECT short_url, COUNT(*) AS "Count" FROM urlshortener GROUP BY short_url HAVING count(*) > 1;

-- get records with short-url
select * from urlshortener where short_url='611b1c91';