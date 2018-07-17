(ns {{name}}.clj.routes.blog
    (:require [{{name}}.clj.models.sql :as sql]))

(defn public-view [article]
      (select-keys article [:id :title :content]))

(defn blog-entries [req]
      (let [articles (sql/all-articles sql/dbspec)]
           {:status 200
            :body   (map public-view articles)}))

(defn blog-entry [{:keys [route-params]}]
      (let [{:keys [id]} route-params
            parsed-id (try (Long/parseLong id)
                           (catch NumberFormatException e -1))]
           (if-let [article (first (sql/article-by-id sql/dbspec {:id parsed-id}))]
                   {:status 200
                    :body (public-view article)}
                   {:status 404})))
