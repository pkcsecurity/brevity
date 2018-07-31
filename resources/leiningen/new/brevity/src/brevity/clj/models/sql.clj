(ns {{name}}.clj.models.sql
  (:require [clojure.java.jdbc :as jdbc]
            [environ.core :as environ]
            [hugsql.core :as hug]
            [hugsql-adapter-case.adapters :as adapters])
  (:import [com.opentable.db.postgres.embedded EmbeddedPostgres]
           [java.io File]))

(def dbspec {:dbtype (environ/env :sql-dbtype)
             :classname (environ/env :sql-dbname)
             :dbname (environ/env :sql-dbname)
             :host (environ/env :sql-host)
             :port (environ/env :sql-port)
             :user (environ/env :sql-user)
             :password (environ/env :sql-password)
             :encrypt "true"
             :loginTimeout "30"})

(hug/set-adapter! (adapters/kebab-adapter))

(hug/def-db-fns "{{raw-name}}/sql/articles.sql")
(hug/def-db-fns "{{raw-name}}/sql/users.sql")

(defn init! []
      (let [dev-mode? (= "true" (environ/env :dev-database))]
           (when dev-mode?
                 (let [db-port (Integer/parseInt (environ/env :sql-port))
                       db (-> (EmbeddedPostgres/builder)
                              (.setPort db-port)
                              (.setDataDirectory (File. "resources/private/development-db"))
                              (.setCleanDataDirectory false))]
                      (future (.start db))))))
