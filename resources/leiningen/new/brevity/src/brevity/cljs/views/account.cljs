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
                [:div.mv3
                 [:label.db.f6 {:for "email"} "Email Address"]
                 [c/input :id "email" :type "email" :value email :class "w-100"]]
                [:div.mv3
                 [:label.db.f6 {:for "password"} "Password"]
                 [c/input :id "password" :type "password" :value password :class "w-100"]]
                [c/button "Login"
                 (fn [e]
                     (.preventDefault e)
                     (s/login @email @password message))]])))
