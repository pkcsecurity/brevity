(ns {{name}}.cljs.views.blog
  (:require [{{name}}.cljc.routes :as routes]
            [{{name}}.cljs.xhr :as xhr]
            [{{name}}.cljs.models.blog :as b]))

(defn blog []
      [:div
       [:h1 "{{name}}: Blog"]
       (if @b/all-entries
         [:div (for [{:keys [id title]} @b/all-entries]
                    ^{:key id}
                    [:div
                     [:a {:href (routes/page :blog-entry :id id)} title]])]
         [:div "Loading entries..."])])

(defn blog-entry []
      (let [{:keys [title content]} @b/blog-entry]
           [:div
            [:h1 (or title "Loading...")]
            [:div
             [:p (or content "")]
             [:a {:href (routes/page :blog)} "Back to Blog"]]]))
