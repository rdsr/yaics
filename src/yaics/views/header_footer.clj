(ns yaics.views.header-footer
  (:use [hiccup core page-helpers]))

(defn render-header []
  [:div.header
   [:em "Yaics : Yet Another Incidious Comic Strip"]])

(defn render-footer []
  [:div.footer])