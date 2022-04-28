(ns shows-api.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [shows-api.domain :as domain]
            [shows-api.db :as db]))

(defroutes app
  (GET "/shows/:id" [id] (domain/get-show-by-id id))
  (DELETE "/shows/:id" [id] (domain/delete-show! id))
  (GET "/shows" [] (str (domain/get-all-shows)))
  (POST "/shows" [show] (domain/create-show! show))
  (route/not-found "404 Not Found"))
