(ns {{name}}.cljs.views.core
  (:require [{{name}}.cljc.routes :as routes]
            [{{name}}.cljs.views.blog :as blog]
            [{{name}}.cljs.views.components :as c]
            [reagent.session :as session]))

(defn index [params]
      [:span
       [:h1 "{{name}}: Index"]
       [:ul
        [:li [:a {:href (routes/page :blog)} "Blog"]]
        [:li [:a {:href "/borken/link"} "Borken link"]]]])

(defn four-o-four [params]
      [:span
       [:h1 "404: Page not found"]
       [:p ":("]])

(def views
  {:index index
   :four-o-four four-o-four
   :blog blog/blog
   :blog-post blog/blog-post})

(defn page-contents [route]
      (let [page (:current-page route)
            params (:route-params route)]
           ((views page) params)))

(defn layout []
      (fn []
          (let [route (session/get :route)]
               [:div
                [c/header]
                ^{:key route} [page-contents route]
                [c/footer]])))