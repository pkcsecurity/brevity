(ns {{name}}.routes.core
  (:require [ring.middleware.json :as json]
            [ring.middleware.file :as file]
            [ring.middleware.content-type :as ct]
            [{{name}}.roles.core :as roles]
            [compojure.core :as r]
            [compojure.route :as route]))

(r/defroutes routes
  (r/GET "/" [] 
         (constantly 
           {:status 200
            :headers {"Content-Type" "text/html"}
            :body (slurp "static/index.html")}))
  (route/not-found nil))

(def app
  (-> routes
      (json/wrap-json-response)
      (json/wrap-json-body {:keywords? true})
      (roles/wrap-security)
      (file/wrap-file "static" {:index-files? false})
      (ct/wrap-content-type)))
