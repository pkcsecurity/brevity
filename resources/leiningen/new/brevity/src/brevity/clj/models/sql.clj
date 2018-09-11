(ns {{name}}.clj.models.sql
  (:require [clojure.java.jdbc :as jdbc]
            [environ.core :as environ]
            [hugsql.core :as hug]
            [hugsql-adapter-case.adapters :as adapters]
            [conman.core :as conman])
  (:import [com.opentable.db.postgres.embedded EmbeddedPostgres]
           [java.io File]))

; Brevity doesn't yet do any connection pooling by default.  If you have a need for connection pooling, see:
; https://github.com/luminus-framework/conman#using-bind-connection
(def dbspec (environ/env :database-url))

(hug/set-adapter! (adapters/kebab-adapter))

(conman/bind-connection dbspec "{{raw-name}}/sql/articles.sql")
(conman/bind-connection dbspec "{{raw-name}}/sql/users.sql")

(defn init! []
      (let [dev-mode? (= "true" (environ/env :dev-database))]
           (when dev-mode?
                 (let [db-port (Integer/parseInt (environ/env :dev-database-port))
                       db (-> (EmbeddedPostgres/builder)
                              (.setPort db-port)
                              (.setDataDirectory (File. "resources/private/development-db"))
                              (.setCleanDataDirectory false))]
                      (future (.start db))))))
