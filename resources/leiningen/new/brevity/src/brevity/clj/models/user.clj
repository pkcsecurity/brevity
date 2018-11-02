(ns {{name}}.clj.models.user
  (:require [{{name}}.clj.models.sql :as sql]
            [buddy.hashers :as hash]))

(defn insert! [{:keys [email password full-name admin?]}]
  (sql/insert-user {:email email
                    :passwordhash (hash/derive password)
                    :full-name full-name
                    :is-admin admin?}))