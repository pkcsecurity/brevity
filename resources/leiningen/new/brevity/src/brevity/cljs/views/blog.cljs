(ns {{name}}.cljs.views.blog
  (:require [{{name}}.cljc.routes :as routes]))

(def blog-posts
  [{:id "interesting-content" :title "A very interesting post" :content "Coming soon!"}
   {:id "halting-problem" :title "How to solve the halting problem" :content "Not coming any time soon."}])

(defn blog [params]
      [:span
       [:h1 "{{name}}: Blog"]
       [:div (for [{:keys [id title]} blog-posts]
                  ^{:key id}
                  [:div
                   [:a {:href (routes/path :blog-post :post-id id)} title]])]])

(defn blog-post [params]
      (let [post-id (:post-id params)
            {:keys [title content]} (first (filter #(= post-id (:id %)) blog-posts))]
           [:span
            [:h1 title]
            [:div
             [:p content]
             [:a {:href (routes/path :blog)} "Back to Blog"]]]))