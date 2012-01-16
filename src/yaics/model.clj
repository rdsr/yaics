(ns yaics.model
  (:require [clojure.java.jdbc :as sql]))

(def mysql-db-params
  {:classname "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   :subname "//127.0.0.1:3306/site"
   :user "yaics"
   :password "yaics"})

(def hsql-db-params
  {:classname "org.hsqldb.jdbcDriver"
   :subprotocol "hsqldb"
   :subname "/tmp/site_yaics_hsqldb"})

(def ^{:dynamic true} *db-params* hsql-db-params)

(defn- image []
  (let [p (:subprotocol *db-params*)]
    (sql/create-table
     :image
     [:id :int (if (= "mysql" p) "primary key auto_increment" "default 0")]
     [:title "varchar(250)"]
     [:path "varchar(250)"]
     [:views :int "default 0"]
     [:created_at :timestamp "default current_timestamp"]
     :table-spec (if (= "mysql" p) "ENGINE=InnoDB" ""))))

(defn- comments []
  (let [p (:subprotocol *db-params*)]
    (sql/create-table
     :comments
     [:id :int (if (= "mysql" p) "primary key auto_increment" "default 0")]
     [:author "varchar(20)"]
     [:parent_id :int]
     [:image_id :int "not null"]
     [:content "varchar(1000)"]
     [:hidden "boolean" "default true"]
     [:date :timestamp "default current_timestamp"]
     :table-spec (if (= "mysql" p) "ENGINE=InnoDB" ""))))

(defn- user []
  (let [p (:subprotocol *db-params*)]
    (sql/create-table
     :user
     [:user_id "varchar(20)" (if (= "mysql" p) "primary key" "") "not null"]
     [:first_name "varchar(20)"]
     [:last_name "varchar(20)" ]
     [:admin "boolean" "default false"]
     :table-spec (if (= "mysql" p) "ENGINE=InnoDB" ""))))

(defn init []
  (for [f [image comments user]]
    (sql/with-connection *db-params* (f))))

(defn clean-up []
  (sql/with-connection
    *db-params*
    (doseq [table [:image :comments :user]]
      (try
        (sql/drop-table table)
        (catch Exception _)))))