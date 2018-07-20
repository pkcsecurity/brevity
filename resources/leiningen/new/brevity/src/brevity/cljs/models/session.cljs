(ns {{name}}.cljs.models.session
  (:require [{{name}}.cljs.models.core :as m]
            [{{name}}.cljc.routes :as routes]
            [clojure.core.async :as async :refer [<!]]
            [cljs-http.client :as http]
            [reagent.cookies :as cookies]
            [accountant.core :as accountant]))

(def session
  (m/model
    :get [:get-account-info]
    :post [:login]
    :delete [:logout]))


(defn successful-login [{:keys [token user]}]
      ; TODO the navigate call might make more sense as part of the view
      (cookies/set! :brevity-token token)
      (accountant/navigate! (routes/page :index))
      (m/rest-get session))

(defn login [email password message]
      (reset! message "Logging in...")
      (m/rest-post
        session
        :data {:email email :password password}
        :on-success successful-login
        :on-error #(reset! message "Invalid email or password.")))

(defn logout []
      (m/rest-delete
        session
        :on-success
        #(cookies/set! :brevity-token "")))
