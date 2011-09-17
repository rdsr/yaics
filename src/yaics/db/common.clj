(ns yaics.db.common
  (:use [yaics.settings :only (current-db)])
  (:require [clojure.java.jdbc :as sql]))

(defn fetch-records-by
  ([table value where-clause]
     (sql/with-connection current-db
       (sql/with-query-results res
         [(str "select * from " (name table) " where " where-clause) value]
         res))))

(defn fetch-record-by
  ([table value]
     (first (fetch-records-by table value "id = ?")))
  ([table value where-clause]
     (first (fetch-records-by table value where-clause))))

(defn update-record
  ([table id update-fn] (update-record table id ["id = ?" id] update-fn))
  ([table id where-params update-fn]
     (let [record (fetch-record-by table id)]
       (sql/with-connection current-db
         (sql/update-values
          table
          where-params (update-fn record))))))

(defn delete-record
  ([table value] (delete-record table value "id = ?"))
  ([table value where-clause]
     (sql/with-connection current-db
       (sql/delete-rows table [where-clause value]))))

(defn insert-record
  [table record]
  (sql/with-connection current-db
    (sql/insert-record table record)))
