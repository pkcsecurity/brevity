(ns {{name}}.cljs.views.core
  (:require [{{name}}.cljc.routes :as routes]
            [{{name}}.cljs.views.blog :as blog]
            [{{name}}.cljs.views.account :as account]
            [{{name}}.cljs.views.components :as c]
            [reagent.session :as session]))

(defn index [params]
      [:div
       [:h1 "{{name}}"]
       [:p
        "Brevity's default styles are pretty basic.  To tailor them to your project, see "
        [:a {:href "https://tachyons.io/docs/"} "the tachyons documentation"] "."]])

(defn four-o-four [params]
      [:div
       [:h1 "404: Page not found"]
       [:p ":("]])

(def views
  {:index index
   :login account/login
   :four-o-four four-o-four
   :blog blog/blog
   :blog-entry blog/blog-entry})

(defn page-contents [route]
      (let [page (:current-page route)
            params (:route-params route)]
           [:div.mw7.pv3.ph5.center
            [(views page) params]]))

(defn layout []
      (fn []
          (let [route (session/get :route)]
               [:div
                [c/header]
                ^{:key route} [page-contents route]
                [c/footer]])))
