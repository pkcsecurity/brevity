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

(defrecord RestModel [state routes-by-method listeners]
           IDeref
           ; TODO this seems kind of wrong but is convenient
           (-deref [_] @state))

(defn init! [{:keys [state]} new-state]
      ; TODO this also seems kind of wrong but is convenient
      (reset! state new-state))

(defn rest-request [method {:keys [state routes-by-method listeners]}
                    & {:keys [data on-success on-error]}]
      ; TODO tie bidi's request methods in with this
      ; TODO instead of routes-by-method, just a list of routes
      (xhr/simple-xhr
        method (make-url method routes-by-method @state)
        :data data
        :on-success
        (fn [{:keys [body] :as response}]
            (reset! state
                    (if (= method :delete)
                      nil
                      body))
            (if (not= method :get)
              (for [listener listeners]
                   (rest-request :get listener)))
            (when on-success (on-success body)))
        :on-error
        (fn [response]
            (when-not (= method :delete
                         (reset! state nil)))
            (when on-error (on-error response)))))

(def rest-get (partial rest-request :get))

(def rest-post (partial rest-request :post))

(def rest-put (partial rest-request :put))

(def rest-delete (partial rest-request :delete))

(defn model [& {:keys [affects] :as settings}]
      (let [routes-by-method (select-keys settings [:get :post :put :delete])]
           (->RestModel (r/atom nil) routes-by-method affects)))
