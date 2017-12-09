(ns brevity.core
  (:require [clojure.java.shell :refer [sh]]))

(defn print-error [err]
  (println (str "Error running command: " err)))

(defn execute-command [shell-command]
  (println (str ">>>RUNNING: " shell-command))
  (let [{:keys [exit out err]} (apply sh (clojure.string/split shell-command #" "))]
    (if (= 1 exit)
      (print-error err)
      (do
        (print out)
        (println ">>>Success!")))))

(def default-command #(execute-command "echo Haven't implemented this yet..."))

(defn generate [[c & commands]]
  (execute-command (str "echo " c " (just echoing the last command for now)"))
  (execute-command (str "echo does this work?")))

(defn handle-commands [c & commands]
  (case c
    "generate" (generate commands)
    "start" (default-command)
    (println "Not a valid command!"))
  (shutdown-agents))