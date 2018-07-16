(ns {{name}}.clj.core
  (:gen-class)
  (:require [{{name}}.clj.routes.core :as r]
            [{{name}}.clj.utils.core :as u]
            [{{name}}.clj.models.sql :as sql]
            [environ.core :as environ]
            [immutant.web :as server])
  (:import [com.opentable.db.postgres.embedded EmbeddedPostgres]))

(def host (environ/env :server-host))
(def port (environ/env :server-port))

(defn -main [& args]
  (sql/init! (boolean (environ/env :dev-database)))
  (if (= "development" (environ/env :environment))
    (server/run r/app :host host :port port)
    (server/run-dmc r/app :host host :port port)))
