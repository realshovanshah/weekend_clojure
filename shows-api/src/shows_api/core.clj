(ns shows-api.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [shows-api.domain :as domain]))

(defroutes app
  (GET "/shows/:id" [id] (domain/get-show-by-id id))
  (GET "/shows" [] (str (domain/get-all-shows)))
  (POST "/shows" [show] (domain/create-show! show))
  (route/not-found "404 Not Found"))
