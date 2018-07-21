(ns {{name}}.cljs.core
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.session :as session]
            [accountant.core :as accountant]
            [bidi.bidi :as bidi]
            [{{name}}.cljc.routes :as routes]
            [{{name}}.cljs.controllers.core :as controllers]
            [{{name}}.cljs.views.core :as views]))

(enable-console-print!)

(defn on-js-reload []
      (reagent/render-component [views/layout]
                                (. js/document (getElementById "app"))))

(defn -main []
      (accountant/configure-navigation!
        {:nav-handler (fn [path]
                        (let [match (bidi/match-route routes/page-routes path)
                              current-page (:handler match)
                              route-params (:route-params match)
                              initializer (controllers/page-initializers current-page)]
                             (when initializer (initializer route-params))
                             (session/put! :route {:current-page current-page
                                                   :route-params route-params})))
         :path-exists? (fn [path]
                           (boolean (bidi/match-route routes/page-routes path)))})
      (accountant/dispatch-current!)
      (on-js-reload))

(-main)