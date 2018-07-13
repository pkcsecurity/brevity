(ns {{name}}.clj.routes.blog)

(defn blog-handler [req]
      {:status 200
       :body   [{:id "interesting-content" :title "A very interesting post" :content "Coming soon!"}
                {:id "halting-problem" :title "How to solve the halting problem" :content "Not coming any time soon."}]})

(defn blog-post-handler [{:keys [route-params]}]
      {:status 200
       :body   {:id (:post-id route-params) :title "A very interesting post" :content "Coming soon!"}})
