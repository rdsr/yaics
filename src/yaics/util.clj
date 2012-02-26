(ns yaics.util
  (:require [yaics.model.image :as image]
            [compojure.route :as route]
            [compojure.handler :as handler]))

(defn path-of-comic [id]
  (->> id
      image/fetch-by-id
      :filename
      (str "resources/public/comics/")))

(defn height [image default]
  (if-let [resolution (:resolution image)]
    (first (.split resolution "x"))
    default))

(defn width [image default]
  (if-let [resolution (:resolution image)]
    (second (.split resolution "x"))
    default))