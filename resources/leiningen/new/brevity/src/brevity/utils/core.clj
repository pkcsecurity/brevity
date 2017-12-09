(ns {{name}}.utils.core
  (:require [clojure.string :as str]
            [camel-snake-kebab.core :as csk]
            [{{name}}.startup.core :as startup]))

(startup/startup)

(def mode 
  (delay 
    (or (System/getProperty "BREVITY_MODE") 
        (throw 
          (ex-info "BREVITY_MODE required in environment or app.yaml to run" {})))))

(defn property [& ks]
  (delay 
    (let [k (csk/->SCREAMING_SNAKE_CASE
              (str/join "_" (into
                              [@mode]
                              (map 
                                (comp str/upper-case name) 
                                ks))))]
      (or (System/getProperty k)
          (throw
            (ex-info (format "Missing %s in environment variables or app.yaml" k) {}))))))
