(ns {{name}}.startup.core
  (:require [{{name}}.startup.properties :as p]))

(defn startup []
  "Runs before server start time to prepare the application for runtime."
  (p/push-configs))
