(ns yaics.db.image
  (:use [yaics.settings :only (current-db)])
  (:use [yaics.db.common])
  (:require [clojure.java.jdbc :as sql]))

(def table :image)

(defn insert [title image-path]
  (insert-record
   table
   {:title title :path image-path}))

(defn fetch-latest []
  (sql/with-connection current-db
    (sql/with-query-results res
      [(str "select * from " (name table) " order by created_at desc limit 1")]
      (first res))))

(defn fetch-by-title [title]
  (fetch-record-by table title "title = ?"))

(defn fetch-by-id [id]
  (fetch-record-by table id))

(defn increment-views [id]
  (update-record
   table
   id
   (fn [record]
     (assoc record :views (inc (record :views))))))

;; (insert "The Contract" "comics/work.jpg")
;; (fetch-by-title "work")
;; (-> "test_image" fetch-by-title :id fetch-by-id)
;; (-> "test_image" fetch-by-title :id increment-views)
;; (fetch-latest)
