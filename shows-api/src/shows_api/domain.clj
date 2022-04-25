(ns shows-api.domain)

(def shows (atom [{:id 1 :name "Utopia" :description "A show about future and the possible absence of it."}]))

(defn get-all-shows []
  (deref shows))

(defn get-show-by-id [id]
    first (for [show @shows]
      (if (= (:id show) (bigint id))
        show)))

(defn create-show! [show]
  "show is a map with :name, :description"
  (swap! shows conj (conj {:id (inc (:id (apply max-key :id @shows)))} show )))
