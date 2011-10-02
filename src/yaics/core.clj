(ns yaics.core
  (:use compojure.core)
  (:use [ring.util.response :only (file-response)])
  (:use [yaics.views.homepage ])
  (:require [yaics.db.image :as image]
            [compojure.route :as route]
            [compojure.handler :as handler]))

(defroutes main-routes
  (GET "/" [] (homepage))
  (GET "/comic/:id" [id]
       (file-response (-> id image/fetch-by-id :path)
                      {:root "resources/public"}))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (handler/site main-routes))
