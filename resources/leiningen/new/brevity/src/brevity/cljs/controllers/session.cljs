(ns {{name}}.cljs.controllers.session
  (:require [{{name}}.cljc.routes :as routes]
            [{{name}}.cljs.xhr :as xhr]
            [{{name}}.cljs.models.session :as s]
            [reagent.cookies :as cookies]
            [accountant.core :as accountant]))


(defn successful-login [{:keys [body]}]
      (let [{:keys [token user]} body]
           (cookies/set! :brevity-token token)
           (accountant/navigate! (routes/page :index))
           (xhr/send-get (routes/api :get-account-info)
                         :success-atom s/session)))

(defn login [email password message]
      (reset! message "Logging in...")
      (xhr/send-post
        (routes/api :login)
        :data {:email email :password password}
        :on-success successful-login
        :on-error #(reset! message "Invalid email or password.")))

(defn logout []
      (xhr/send-delete
        (routes/api :logout)
        @s/session
        :on-success
        (fn []
            (cookies/set! :brevity-token "")
            (reset! s/session nil))))
