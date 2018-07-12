(ns {{name}}.clj.core
  (:gen-class)
  (:require [{{name}}.clj.routes.core :as r]
            [{{name}}.clj.utils.core :as u]
            [environ.core :as environ]
            [immutant.web :as server]))

(def host (environ/env :server-host))
(def port (environ/env :server-port))

(defn -main [& args]
  (if (= "development" (environ/env :environment))
    (server/run-dmc r/app :host host :port port)
    (server/run r/app :host host :port port)))
