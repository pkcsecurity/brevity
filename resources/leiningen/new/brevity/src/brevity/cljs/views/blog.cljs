(ns {{name}}.cljs.views.blog
  (:require [{{name}}.cljc.routes :as routes]
            [{{name}}.cljs.xhr :as xhr]))

(def blog-posts
  [{:id "interesting-content" :title "A very interesting post" :content "Coming soon!"}
   {:id "halting-problem" :title "How to solve the halting problem" :content "Not coming any time soon."}])

(defn blog [params]
      (let [entries (xhr/api-atom :blog)]
           (fn []
               [:div
                [:h1 "{{name}}: Blog"]
                (if @entries
                  [:div (for [{:keys [id title]} (:body @entries)]
                             ^{:key id}
                             [:div
                              [:a {:href (routes/page :blog/entry :id id)} title]])]
                  [:div "Loading entries..."])])))

(defn blog-entry [params]
      (let [id (:id params)
            entry (xhr/api-atom :blog/entry :id id)]
           (fn []
               (let [{:keys [title content]} (:body @entry)]
                    [:div
                     [:h1 (or title "Loading...")]
                     [:div
                      [:p (or content "")]
                      [:a {:href (routes/page :blog)} "Back to Blog"]]]))))
