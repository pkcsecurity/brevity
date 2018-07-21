(ns {{name}}.cljs.models.blog
  (:require [reagent.core :as r]))

(def all-entries (r/atom nil))

(def blog-entry (r/atom nil))
