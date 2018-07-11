(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev [:project/dev :profiles/dev]
             :test [:project/test :profiles/test]
             ;; only edit :profiles/* in profiles.clj
             :profiles/dev  {}
             :profiles/test {}
             :project/dev {:source-paths ["src" "tool-src"]
                           :plugins [[lein-auto "LATEST"]]}
             :project/test {}}
  :aliases {"brevity" ["run" "-m" "brevity.core/handle-commands" :project/main]}
  :main ^:skip-aot {{namespace}}
  :dependencies [[org.clojure/clojure "LATEST"]
                 [org.immutant/web "LATEST"]
                 [ring/ring-core "LATEST"]
                 [ring/ring-devel "LATEST"]
                 [ring/ring-json "LATEST"]
                 [compojure "LATEST"]
                 [buddy/buddy-auth "LATEST"]
                 [buddy/buddy-sign "LATEST"]
                 [buddy/buddy-hashers "LATEST"]
                 [caesium "LATEST"]
                 [io.forward/yaml "LATEST"]
                 [camel-snake-kebab "LATEST"]
                 [org.clojure/spec.alpha "LATEST"]
                 [org.clojure/java.jdbc "LATEST"]
                 [stencil "0.5.0"]
                 [org.clojure/clojurescript "LATEST"]
                 [hiccup "LATEST"]
                 [reagent "LATEST"]
                 [environ "LATEST"]]
  :clean-targets ["static/development/js"
                  "static/release/js"
                  "static/development/index.js"
                  "static/development/index.js.map"
                  "out"
                  "target"]
  :plugins [[lein-cljsbuild "LATEST"]
            [lein-environ "LATEST"]]
  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src/cljs"]
                :compiler
                {:output-to "static/development/index.js"
                 :source-map true
                 :output-dir "static/development/js"
                 :optimizations :none
                 :main {{raw-name}}.cljs.core
                 :asset-path "development/js"
                 :cache-analysis true
                 :pretty-print true}}
               {:id "release"
                :source-paths ["src/cljs"]
                :compiler
                {:output-to "static/release/index.js"
                 :source-map "static/release/index.js.map"
                 :externs []
                 :main {{raw-name}}.cljs.core
                 :output-dir "static/release/js"
                 :optimizations :advanced
                 :pseudo-names false}}]})
