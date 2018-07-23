# brevity

Brevity is a template for full-stack clojure apps.  It includes:
- Postgres, using [migratus](https://github.com/yogthos/migratus) for migrations and [HugSQL](https://www.hugsql.org/) for queries.  To enable quick ramp-up for new projects, brevity also provides a convenient way for developers to run postgres, via [otj-pg-embedded](https://github.com/opentable/otj-pg-embedded).
- [Buddy](https://github.com/funcool/buddy) for auth, along with the necessary tables and session logic.
- [Bidi](https://github.com/juxt/bidi) with route definitions in a common `.cljc` file between the frontend and backend.
- [Figwheel](https://github.com/bhauman/lein-figwheel) for a clojurescript REPL and auto-loading during development.

## Requirements
On MacOS, run:
```bash
brew install clojure leiningen postgresql
```
## Creating a project
`lein install` inside the brevity root directory.

cd ..

lein new brevity [project name]
