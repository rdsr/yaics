(ns yaics.model.comments
  (:use [yaics.model.common])
  (:require [clojure.java.jdbc :as sql]))

(def table :comments)

(defn insert
  ([author content image-id]
     (if-let [record (fetch-record-by table "image_id = ?" image-id)]
       (insert author content (:parent_id record) image-id)
       (insert author content -1 image-id)))
  ([author content parent-id image-id]
     (insert-record
      table
      {:author author :content content :parent_id parent-id :image_id image-id})))

(defn all-comments-for-image [image-id]
  (fetch-records-by table "image_id=? and hidden=false" image-id))

(defn delete [id]
  (delete-record table id))

(defn approve [id]
  (update-record
   table
   id
   (fn [record] (assoc record :hidden false))))
