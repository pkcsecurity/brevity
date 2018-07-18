-- :name insert-article
insert into articles (title, content)
values (:title, :content);

-- :name article-by-id
-- :result :one
select * from articles
where article_id = :id;

-- :name all-articles
select * from articles
order by date_added asc;
