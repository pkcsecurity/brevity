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
             "static"
             "static/css"
             "static/img"
             "src/{{raw-name}}/clj/external"
             "src/{{raw-name}}/clj/models"
             "src/{{raw-name}}/clj/shutdown"
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             [".gitignore" (render "gitignore" data)]
             ["static/css/index.css" (render "static/css/index.css" data)]
             ["private/app.yaml" (render "private/app.yaml" data)]
             ["src/{{nested-dirs}}.clj" (render "src/brevity/core.clj" data)]
             ["src/{{raw-name}}/clj/roles/core.clj" (render "src/brevity/clj/roles/core.clj" data)]
             ["src/{{raw-name}}/clj/routes/core.clj" (render "src/brevity/clj/routes/core.clj" data)]
             ["src/{{raw-name}}/clj/startup/core.clj" (render "src/brevity/clj/startup/core.clj" data)]
             ["src/{{raw-name}}/clj/startup/properties.clj" (render "src/brevity/clj/startup/properties.clj" data)]
             ["src/{{raw-name}}/clj/utils/core.clj" (render "src/brevity/clj/utils/core.clj" data)]
             ["src/{{raw-name}}/cljs/core.cljs" (render "src/brevity/cljs/core.cljs" data)]
             ["src/{{raw-name}}/clj/utils/spec.clj" (render "src/brevity/clj/utils/spec.clj" data)]
             ["src/{{raw-name}}/clj/models/sql.clj" (render "src/brevity/clj/models/sql.clj" data)]
             ["tool-src/brevity/core.clj" (render "tool-src/brevity/core.clj" data)]
             ["tool-src/templates/entity.clj" (render "tool-src/templates/entity.clj")])))
