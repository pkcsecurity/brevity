(ns {{name}}.clj.models.sql
  (:require [clojure.java.jdbc :as jdbc]
            [environ.core :as environ]
            [hugsql.core :as hug]
            [hugsql-adapter-case.adapters :as adapters]
            [hikari-cp.core :as hikari]
            [conman.core :as conman])
  (:import [com.opentable.db.postgres.embedded EmbeddedPostgres]
           [java.io File]))

(def connection-uri (environ/env :jdbc-database-url))

(def dbspec
  (delay {:datasource (hikari/make-datasource
                        {:jdbc-url connection-uri
                         :maximum-pool-size (or (environ/env :hikari-maximum-pool-size) 10)
                         :minimum-idle (or (environ/env :hikari-minimum-idle) 10)})}))

(hug/set-adapter! (adapters/kebab-adapter))

(conman/bind-connection @dbspec "{{raw-name}}/sql/articles.sql")
(conman/bind-connection @dbspec "{{raw-name}}/sql/users.sql")

(defn init! []
      (let [dev-mode? (= "true" (environ/env :dev-database))]
           (when dev-mode?
                 (let [db-port (Integer/parseInt (environ/env :dev-database-port))
                       db (-> (EmbeddedPostgres/builder)
                              (.setPort db-port)
                              (.setDataDirectory (File. "resources/private/development-db"))
                              (.setCleanDataDirectory false))]
                      (future (.start db))))))
