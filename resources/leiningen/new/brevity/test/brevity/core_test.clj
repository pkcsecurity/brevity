(ns {{name}}.core-test
  (:require [{{name}}.clj.core :as server]
            [etaoin.api :as e]
            [etaoin.keys :as keys]
            [environ.core :as environ]
            [clojure.test :refer [deftest use-fixtures is]]))

(defn with-backend [f]
  (let [s (server/-main)]
    (f)
    (.stop s)))

(use-fixtures :once with-backend)

(def driver (e/firefox))

(def base-url (str "http://" (environ/env :host) ":" (environ/env :port)))

(deftest welcome-message-shows-after-login
  (doto driver
    (e/go base-url)
    (e/wait-visible {:css ".login-link a"})
    (e/click {:css ".login-link a"})
    (e/fill {:css ".login-form input[type=email]"} "admin@example.com")
    ; TODO eventually the test logins will go away and we'll want to account for that here
    (e/fill {:css ".login-form input[type=password]"} "secretpassword" keys/enter)
    (e/wait-visible {:css ".welcome-message"})))
