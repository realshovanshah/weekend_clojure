(ns shows-api.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [shows-api.domain :as domain]
            [muuntaja.core :as mun]
            [muuntaja.middleware :as md]))

(defn request [body]
  {:headers
   {"content-type" "application/json"
    "accept" "application/json"}
   :body (mun/encode  "application/json" body)})

(def app
  (md/wrap-format 
    (routes (GET "/shows/:id" [id] (request (domain/get-show-by-id id)))
    (DELETE "/shows/:id" [id] (request (domain/delete-show! id)))
    (GET "/shows" [] (request (domain/get-all-shows)))
    (POST "/shows" [show] (request (domain/create-show! show)))
    (route/not-found "404 Not Found"))))