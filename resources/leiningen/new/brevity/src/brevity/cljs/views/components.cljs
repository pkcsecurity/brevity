(ns {{name}}.cljs.views.components
  (:require [{{name}}.cljc.routes :as routes]))

(defn header []
      [:div
       [:p [:a {:href (routes/path :index)} "{{name}}"]]
       [:hr]])

(defn footer []
      [:div
       [:hr]
       [:p "External link to " [:a {:href "https://github.com/pkcsecurity/brevity"} "brevity"]]])

