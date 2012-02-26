(defproject yaics "1.0.0.0"
  :description "yaics (yet another incidious comic strip)"
  :dependencies [[org.clojure/java.jdbc "0.0.6"]
                 [mysql/mysql-connector-java "5.1.6"]
                 [org.clojure/clojure "1.2.1"]
                 [compojure "0.6.4"]]
  :dev-dependencies [[hsqldb/hsqldb "1.8.0.10"]
                     [lein-ring "0.4.5"]]
  :ring {:handler yaics.core/app})