(ns yaics.db
  (:use [yaics.settings :only (current-db)])
  (:require [clojure.java.jdbc :as sql]))

(defn insert-image-path [title image-path]
  (sql/with-connection current-db
  (sql/insert-record
   :yaics
   {:title title :path image-path})))

(defn fetch-image-path [title]
  (sql/with-connection current-db
    (sql/with-query-results res
      [(str "SELECT path from yaics where title = ?") title]
      (-> res first :path))))
