(ns learn-clojure.records)

(defrecord CarModel [make model])

;; (-> CarModel)
;; (map->CarModel)

;; Woy to create a record
(def fiat-500 (-> CarModel "Fiat" "500"))

;; Create a record from map
(def ford-focus (map->CarModel {:make "Ford" :model "Focus"}))

;; fiat-500 and ford-focus now work like a map