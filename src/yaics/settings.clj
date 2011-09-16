(ns yaics.settings
  (:require [clojure.java.jdbc :as sql]))

(def mysql-db {:classname "com.mysql.jdbc.Driver"
               :subprotocol "mysql"
               :subname "//127.0.0.1:3306/site"
               :user "yaics"
               :password "yaics"})

(def hsqldb-db {:classname "org.hsqldb.jdbcDriver"
                :subprotocol "hsqldb"
                :subname "/tmp/site_yaics_hsqldb"})

(defn- clean-up
  [db]
  (sql/with-connection
    db
    (doseq [table [:yaics]]
      (try
        (sql/drop-table table)
        (catch Exception _)))))

(defn- create-table
  [table db]
  (let [p (:subprotocol db)]
    (sql/create-table
     table
     [:id :int (if (= "mysql" p) "PRIMARY KEY AUTO_INCREMENT" "DEFAULT 0")]
     [:title "VARCHAR(1000)"]
     [:path "VARCHAR(250)"]
     :table-spec (if (= "mysql" p) "ENGINE=InnoDB" ""))))

(defn init [db]
  (clean-up db)
  (sql/with-connection db
    (create-table :yaics db)))

(def current-db hsqldb-db)
(init hsqldb-db)