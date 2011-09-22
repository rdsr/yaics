(ns yaics.views.homepage
  (:use [hiccup core page-helpers])
  (:use [yaics.views.constants])
  (:require [yaics.db.image :as image]))

(defn- prev-id [id]
  (Math/max (dec id) 0))

(defn- next-id [id]
  (Math/min (inc id) (-> (image/fetch-latest) :id)))

(defn- latest-id []
  (-> (image/fetch-latest) :id))

(defn- last-link []
  (-> 0 image/fetch-by-id :path))

(defn- prev-link [id]
  (-> id prev-id image/fetch-by-id :path))

(defn- next-link [id]
  (-> id next-id image/fetch-by-id :path))

(defn- latest-link []
  (-> (image/fetch-latest) :path))

(defn render-links [id]
  [:div.links
   [:ul
    [:li [:a {:href (last-link)} "&lt;&lt;"]]
    [:li [:a {:href (prev-link id)} "&lt;"]]
    [:li [:a {:href (next-link id)} "&gt;"]]
    [:li [:a {:href (latest-link)} "&gt;&gt;"]]]])

(defn render-image-module [image]
  [:div.comic
   [:img {:src (-> image :path resolve-uri)}]
   (render-links (:id image))])

(defn index-page []
  (let [image (image/fetch-latest)]
    (html5
     [:head
      [:title (str "yaics: " (:title image))]
      (include-css "/css/homepage.css")]
     [:body
      [:h1 (:title image)]
      (render-header)
      (render-image-module image)
      ;; (render-comments image)
      (render-footer)])))
