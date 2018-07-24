(ns {{name}}.cljs.views.components
  (:require [reagent.core :as r]
            [{{name}}.cljc.routes :as routes]
            [{{name}}.cljs.controllers.session :as s]
            [{{name}}.cljs.models.session :as m]
            [{{name}}.cljs.xhr :as xhr]))

(defn header-link [href text class & attrs]
      [:div
       {:class (str "fl bg-dark-gray pa3 dim pointer " class)}
       [:a.no-underline.white {:href href} text]])

(defn welcome-message [{:keys [full-name] :as session}]
      [:div.fr.relative.hide-child
       (if session
         [:div
          [:div.pa3
           [:i.fas.fa-chevron-down.mr2]
           full-name]
          [:div.bg-gray.pa3.dim.pointer.child.absolute.w-100.dim.tc
           {:on-click (fn [e] (.preventDefault e) (s/logout))}
           "Logout"]]
         [header-link (routes/page :login) "Login" "fr"])])

(defn header []
      (xhr/send-get (routes/api :get-account-info) :success-atom m/session)
      (fn []
          [:header.white.cf.bg-dark-gray.tracked-mega.small-caps
           [header-link (routes/page :index) "Home" "fl"]
           [header-link (routes/page :blog) "Blog" "fl"]
           [welcome-message @m/session]]))

(defn loading-spinner []
      [:div.tc.mv3.gray
       [:i.fas.fa-spinner.fa-pulse]])

(defn footer []
      [:div.tc.mv2
       "External link to " [:a {:href "https://github.com/pkcsecurity/brevity"} "brevity"]])

(defn button [text on-click]
      [:button.pointer.grow.w-100.bg-light-gray.b--dark-gray.ba.bw2.tc.tracked.pv1
       {:on-click on-click}
       text])

(def input-default-style {})

(def input-default-classes "w-100 b--grey ba bt bl")

(def input-invalid-classes "")

(def input-focus-classes "")

(def input-invalid-focus-classes "")

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
