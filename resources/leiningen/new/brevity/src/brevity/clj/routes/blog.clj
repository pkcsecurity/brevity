(ns {{name}}.clj.routes.blog
    (:require [{{name}}.clj.models.sql :as sql]
              [clojure.set :as s]))

(defn public-view [article]
      (-> article
          (select-keys [:article-id :title :content])
          (s/rename-keys {:article-id :id})))

(defn blog-entries [req]
      (let [articles (sql/all-articles)]
           {:status 200
            :body   (map public-view articles)}))

(defn blog-entry [{:keys [route-params]}]
      (let [{:keys [id]} route-params
            parsed-id (try (Long/parseLong id)
                           (catch NumberFormatException e -1))]
           (if-let [article (sql/article-by-id {:id parsed-id})]
                   {:status 200
                    :body (public-view article)}
                   {:status 404})))

(defn new-blog-entry [{:keys [body]}]
      (let [article (sql/insert-article (select-keys body [:title :content]))]
           {:status 200
            :body article}))

(defn delete-blog-entry [{:keys [route-params]}]
      (let [{:keys [id]} route-params
            parsed-id (try (Long/parseLong id)
                           (catch NumberFormatException e -1))]
           (sql/delete-article {:id parsed-id})
           {:status 200}))
