(ns yaics.model
  (:require [clojure.java.jdbc :as sql]))

;; create user 'rdsr'@'%' identified by '****';
;; grant all on yaics.* to rdsr@'%'

(def mysql-db-params
  {:classname "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   :subname "//indeedsoil-dr:3306/yaics"
   :user "rdsr"
   :password "rdsr"})

(def hsql-db-params
  {:classname "org.hsqldb.jdbcDriver"
   :subprotocol "hsqldb"
   :subname "/tmp/site_yaics_hsqldb;shutdown=true;hsqldb.write_delay=false;"})

(def ^{:dynamic true} *db-params* mysql-db-params)

(defn- image []
  (let [p (:subprotocol *db-params*)]
    (sql/create-table
     :image
     [:id :int (if (= "mysql" p) "primary key auto_increment" "default 0")]
     [:hidden "boolean" "default false"]
     [:title "varchar(250)" "not null"]
     [:filename "varchar(250)" "not null"]
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
     [:content "varchar(1000)" "not null"]
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