(ns {{name}}.cljc.routes
    (:require [bidi.bidi :as bidi]
              [{{name}}.cljc.utils :as u]))

(def page-routes
  (u/router "/"
            (u/page "" :index)
            (u/context "blog"
                       (u/page "/" :blog)
                       (u/page ["/post/" :post-id] :blog-post))))

(def api-routes
  (u/router "/api/v1/"
            (u/context "blog"
                       (u/GET "/" :blog)
                       (u/GET ["/post/" :post-id] :blog-post))))

(def page (partial bidi/path-for page-routes))
(def api (partial bidi/path-for api-routes))