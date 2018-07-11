(ns {{name}}.clj.startup.properties
  (:require [yaml.core :as yaml]
            [clojure.string :as str]
            [camel-snake-kebab.core :as csk]))

(defn get-config [path]
  (try
    (yaml/from-file path)
    (catch Exception _)))

(defn push-subtree 
  ([{:strs [mode] :as tree}] 
   (System/setProperty "BREVITY_MODE" mode)
   (push-subtree [] (dissoc tree "mode") mode))
  ([prefix tree mode]
   (doseq [[k v] tree]
     (let [prefix (conj prefix (name k))]
       (if (coll? v)
         (push-subtree prefix v mode)
         (System/setProperty 
           (csk/->SCREAMING_SNAKE_CASE (str/join "_" (into [mode] prefix)))
           (str v)))))))

(defn push-configs []
  (push-subtree (get-config "private/app.yaml")))
