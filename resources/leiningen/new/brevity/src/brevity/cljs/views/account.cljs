(ns {{name}}.cljs.views.account
  (:require [cljs-http.client :as http]
            [clojure.core.async :as async :refer [<!]]
            [reagent.core :as r]
            [reagent.cookies :as cookies]
            [reagent.session :as session]
            [accountant.core :as accountant]
            [{{name}}.cljc.routes :as routes]
            [{{name}}.cljs.xhr :as xhr]
            [{{name}}.cljs.views.components :as c]))

(defn successful-login [{:keys [token user]}]
      ; TODO this
      (cookies/set! :brevity-token token)
      (accountant/navigate! (routes/page :index)))

(defn submit [email password message]
      (reset! message "Logging in...")
      (async/go
        (let [body {:json-params {:email email :password password}}
              request (http/post (routes/api :login) body)
              {:keys [status body]} (<! request)]
             (if (= 200 status)
               (successful-login body)
               (reset! message "Invalid email or password.")))))

(defn login []
      (let [email (r/atom "")
            password (r/atom "")
            message (r/atom "")]
           (fn []
               [:form
                [:div @message]
                [:div
                 [:label {:for "email"} "Email Address"]
                 [c/input :id "email" :type "email" :value email]]
                [:div
                 [:label {:for "password"} "Password"]
                 [c/input :id "password" :type "password" :value password]]
                [:button {:on-click
                          (fn [e]
                              (.preventDefault e)
                              (submit @email @password message))}
                 "Login"]])))
