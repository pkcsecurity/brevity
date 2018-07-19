(ns {{name}}.cljs.xhr
  (:require [cljs-http.client :as http]
            [reagent.core :as r]
            [reagent.cookies :as cookies]
            [cljs.core.async :as async :refer [<!]]
            [{{name}}.cljc.routes :as routes]))

(defn auth-header []
      (if-let [token (cookies/get :brevity-token)]
              {"Authorization" (str "Token " token)}
              {}))

(defn api-atom [& route-def]
      (let [result (r/atom nil)]
           (async/go
             (let [request (http/get (apply routes/api route-def)
                                     {:headers (auth-header)})
                   response (<! request)]
                  (reset! result response)))
           result))
