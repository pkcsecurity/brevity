(ns {{name}}.cljs.views.blog
  (:require [{{name}}.cljc.routes :as routes]
            [{{name}}.cljs.xhr :as xhr]))

(def blog-posts
  [{:id "interesting-content" :title "A very interesting post" :content "Coming soon!"}
   {:id "halting-problem" :title "How to solve the halting problem" :content "Not coming any time soon."}])

(defn blog [params]
      (let [posts (xhr/api-atom :blog)]
           (fn []
               [:div
                [:h1 "{{name}}: Blog"]
                (if @posts
                  [:div (for [{:keys [id title]} (:body @posts)]
                             ^{:key id}
                             [:div
                              [:a {:href (routes/page :blog-post :post-id id)} title]])]
                  [:div "Loading posts..."])])))

(defn blog-post [params]
      (let [post-id (:post-id params)
            post (xhr/api-atom :blog-post :post-id post-id)]
           (fn []
               (let [{:keys [title content]} (:body @post)]
                    [:div
                     [:h1 (or title "Loading...")]
                     [:div
                      [:p (or content "")]
                      [:a {:href (routes/page :blog)} "Back to Blog"]]]))))
