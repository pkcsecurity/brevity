(ns {{name}}.clj.routes.blog)

(defn blog-entries [req]
      {:status 200
       :body   [{:id "interesting-content" :title "A very interesting post" :content "Coming soon!"}
                {:id "halting-problem" :title "How to solve the halting problem" :content "Not coming any time soon."}]})

(defn blog-entry [{:keys [route-params]}]
      {:status 200
       :body   {:id (:id route-params) :title "A very interesting post" :content "Coming soon!"}})
