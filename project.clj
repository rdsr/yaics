(defproject yaics "1.0.0.0"
  :description "yaics (yet another incidious comic strip)"
  :dependencies [[org.clojure/java.jdbc "0.0.6"]
                 [mysql/mysql-connector-java "5.1.6"]
                 [org.clojure/clojure "1.2.1"]]
  :disable-implicit-clean true
  :dev-dependencies [[hsqldb/hsqldb "1.8.0.10"]
                     [lein-ring "0.4.0"]
                     [swank-clojure "1.3.2"]])