(ns yaics.model.common
  (:use [yaics.model :only (*db-params*)])
  (:require [clojure.java.jdbc :as sql]))

(defn fetch-records-by
  ([table where-clause values]
     (sql/with-connection *db-params*
       (sql/with-query-results res
         [(str "select * from "(name table)" where " where-clause) values]
         res))))

(defn fetch-record-by
  ([table value]
     (first (fetch-records-by table "id = ?" value)))
  ([table where-clause values]
     (first (fetch-records-by table where-clause values))))

(defn update-record
  ([table id update-fn] (update-record table id ["id = ?" id] update-fn))
  ([table id where-params update-fn]
     (let [record (fetch-record-by table id)]
       (sql/with-connection *db-params*
         (sql/update-values
          table
          where-params (update-fn record))))))

(defn delete-record
  ([table value] (delete-record table value "id = ?"))
  ([table value where-clause]
     (sql/with-connection *db-params*
       (sql/delete-rows table [where-clause value]))))

(defn insert-record
  [table record]
  (sql/with-connection *db-params*
    (sql/insert-record table record)))
