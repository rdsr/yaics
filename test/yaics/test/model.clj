(ns yaics.test.model
  (:use [clojure.test])
  (:require [yaics.model :as model]
            [yaics.model.image :as image]
            [yaics.model.user :as user]
            [yaics.model.comments :as comments]))

(deftest image-test []
  (image/insert "test-image1" "file1.jpg")
  (is (= "test-image1" (-> (image/fetch-latest) :title)))
  (is (= "test-image1" (-> "test-image1" image/fetch-by-title :title)))

  (let [image (image/fetch-latest)
        views (:views image)]
    (image/increment-views (:id image))
    (is (= (inc views) (-> :id image image/fetch-by-id :views)))))

(deftest user-test []
  (user/insert "john" "john" "Shebaski")
  (is (false? (user/admin? "john")))

  (user/insert "rdsr" "rd" "sr" "true")
  (is (true? (user/admin? "rdsr"))))

(deftest comments-test []
  (image/insert "test-image" "file.jpg")
  (user/insert "rdsr" "rd" "sr")
  (let [image-id (-> (image/fetch-latest) :id)]
    (comments/insert "rdsr" "testing comments table" image-id)
    (comments/insert "rdsr" "testing comments table 2" 0 image-id)
    (is (= 0 (-> image-id comments/all-comments-for-image count)))))

;(defn db-fixture [f]
  ;(model/clean-up)
  ;(model/init)
  ;(f))

;(use-fixtures :each db-fixture)
