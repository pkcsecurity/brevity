(ns {{name}}.cljs.xhr
  (:require [cljs-http.client :as http]
            [reagent.core :as r]
            [cljs.core.async :as async :refer [<!]]
            [{{name}}.cljc.routes :as routes]))

(defn api-atom [& route-def]
      (let [result (r/atom nil)]
           (async/go (let [request (http/get (apply routes/api route-def))
                           response (<! request)]
                          (reset! result response)))
           result))