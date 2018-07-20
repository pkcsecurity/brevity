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

(defn simple-xhr [method url & {:keys [data on-success on-error]}]
      (async/go
        (let [request (http/request
                        {:method method
                         :url url
                         :json-params data
                         :headers (auth-header)})
              response (<! request)
              {:keys [status]} response]
             (if (= 200 status) ; TODO this should actually let through any 200-series code
               (when on-success (on-success response))
               (when on-error (on-error response))))))
