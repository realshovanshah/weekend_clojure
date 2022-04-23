(ns shows-api.domain)

(def shows (atom [{:id 1 :name "Utopia" :description "A show about future and the possible absence of it."}]))

(deref shows)



