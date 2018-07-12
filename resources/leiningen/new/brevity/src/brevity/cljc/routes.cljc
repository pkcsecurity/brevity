(ns {{name}}.cljc.routes
    (:require [bidi.bidi :as bidi]))

(def page-routes
  ["/" {"" :index
        "blog" {"" :blog
                ["/" :post-id] :blog-post}
        "missing-route" :missing-route
        true :four-o-four}])

(def path (partial bidi/path-for page-routes))