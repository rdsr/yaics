(ns yaics.model.user
  (:use [yaics.model.common])
  (:require [clojure.java.jdbc :as sql]))

(def table :user)

(defn insert
  ([user-id first-name last-name]
     (insert user-id first-name last-name false))
  ([user-id first-name last-name admin]
     (insert-record
      table
      {:user_id user-id 
       :first_name first-name 
       :last_name last-name 
       :admin admin})))

(defn fetch [user-id]
  (fetch-record-by table "user_id = ?" user-id))

(defn admin? [user-id]
  (-> user-id fetch :admin))
