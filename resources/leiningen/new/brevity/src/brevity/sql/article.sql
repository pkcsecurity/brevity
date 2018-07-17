-- :name insert-article
insert into article (title, content)
values (:title, :content);

-- :name article-by-id
select * from article
where id = :id;

-- :name all-articles
select * from article
order by date_added asc;