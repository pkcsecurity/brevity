(ns {{name}}.cljc.routes
    (:require [bidi.bidi :as bidi]))

(def app-routes
  ["/" {"" :index
        "blog" {"" :blog
                ["/" :post-id] :blog-post}
        "missing-route" :missing-route
        true :four-o-four}])

(def path (partial bidi/path-for app-routes))