(ns leiningen.new.brevity
  (:require [leiningen.new.templates :refer [renderer year date project-name
                                             ->files sanitize-ns name-to-path
                                             multi-segment]]
            [leiningen.core.main :as main]))

(def render (renderer "brevity"))

(defn brevity [name]
  (let [main-ns (multi-segment (sanitize-ns (str name "/clj/core")))
        data {:raw-name (.replace (str name) \- \_)
              :name (project-name name)
              :namespace main-ns
              :nested-dirs (name-to-path main-ns)}]
    (main/info "Generating fresh 'lein new' brevity project.")
    (->files data
             "private"
             "sql"
             "target"
             "resources"
             "resources/public/css"
             "resources/public/img"
             "src/{{raw-name}}/clj/external"
             "src/{{raw-name}}/clj/models"
             "src/{{raw-name}}/clj/shutdown"
             ["project.clj" (render "project.clj" data)]
             ["profiles.clj" (render "profiles.clj" data)]
             ["README.md" (render "README.md" data)]
             [".gitignore" (render "gitignore" data)]
             ["resources/public/css/tachyons.min.css" (render "resources/public/css/tachyons.min.css" data)]
             ["resources/private/migrations/20180716210558-initial-setup.down.sql"
              (render "resources/private/migrations/20180716210558-initial-setup.down.sql" data)]
             ["resources/private/migrations/20180716210558-initial-setup.up.sql"
              (render "resources/private/migrations/20180716210558-initial-setup.up.sql" data)]
             ["src/{{raw-name}}/clj/core.clj" (render "src/brevity/clj/core.clj" data)]
             ["src/{{raw-name}}/clj/roles/core.clj" (render "src/brevity/clj/roles/core.clj" data)]
             ["src/{{raw-name}}/clj/routes/core.clj" (render "src/brevity/clj/routes/core.clj" data)]
             ["src/{{raw-name}}/clj/routes/middleware.clj" (render "src/brevity/clj/routes/middleware.clj" data)]
             ["src/{{raw-name}}/clj/routes/blog.clj" (render "src/brevity/clj/routes/blog.clj" data)]
             ["src/{{raw-name}}/clj/routes/account.clj" (render "src/brevity/clj/routes/account.clj" data)]
             ["src/{{raw-name}}/clj/utils/core.clj" (render "src/brevity/clj/utils/core.clj" data)]
             ["src/{{raw-name}}/clj/views/core.clj" (render "src/brevity/clj/views/core.clj" data)]
             ["src/{{raw-name}}/cljs/core.cljs" (render "src/brevity/cljs/core.cljs" data)]
             ["src/{{raw-name}}/cljs/xhr.cljs" (render "src/brevity/cljs/xhr.cljs" data)]
             ["src/{{raw-name}}/cljs/models/session.cljs" (render "src/brevity/cljs/models/session.cljs" data)]
             ["src/{{raw-name}}/cljs/models/blog.cljs" (render "src/brevity/cljs/models/blog.cljs" data)]
             ["src/{{raw-name}}/cljs/views/blog.cljs" (render "src/brevity/cljs/views/blog.cljs" data)]
             ["src/{{raw-name}}/cljs/views/account.cljs" (render "src/brevity/cljs/views/account.cljs" data)]
             ["src/{{raw-name}}/cljs/views/components.cljs" (render "src/brevity/cljs/views/components.cljs" data)]
             ["src/{{raw-name}}/cljs/views/core.cljs" (render "src/brevity/cljs/views/core.cljs" data)]
             ["src/{{raw-name}}/cljs/controllers/core.cljs" (render "src/brevity/cljs/controllers/core.cljs" data)]
             ["src/{{raw-name}}/cljs/controllers/blog.cljs" (render "src/brevity/cljs/controllers/blog.cljs" data)]
             ["src/{{raw-name}}/cljs/controllers/session.cljs" (render "src/brevity/cljs/controllers/session.cljs" data)]
             ["src/{{raw-name}}/cljc/routes.cljc" (render "src/brevity/cljc/routes.cljc" data)]
             ["src/{{raw-name}}/cljc/utils.cljc" (render "src/brevity/cljc/utils.cljc" data)]
             ["src/{{raw-name}}/cljc/validators.cljc" (render "src/brevity/cljc/validators.cljc" data)]
             ["src/{{raw-name}}/clj/utils/spec.clj" (render "src/brevity/clj/utils/spec.clj" data)]
             ["src/{{raw-name}}/clj/models/sql.clj" (render "src/brevity/clj/models/sql.clj" data)]
             ["src/{{raw-name}}/sql/articles.sql" (render "src/brevity/sql/articles.sql" data)]
             ["src/{{raw-name}}/sql/users.sql" (render "src/brevity/sql/users.sql" data)]
             ["tool-src/brevity/core.clj" (render "tool-src/brevity/core.clj" data)]
             ["tool-src/templates/entity.clj" (render "tool-src/templates/entity.clj")])))
