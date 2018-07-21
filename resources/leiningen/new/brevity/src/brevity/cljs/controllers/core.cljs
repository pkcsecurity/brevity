(ns {{name}}.cljs.controllers.core
  (:require [{{name}}.cljs.controllers.blog :as blog]))

(def page-initializers
  {:blog-entry blog/blog-entry
   :blog blog/blog-entries})
