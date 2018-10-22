(ns brevity.core
  (:require [clojure.java.shell :refer [sh]]
            [brevity-test-project.clj.models.sql :as sql]
            [migratus.core :as migratus]
            [clojure.tools.cli :as cli]
            [stencil.core :as stencil]))

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

(def default-command #(execute-command "echo Haven't implemented this command yet..."))

(defn generate-scaffolding [name [entity]]
  (let [data {:name name
              :entity entity
              :entity-plural (str entity "s")}
        result (stencil/render-string (slurp "tool-src/templates/entity.clj") data)]
    (spit (str "src/" (:name data) "/clj/models/" (:entity data) ".clj") result)))

(defn generate [name [c & commands]]
  (case c
    "scaffolding" (generate-scaffolding name commands)
    (println "Not a valid command!")))

(def migratus-spec
  {:store :database
   ; The migration dir is relative to /resources, so .sql files will be dropped in resources/private/migrations.
   :migration-dir "private/migrations"
   :db {:connection-uri sql/connection-uri}})

(defn migration-id [[id-command]]
  (Long/parseLong id-command))

(defn wait-for-db! []
  (try
    (when-let [pending-embedded-database (sql/init!)]
      @pending-embedded-database)
    (catch Exception _
      (println "Could not spin up a development database, so we'll attempt to connect to an"
        "already-running instance."))))

(def migration-id-opt
  ["-i" "--id ID" "Migration ID"
   :validate [not-empty]
   :required true
   :parse-fn #(Long/parseLong %)])

(defn user-input [prompt-text]
  (print prompt-text)
  (flush)
  (let [reader (java.io.BufferedReader. *in*)]
    (.readLine reader)))

(defn migrate-outstanding [_]
  (let [db-instance (wait-for-db!)]
    (migratus/migrate migratus-spec)
    (.close db-instance)))

(defn migrate-new [{:keys [options]}]
  (let [{:keys [name]} options
        migration-name (or name (user-input "Migration name: "))
        db-instance (wait-for-db!)]
    (println "Creating up.sql and down.sql for" migration-name "in" (:migration-dir migratus-spec))
    (migratus/create migratus-spec migration-name)
    (.close db-instance)))

(defn execute-migratus [subcommand]
  (fn [{:keys [options]}]
    (let [{:keys [id]} options
          migration-id (or id (Long/parseLong (user-input "Migration ID: ")))
          db-instance (wait-for-db!)]
      (subcommand migratus-spec migration-id)
      (.close db-instance))))

(def subcommands
  {"migrate"
   {:opts []
    :command migrate-outstanding}
   "migrate:new"
   {:opts
    [["-n" "--name NAME" "Migration Name"
      :required true
      :validate [not-empty]]]
    :command migrate-new}
   "migrate:up"
   {:opts [migration-id-opt]
    :command
    (execute-migratus migratus/up)}
   "migrate:down"
   {:opts [migration-id-opt]
    :command
    (execute-migratus migratus/down)}
   "migrate:rollback"
   {:opts []
    :command (fn [_] (migratus/rollback (wait-for-db!)))}})

(defn handle-commands [_ subcommand & args]
  (if-let [{:keys [opts command]} (subcommands subcommand)]
    (let [{:keys [options arguments] :as parsed-opts} (cli/parse-opts args opts :strict true)]
      (command parsed-opts))
    (println "Not a valid command!"))
  (shutdown-agents))
