(ns yaics.core
  (:use compojure.core)
  (:use [ring.util.response :only (file-response)])
  (:use [yaics.views.page])
  (:use [yaics.util])
  (:require [yaics.model.image :as image]
            [compojure.route :as route]
            [compojure.handler :as handler]))


(defroutes main-routes
  (GET "/" [] (render-page))
  (GET "/comic/:id" [id] (file-response (path-of-comic id)))
   (route/not-found "Page not found"))

(def app
  (handler/site main-routes))
