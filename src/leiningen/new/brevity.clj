(ns leiningen.new.brevity
  (:require [leiningen.new.templates :refer [renderer year date project-name
                                             ->files sanitize-ns name-to-path
                                             multi-segment]]
            [leiningen.core.main :as main]))

(def render (renderer "brevity"))

(defn brevity [name]
  (let [main-ns (multi-segment (sanitize-ns name))
        data {:raw-name (.replace (str name) \- \_)
              :name (project-name name)
              :namespace main-ns
              :nested-dirs (name-to-path main-ns)}]
    (main/info "Generating fresh 'lein new' brevity project.")
    (->files data
             "private"
             "sql"
             "target"
             "src/{{raw-name}}/external"
             "src/{{raw-name}}/shutdown"
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             [".gitignore" (render "gitignore" data)]
             ["static/index.html" (render "static/index.html" data)]
             ["private/app.yaml" (render "private/app.yaml" data)]
             ["src/{{nested-dirs}}.clj" (render "src/brevity/core.clj" data)]
             ["src/{{raw-name}}/roles/core.clj" (render "src/brevity/roles/core.clj" data)]
             ["src/{{raw-name}}/routes/core.clj" (render "src/brevity/routes/core.clj" data)]
             ["src/{{raw-name}}/startup/core.clj" (render "src/brevity/startup/core.clj" data)]
             ["src/{{raw-name}}/startup/properties.clj" (render "src/brevity/startup/properties.clj" data)]
             ["src/{{raw-name}}/utils/core.clj" (render "src/brevity/utils/core.clj" data)]
             ["src/{{raw-name}}/utils/spec.clj" (render "src/brevity/utils/spec.clj" data)]
             ["src/{{raw-name}}/models/sql.clj" (render "src/brevity/models/sql.clj" data)]
             ["tool-src/brevity/core.clj" (render "tool-src/brevity/core.clj" data)]
             ["tool-src/templates/entity.clj" (render "tool-src/templates/entity.clj")])))
