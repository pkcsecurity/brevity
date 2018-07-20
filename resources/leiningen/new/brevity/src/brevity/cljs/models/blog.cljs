(ns {{name}}.cljs.models.blog
  (:require [{{name}}.cljs.models.core :as m]
            [{{name}}.cljc.routes :as routes]
            [clojure.core.async :as async :refer [<!]]
            [cljs-http.client :as http]
            [reagent.cookies :as cookies]
            [accountant.core :as accountant]))

(def all-entries
  (m/model :get [:blog]))

(def blog-entry
  (m/model
    :affects [all-entries]
    :get [:blog-entry]
    :post [:new-blog-entry]
    :delete [:delete-blog-entry]))
