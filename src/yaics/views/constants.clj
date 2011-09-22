(ns yaics.views.constants
  (:use [hiccup core page-helpers]))

(defn render-header []
  [:div.header
   [:em "YAICS - Yet Another Incidious Comic Strip"]])

(defn render-footer []
  [:div.footer])