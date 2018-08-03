(ns {{name}}.cljs.views.blog
  (:require [{{name}}.cljc.routes :as routes]
            [{{name}}.cljs.views.components :as c]
            [{{name}}.cljs.models.blog :as b]))

(defn blog []
      [:div
       [:h1 "{{name}}: Blog"]
       (if @b/all-entries
         [:div (for [{:keys [id title]} @b/all-entries]
                    ^{:key id}
                    [:div
                     [:a {:href (routes/page :blog-entry :id id)} title]])]
         [c/loading-spinner])])

(defn blog-entry []
      (let [{:keys [title content]} @b/blog-entry]
           [:div
            [:h1 (or title [c/loading-spinner])]
            [:div
             [:p (or content "")]
             [:a {:href (routes/page :blog)} "Back to Blog"]]]))
