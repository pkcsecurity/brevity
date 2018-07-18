(ns {{name}}.clj.routes.login
    (:require [{{name}}.clj.models.sql :as sql]
              [buddy.hashers :as hashers]
              [crypto.random :as random]))

(defn fail-with-dummy-hash
      "Computes a password hash and returns 404.
      This is to ensure that all login checks take roughly the same time, regardless
      of whether or not the email is actually present in our database."
      []
      (hashers/derive "")
      {:status 404})

(defn new-session-token [user-id]
      (let [token (random/base64 50)]
           (sql/insert-session sql/dbspec {:id token :user-id user-id})
           token))

(defn verify-login [user password]
      (let [{:keys [password-hash user-id]} user]
           (if (hashers/check password password-hash)
             {:status 200
              :body {:token (new-session-token user-id)
                     :user (select-keys user [:full-name :email])}}
             ; No need to fail-with-dummy-hash below, since we've already computed a password hash.
             {:status 404})))

; TODO we'll want rate-limiting here
(defn login [{:keys [body]}]
      (let [{:keys [email password]} body]
           (if-let [user (sql/user-by-email sql/dbspec {:email email})]
                   (verify-login user password)
                   (fail-with-dummy-hash))))
