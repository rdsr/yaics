(ns yaics.model.image
  (:use [yaics.model :only (*db-params*)])
  (:use [yaics.model.common])
  (:require [clojure.java.jdbc :as sql]))

(def table :image)

(defn insert [title filename]
  (insert-record
   table
   {:title title :filename filename}))

(defn fetch-latest []
  (sql/with-connection *db-params*
    (sql/with-query-results res
      [(str "select * from " (name table) " order by created_at desc limit 1")]
      (first res))))

(defn fetch-by-title [title]
  (fetch-record-by table "title = ?" title))

(defn fetch-by-id [id]
  (fetch-record-by table id))

(defn increment-views [id]
  (update-record
   table
   id
   (fn [record]
     (assoc record :views (inc (record :views))))))
