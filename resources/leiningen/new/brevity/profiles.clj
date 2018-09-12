{:profiles/dev {:env {:environment "development"
                      :host "127.0.0.1"
                      :port "8080"
                      :session-timeout-minutes "10000"
                      :idle-session-timeout-minutes "15"
                      :jdbc-database-url "jdbc:postgresql://localhost:55432/postgres?user=postgres&password=postgres"
                      :dev-database "true"
                      :dev-database-port "55432"}}}
