(ns yaics.db.comment
  (:use [yaics.settings :only (current-db)])
  (:use [yaics.db.common])
  (:require [clojure.java.jdbc :as sql]))

(def table :comment)

(defn insert
  ([author content id]
     (if-let [record (fetch-record-by table id)]
       (insert author content (:parent_id record) (:image_id record))
       (insert author content -1 id)))
  ([author content parent-id image-id]
     (insert-record
      table
      {:author author :content content :parent_id parent-id :image_id image-id})))

(defn all-comments-for-image [id]
  (fetch-records-by table id "image_id = ?"))

(defn delete [id]
  (delete-record table id))

(defn approve [id]
  (update-record
   table
   id
   (fn [record] (assoc record :hidden false))))

;; (insert "rdsr" "testing" -1 0)
;; (approve 0)
;; (delete 0)
;; (all-comments-for-image 0)
;; (insert "rs" "test" 0 0)
