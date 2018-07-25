(ns {{name}}.cljs.views.account
  (:require [reagent.core :as r]
            [{{name}}.cljs.controllers.session :as s]
            [{{name}}.cljc.routes :as routes]
            [{{name}}.cljs.xhr :as xhr]
            [{{name}}.cljs.views.components :as c]))

(defn login []
      (let [email (r/atom "")
            password (r/atom "")
            message (r/atom "")]
           (fn []
               [:form.mw5.center
                [:div @message]
                [c/input :label-text "Email Address" :id "email" :type "email" :value email]
                [c/input :label-text "Password" :id "password" :type "password" :value password]
                [c/button "Login"
                 (fn [e]
                     (.preventDefault e)
                     (s/login @email @password message))]])))
