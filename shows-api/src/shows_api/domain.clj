(ns shows-api.domain
  (:require [shows-api.db :as db]))

; (def shows (atom [{:id 1 :name "Utopia" :description "A show about future and the possible absence of it."}]))

(defn get-all-shows []
  (db/get-all-shows))

;; (get-all-shows)
;; (get-show-by-id 1)
;; (delete-show! 3)

(defn get-show-by-id [id]
    (str (first (db/get-show-by-id id))))

(defn create-show! [show]
  (db/create-show! show))

(defn delete-show! [id]
  (db/delete-show! id))

;; (defn get-show-by-id [id]
;;     first (for [show @shows]
;;       (if (= (:id show) (bigint id))
;;         show)))

;; (defn create-show! [show]
;;   "show is a map with :name, :description"
;;   (swap! shows conj (conj {:id (inc (:id (apply max-key :id @shows)))} show )))
