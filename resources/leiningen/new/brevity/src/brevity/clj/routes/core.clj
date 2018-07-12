(ns {{name}}.clj.routes.core
  (:require [ring.middleware.json :as json]
            [ring.middleware.file :as file]
            [ring.middleware.content-type :as ct]
            [ring.middleware.resource :as resource]
            [{{name}}.clj.roles.core :as roles]
            [{{name}}.clj.utils.core :as u]
            [{{name}}.cljc.routes :as routing]
            [bidi.bidi :as bidi]
            [bidi.ring :as br]
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

(defn blog-handler [req]
      {:status 200
       :body   [{:id "interesting-content" :title "A very interesting post" :content "Coming soon!"}
                {:id "halting-problem" :title "How to solve the halting problem" :content "Not coming any time soon."}]})

(defn blog-post-handler [{:keys [route-params]}]
      {:status 200
       :body   {:id (:post-id route-params) :title "A very interesting post" :content "Coming soon!"}})

(def api-handlers
  {:blog blog-handler
   :blog-post blog-post-handler})

(defn wrap-bidi [handler wrapped-routes renderer]
      (fn [{:keys [uri path-info] :as req}]
          (let [path (or path-info uri)
                resolved-route (bidi/match-route* wrapped-routes path req)
                resolved-handler (:handler resolved-route)
                route-params (:route-params resolved-route)]
               (if (= :four-o-four resolved-handler)
                 (handler req)
                 (renderer
                   (-> req
                       (update-in [:params] merge route-params)
                       (update-in [:route-params] merge route-params))
                   resolved-handler)))))

(defn page-handler [request handler-name]
      {:status  200
       :headers {"Content-Type" "text/html"}
       :body    index})

(defn api-handler [request handler-name]
      ((api-handlers handler-name) request))

(def app
  (-> routes
      (wrap-bidi routing/page-routes page-handler)
      (wrap-bidi routing/api-routes api-handler)
      (json/wrap-json-response)
      (json/wrap-json-body {:keywords? true})
      (roles/wrap-security)
      (ct/wrap-content-type)))
