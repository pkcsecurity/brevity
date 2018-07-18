(ns {{name}}.clj.roles.core
  (:require [buddy.auth :as auth]
            [buddy.auth.accessrules :as authz]
            [buddy.auth.backends :as backends]
            [buddy.auth.middleware :as mw]
            [{{name}}.clj.models.sql :as sql]
            [environ.core :as environ]
            [{{name}}.clj.utils.core :as u]))

(def secret (environ/env :auth-secret))
(def issuer (environ/env :auth-issuer))
(def audience (environ/env :auth-audience))
(def token-name (environ/env :auth-token-name))

(def allow-all (constantly true))
(defn allow-admin [{:keys [identity]}]
      (:is-admin identity))
(def deny-all (constantly false))

(def rules
  [{:uris ["/*"]
    :handler allow-all}
   {:pattern #"^/.*$"
    :handler deny-all}])

(defn token-auth [request token]
      ; TODO it's probably best to store the tokens hmac'd to guard against timing attacks
      ; TODO this will need expiry times
      (first (sql/session-by-id sql/dbspec {:id token})))

(defn wrap-security [app]
  (let [auth-backend (backends/token {:authfn token-auth})]
    (-> app
        (authz/wrap-access-rules {:rules rules})
        (mw/wrap-authentication auth-backend)
        (mw/wrap-authorization auth-backend))))

