(ns {{name}}.cljs.controllers.blog
  (:require [{{name}}.cljc.routes :as routes]
            [{{name}}.cljs.xhr :as xhr]
            [{{name}}.cljs.models.blog :as m]))

(defn blog-entries []
      (xhr/send-get
        (routes/api :blog)
        :success-atom m/all-entries))

(defn blog-entry [{:keys [id]}]
      (reset! m/blog-entry nil)
      (xhr/send-get
        (routes/api :blog-entry :id id)
        :success-atom m/blog-entry))
