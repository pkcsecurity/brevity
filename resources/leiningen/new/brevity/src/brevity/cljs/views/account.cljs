(ns {{name}}.cljs.views.account
  (:require [cljs-http.client :as http]
            [clojure.core.async :as async :refer [<!]]
            [reagent.core :as r]
            [reagent.cookies :as cookies]
            [reagent.session :as session]
            [accountant.core :as accountant]
            [{{name}}.cljs.models.session :as m]
            [{{name}}.cljc.routes :as routes]
            [{{name}}.cljs.xhr :as xhr]
            [{{name}}.cljs.views.components :as c]))

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
                              (m/login @email @password message))}
                 "Login"]])))
