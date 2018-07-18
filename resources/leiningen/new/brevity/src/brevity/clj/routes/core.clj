(ns {{name}}.clj.routes.core
  (:require [ring.middleware.json :as json]
            [ring.middleware.file :as file]
            [ring.middleware.content-type :as ct]
            [ring.middleware.resource :as resource]
            [{{name}}.clj.roles.core :as roles]
            [{{name}}.clj.utils.core :as u]
            [{{name}}.clj.routes.middleware :as middleware]
            [{{name}}.clj.routes.blog :as blog]
            [{{name}}.clj.routes.login :as login]
            [{{name}}.clj.views.core :as views]
            [{{name}}.cljc.routes :as routing]
            [compojure.core :as r]
            [compojure.route :as route]
            [environ.core :as environ]))

(def api-views
  {:login login/login
   :blog blog/blog-entries
   :blog/entry blog/blog-entry})

(defn page-handler [request handler-name]
      {:status  200
       :headers {"Content-Type" "text/html"}
       :body    views/index})

(defn api-view [request handler-name]
      (when-let [view-fn (api-views handler-name)]
                (view-fn request)))

(r/defroutes routes
             (route/resources "/")
             (route/not-found {:status  404
                               :headers {"Content-Type" "text/html"}
                               :body    views/index}))

(def app
  (-> routes
      (middleware/wrap-bidi routing/page-routes page-handler)
      (middleware/wrap-bidi routing/api-routes api-view)
      (json/wrap-json-response)
      (json/wrap-json-body {:keywords? true})
      (roles/wrap-security)
      (ct/wrap-content-type)))
