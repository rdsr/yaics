(ns yaics.db.user
  (:use [yaics.db.common])
  (:use [yaics.settings :only (current-db)])
  (:require [clojure.java.jdbc :as sql]))

(def table :user)

(defn insert
  ([user-id first-name last-name]
     (insert user-id first-name last-name false))
  ([user-id first-name last-name admin]
     (insert-record
      table
      {:user_id user-id :first_name first-name :last_name last-name :admin admin})))

(defn fetch [user-id]
  (fetch-record-by table user-id "user_id = ?"))

(defn admin? [user-id]
  (-> user-id fetch :admin))

;; (insert "rdsr" "Ratandeep" "Ratti" true)
;; (fetch "rdsr")
;; (admin? "rdsr")