(ns {{name}}.clj.routes.core
  (:require [ring.middleware.json :as json]
            [ring.middleware.file :as file]
            [ring.middleware.content-type :as ct]
            [ring.middleware.resource :as resource]
            [{{name}}.clj.roles.core :as roles]
            [{{name}}.clj.utils.core :as u]
            [{{name}}.cljc.routes :as routing]
            [bidi.bidi :as bidi]
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
              [:script {:src (if (= "development" (environ/env :environment)) "/js/development/index.js" "/js/release/index.js")}]]))

(r/defroutes routes
             (route/resources "/")
             (route/not-found nil))

(defn wrap-index [handler]
      (fn [request]
          (let [resolved-route (bidi/match-route routing/page-routes (:uri request))
                resolved-handler (:handler resolved-route)]
               (if (= :four-o-four resolved-handler)
                 (handler request)
                 {:status  200
                  :headers {"Content-Type" "text/html"}
                  :body    index}))))

(def app
  (-> routes
      (wrap-index)
      (json/wrap-json-response)
      (json/wrap-json-body {:keywords? true})
      (roles/wrap-security)
      (ct/wrap-content-type)))
