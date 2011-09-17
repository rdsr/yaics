(ns yaics.db.user
  (:use [yaics.settings :only (current-db)])
  (:require [clojure.java.jdbc :as sql]))

(def table :user)

(defn insert user
  ([user-id first-name last-name]
     (insert user-id first-name last-name false))
  ([user-id first-name last-name admin]
     (sql/with-connection current-db
       (sql/insert-record
        table
        {:user_id user-id :first_name first-name :last_name last-name :admin admin}))))

(defn fetch-user-record [user-id]
  (sql/with-connection current-db
    (sql/with-query-results res
      [(str "select * from " (name table) " where user_id = ?") user-id]
      (-> res first))))

(defn admin? [user-id]
  (-> user-id fetch-user-record (get "admin")))