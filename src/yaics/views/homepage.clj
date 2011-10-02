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

(defn- first-link [] (str "comic/0"))

(defn- prev-link [id]
  (str "comic/" (prev-id id)))

(defn- next-link [id]
  (str "comic/" (next-id id)))

(defn- latest-link []
  (str "comic/" (latest-id)))

(defn render-links [id]
  [:div.links
   [:ul
    [:li [:a {:href (first-link)} "&lt;&lt;"]]
    [:li [:a {:href (prev-link id)} "&lt;"]]
    [:li [:a {:href (next-link id)} "&gt;"]]
    [:li [:a {:href (latest-link)} "&gt;&gt;"]]]])

(defn render-image-module [image]
  [:div.comic
   [:img {:src (resolve-uri (str "/comic/" (:id image)))}]
   (render-links (:id image))])

(defn homepage []
  (let [image (image/fetch-latest)]
    (html5
     [:head
      [:title (str "yaics: " (:title image))]
      (include-css "/css/homepage.css")]
     [:body
      [:h1 (:title image)]
      (render-header)
      (render-image-module image)
      (render-footer)])))
