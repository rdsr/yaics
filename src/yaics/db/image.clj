(ns yaics.db.image
  (:use [yaics.settings :only (current-db)])
  (:require [clojure.java.jdbc :as sql]))

(def table :image)

(defn insert [title image-path]
  (sql/with-connection current-db
    (sql/insert-record
     table
     {:title title :path image-path})))

(defn fetch-record-by-title [title]
  (sql/with-connection current-db
    (sql/with-query-results res
      [(str "select * from " (name table) " where title = ?") title]
      (-> res first))))

(defn fetch-record-by-id [id]
  (sql/with-connection current-db
    (sql/with-query-results res
      [(str "select * from " (name table) " where id = ?") id]
      (-> res first))))

(defn update-record [id update]
  (let [record (fetch-image-record-by-id id)]
    (sql/with-connection current-db
      (sql/update-values
       table
       ["id=?" id] (update record)))))

(defn increment-views [id]
  (update-image-record
   id
   (fn [record]
     (assoc record :views (inc (record :views))))))
