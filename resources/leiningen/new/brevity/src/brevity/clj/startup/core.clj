(ns {{name}}.clj.startup.core
  (:require [{{name}}.clj.startup.properties :as p]))

(defn startup []
  "Runs before server start time to prepare the application for runtime."
  (p/push-configs))
