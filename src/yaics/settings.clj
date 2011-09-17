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
    (doseq [table [:image :comment :user]]
      (try
        (sql/drop-table table)
        (catch Exception _)))))

(defn- create-image-table
  [db]
  (let [p (:subprotocol db)]
    (sql/create-table
     :image
     [:id :int (if (= "mysql" p) "primary key auto_increment" "default 0")]
     [:title "varchar(250)"]
     [:path "varchar(250)"]
     [:views :int "default 0"]
     [:created_at :timestamp "default current_timestamp"]
     :table-spec (if (= "mysql" p) "ENGINE=InnoDB" ""))))

(defn- create-comment-table
  [db]
  (let [p (:subprotocol db)]
    (sql/create-table
     :comment
     [:id :int (if (= "mysql" p) "primary key auto_increment" "default 0")]
     [:parent_id :int]
     [:image_id :int "not null"]
     [:content "varchar(1000)"]
     [:hidden "boolean" "default true"]
     [:date :timestamp "default current_timestamp"]
     :table-spec (if (= "mysql" p) "ENGINE=InnoDB" ""))))

(defn- create-user-table
  [db]
  (let [p (:subprotocol db)]
    (sql/create-table
     :user
     [:user_id "varchar(20)" (if (= "mysql" p) "primary key" "") "not null"]
     [:first_name "varchar(20)"]
     [:last_name "varchar(20)" ]
     [:admin "boolean" "default false"]
     :table-spec (if (= "mysql" p) "ENGINE=InnoDB" ""))))

(defn init [db]
  (for [f [create-image-table create-comment-table create-user-table]]
    (sql/with-connection db (f db))))

(def current-db hsqldb-db)
;; (clean-up current-db)
;; (init current-db)