(ns {{name}}.cljs.models.core
  (:require [{{name}}.cljc.routes :as routes]
            [{{name}}.cljs.xhr :as xhr]
            [reagent.core :as r]
            [reagent.session :as session]))

(defn make-url [request-method routes-by-method {:keys [id]}]
      (let [base-url-parts (request-method routes-by-method)
            id-parts (when id [:id id])
            url-parts (concat base-url-parts id-parts)]
           (apply routes/api url-parts)))

(defrecord RestModel [state routes-by-method])

(defn rest-get [{:keys [state routes-by-method]} & {:keys [on-success on-error]}]
      ; TODO de-duplicate this stuff
      ; TODO tie bidi's request methods in with this
      ; TODO instead of routes-by-method, just a list of routes
      (xhr/simple-xhr
        :get (make-url :get routes-by-method state)
        :on-success
        (fn [{:keys [body]}]
            (reset! state body)
            (when on-success (on-success body)))
        :on-error
        (fn [response]
            (reset! state nil)
            (when on-error (on-error response)))))

(defn rest-post [{:keys [state routes-by-method]} data & {:keys [on-success on-error]}]
      ; TODO add some listing model and hook this into it
      (xhr/simple-xhr
        :post (make-url :post routes-by-method nil)
        :data data
        :on-success
        (fn [{:keys [body]}]
            (reset! state data)
            (when on-success (on-success body)))
        :on-error
        (fn [response]
            (reset! state nil)
            (when on-error (on-error response)))))

(defn rest-put [{:keys [state routes-by-method]} data & {:keys [on-success on-error]}]
      (xhr/simple-xhr
        :put (make-url :put routes-by-method state)
        :data data
        :on-success
        (fn [response]
            (reset! state data)
            (when on-success (on-success response)))
        :on-error
        (fn [response]
            (reset! state nil)
            (when on-error (on-error response)))))

(defn rest-delete [{:keys [state routes-by-method]} & {:keys [on-success on-error]}]
      (xhr/simple-xhr
        :delete (make-url :delete routes-by-method state)
        :on-success
        (fn [response]
            (reset! state nil)
            (when on-success (on-success response)))
        :on-error
        (fn [response]
            (when on-error (on-error response)))))

(defn model [& {:keys [get put post delete patch] :as routes-by-method}]
      (->RestModel (r/atom nil) routes-by-method))
