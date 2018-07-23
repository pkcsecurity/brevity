(ns {{name}}.clj.views.core
    (:require [environ.core :as environ]
              [hiccup.core :as html]
              [garden.core :as css]))

(def core-css
  (css/css
    {:pretty-print? false}
    [:* {:box-sizing :border-box}]
    [:body {:font-size "16px"
            :line-height 1.5}]
    [:html :body
     {:font-family ["'Roboto'"]
      :color "#222"
      :font-weight 700}]))

(defn style [href]
      [:link
       {:rel "stylesheet"
        :href href}])

(def index
  (html/html {:mode :html}
             [:head
              [:meta {:charset "utf-8"}]
              [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
              [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
              (style "/css/tachyons.min.css")
              [:title "{{name}}"]]
             [:body
              [:div#app]
              [:script {:src (if (= "development" (environ/env :environment)) "/js/development/index.js" "/js/release/index.js")}]]))

