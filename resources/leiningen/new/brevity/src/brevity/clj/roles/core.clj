(ns {{name}}.clj.roles.core
  (:require [buddy.auth :as auth]
            [buddy.auth.accessrules :as authz]
            [buddy.auth.backends.token :as token]
            [buddy.auth.middleware :as mw]
            [caesium.crypto.generichash :as crypto]
            [{{name}}.clj.utils.core :as u]))

(def secret (u/property :auth :secret))
(def issuer (u/property :auth :issuer))
(def audience (u/property :auth :audience))
(def token-name (u/property :auth :token-name))

(def allow-all (constantly true))
(def deny-all (constantly false))

(def rules
  [{:uris ["/"]
    :handler allow-all}
   {:pattern #"^/static/.*$"
    :handler allow-all}
   {:pattern #"^/.*$"
    :handler deny-all}])

(defn wrap-security [app]
  (let [hashed-secret (crypto/hash (.getBytes @secret "UTF-8") {:size 32})
        auth-backend (token/jwe-backend 
                       {:secret hashed-secret
                        :token-name @token-name
                        :options {:iss @issuer
                                  :aud @audience
                                  :alg :a256kw
                                  :enc :a256gcm}})]
    (-> app
        (authz/wrap-access-rules {:rules rules})
        (mw/wrap-authentication auth-backend)
        (mw/wrap-authorization auth-backend))))

