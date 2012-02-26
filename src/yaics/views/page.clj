(ns yaics.views.page
  (:use [hiccup core page-helpers])
  (:use [yaics.views.header-footer])
  (:use [yaics.util])
  (:refer clojure.core :exclude [comment])
  (:require [yaics.model.image :as image]
            [yaics.model.comments :as comments]))

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

(defn- render-links [{:keys [id]}]
  [:div.links
   [:ul
    [:li [:a {:href (first-link)} "&lt;&lt;"]]
    [:li [:a {:href (prev-link id)} "&lt;"]]
    [:li [:a {:href (next-link id)} "&gt;"]]
    [:li [:a {:href (latest-link)} "&gt;&gt;"]]]])

(defn- render-image-module [image]
  [:div.comic
   [:img {:src (resolve-uri (str "/comic/" (:id image)))
          :height (height image 400)
          :width  (width image 400)}]])

(defn- author-present? [comment]
  (and (contains? comment :author)
       (not (nil? (:author comment)))))

(defn- render-comment [comment]
  [:div.comment
   [:ul
    (when (author-present? comment)
      [:li "author: " (:author comment)])
    [:li "date: " (:date comment)]
    [:li "content: " (:content comment)]]])

(defn- render-comments-module [image]
  [:div.comments
   [:ul
    (for [comment (comments/all-comments-for-image image)]
      (render-comment comment))]])

(defn render-page
  ([] (render-page (image/fetch-latest)))
  ([image]
     (html5
      [:head
       [:title "yaics: " (:title image)]
       (include-css "/css/homepage.css")]
      [:body
       [:h1 (:title image)]
       (render-header)
       (render-image-module image)
       (render-links image)
       (render-comments-module image)
       (render-footer)])))
