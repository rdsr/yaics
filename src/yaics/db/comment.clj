(ns yaics.db.comment
  (:use [yaics.settings :only (current-db)])
  (:use [yaics.db.common])
  (:require [clojure.java.jdbc :as sql]))

(def table :comment)

(defn insert [author content parent-id image-id]
  (sql/with-connection current-db
    (sql/insert-record
     table
     {:author author :content content :parent_id parent-id :image_id image-id})))

(defn insert [author content id]
  (if-let [record (fetch-record-by table id)]
    (insert author content (record "parent_id") (record "image_id"))
    (insert author content -1 id)))

(defn all-comments-for-image [id]
  (sql/with-connection current-db
    (sql/with-query-results res
      [(str "select * from " (name table) " where image_id = ?") id])))

(defn delete-comment [id]
  (delete-record table id))

(defn approve [id]
  (update-record
   table id (fn [record] (assoc record :hidden false))))
