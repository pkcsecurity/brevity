(ns {{name}}.cljs.views.components
  (:require [reagent.core :as r]
            [reagent.cookies :as cookies]
            [cljs-http.client :as http]
            [clojure.core.async :as async :refer [<!]]
            [{{name}}.cljc.routes :as routes]
            [{{name}}.cljs.xhr :as xhr]))

(defn logout []
      (async/go
        ; TODO propagate this action out to the global model once we've got it
        (<! (http/post (routes/api :logout)))
        (cookies/set! :brevity-token nil)
        ; TODO get rid of this reload call
        (.reload js/window.location true)))

(defn welcome-message [{:keys [full-name]}]
      [:div
       [:div "Welcome, " full-name]
       [:button {:on-click (fn [e] (.preventDefault e) (logout))}
        "Logout"]])

(defn header []
      (let [user (xhr/api-atom :get-account-info)]
           (fn []
               [:div
                (when (= (:status @user) 200)
                      [welcome-message (:body @user)])
                [:p [:a {:href (routes/page :index)} "testbrev1"]]
                [:hr]])))

(defn footer []
      [:div
       [:hr]
       [:p "External link to " [:a {:href "https://github.com/pkcsecurity/brevity"} "brevity"]]])

(defn nop [& _] nil)

(defn input [& {:keys [class style value placeholder valid? on-change type autofocus? id on-key-press on-blur on-focus]
                :or {on-change nop
                     on-focus nop
                     on-blur nop
                     placeholder ""
                     on-key-press nop
                     valid? true
                     type :text}}]
      (let [focus? (r/atom false)
            value (or value (r/atom ""))]
           (fn [& {:keys [placeholder valid? on-change type autofocus? id on-key-press on-blur on-focus tag-type]
                   :or {on-change nop
                        on-blur nop
                        on-focus nop
                        placeholder ""
                        on-key-press nop
                        valid? true
                        type :text
                        tag-type :input}}]
               [tag-type
                {:type type
                 :autoFocus autofocus?
                 :id (when id id)
                 :placeholder placeholder
                 :on-focus (fn []
                               (reset! focus? true)
                               (on-focus))
                 :on-blur (fn []
                              (reset! focus? false)
                              (on-blur))
                 :on-key-press (fn [e]
                                   (when (= (.-key e) "Enter")
                                         (on-key-press e)))
                 :on-change (fn [e]
                                (reset! value (.. e -target -value))
                                (on-change value e))
                 :value @value
                 :class (str
                          "input px2 border p shadow-none outline-none "
                          (cond
                            (and (not valid?) @focus?) input-invalid-focus-classes
                            (not valid?) input-invalid-classes
                            @focus? input-focus-classes
                            :else input-default-classes)
                          class)
                 :style (merge input-default-style style)}])))
