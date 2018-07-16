(ns {{name}}.clj.models.sql
  (:require [clojure.java.jdbc :as jdbc]
            [environ.core :as environ])
  (:import [com.opentable.db.postgres.embedded EmbeddedPostgres]))

(def dbspec {:dbtype (environ/env :sql-dbtype)
             :classname (environ/env :sql-dbname)
             :dbname (environ/env :sql-dbname)
             :host (environ/env :sql-host)
             :port (environ/env :sql-port)
             :user (environ/env :sql-user)
             :password (environ/env :sql-password)
             :encrypt "true"
             :loginTimeout "30"})

(defn init! [dev-mode?]
  (when dev-mode?
    (let [db (EmbeddedPostgres/builder)
          db-port (Integer/parseInt (environ/env :sql-port))]
         (future
           (.setPort db db-port)
           (.start db)))))