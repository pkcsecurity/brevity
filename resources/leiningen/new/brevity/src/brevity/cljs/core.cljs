(ns {{name}}.cljs.core
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.session :as session]
            [accountant.core :as accountant]
            [bidi.bidi :as bidi]
            [{{name}}.cljc.routes :as routes]))

(enable-console-print!)

(defn index [params]
      [:span
       [:h1 "{{name}}: Index"]
       [:ul
        [:li [:a {:href (routes/path :blog)} "Blog"]]
        [:li [:a {:href "/borken/link"} "Borken link"]]]])

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

(defn four-o-four [params]
      [:span
       [:h1 "404: Page not found"]
       [:p ":("]])

(def views
  {:index index
   :blog blog
   :blog-post blog-post
   :four-o-four four-o-four})

(defn page-contents [route]
      (let [page (:current-page route)
            params (:route-params route)]
           ((views page) params)))

(defn header []
      [:div
       [:p [:a {:href (routes/path :index)} "{{name}}"]]
       [:hr]])

(defn footer []
      [:div
       [:hr]
       [:p "External link to " [:a {:href "https://github.com/pkcsecurity/brevity"} "brevity"]]])

(defn page []
      (fn []
          (let [route (session/get :route)]
               [:div
                [header]
                ^{:key route} [page-contents route]
                [footer]])))

(defn on-js-reload []
      (reagent/render-component [page]
                                (. js/document (getElementById "app"))))

(defn -main []
      (accountant/configure-navigation!
        {:nav-handler (fn
                        [path]
                        (let [match (bidi/match-route routes/app-routes path)
                              current-page (:handler match)
                              route-params (:route-params match)]
                             (session/put! :route {:current-page current-page
                                                   :route-params route-params})))
         :path-exists? (fn [path]
                           (boolean (bidi/match-route routes/app-routes path)))})
      (accountant/dispatch-current!)
      (on-js-reload))

(-main)