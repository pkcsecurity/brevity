(ns {{name}}.clj.routes.core
  (:require [ring.middleware.json :as json]
            [ring.middleware.file :as file]
            [ring.middleware.content-type :as ct]
            [{{name}}.clj.roles.core :as roles]
            [{{name}}.clj.utils.core :as u]
            [compojure.core :as r]
            [compojure.route :as route]
            [environ.core :as environ]
            [hiccup.core :as html]))

(def index
  (html/html {:mode :html}
             [:head
              [:meta {:charset "utf-8"}]
              [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
              [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
              [:title "{{name}}"]]
             [:body 
              [:div#app]
              [:script {:src (if (= "development" (environ/env :environment)) "development/index.js" "release/index.js")}]]))

(r/defroutes routes
  (r/GET "/" [] 
         (constantly 
           {:status 200
            :headers {"Content-Type" "text/html"}
            :body index}))
  (route/not-found nil))

(def app
  (-> routes
      (json/wrap-json-response)
      (json/wrap-json-body {:keywords? true})
      (roles/wrap-security)
      (file/wrap-file "static" {:index-files? false})
      (ct/wrap-content-type)))
