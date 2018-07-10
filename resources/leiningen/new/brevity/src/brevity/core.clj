(ns {{name}}.core
  (:gen-class)
  (:require [{{name}}.clj.routes.core :as r]
            [{{name}}.clj.utils.core :as u]
            [immutant.web :as server]))

(def host (u/property :server :host))
(def port (u/property :server :port))

(defn -main [& args]
  (if (= @u/mode "PROD")
    (server/run r/app :host @host :port @port)
    (server/run-dmc r/app :host @host :port @port)))
