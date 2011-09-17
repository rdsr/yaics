(ns yaics.db.common
  (:use [yaics.settings :only (current-db)])
  (:require [clojure.java.jdbc :as sql]))

(defn fetch-record-by
  "fetch record by a primary key's value"
  ([table value]
     (fetch-record-by value "id = ?"))
  ([table value where-clause]
     (sql/with-connection current-db
       (sql/with-query-results res
         [(str "select * from " (name table) " where " where-clause) value]
         (-> res first)))))

(defn update-record
  [table id where-params update-fn]
  (let [record (fetch-record-by table id)]
    (sql/with-connection current-db
      (sql/update-values
       table
       where-params (update-fn record)))))

(defn delete-record
  ([table value] (delete-record value "id = ?"))
  ([table value where-clause]
     (sql/with-connection current-db
       (sql/delete-rows table [where-clause value]))))