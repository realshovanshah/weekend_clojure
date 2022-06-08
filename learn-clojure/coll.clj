(ns learn-clojure.coll)
;; sequence abstraction is about operating on members individually, 
;; whereas the collection abstraction is about the data structure as a whole

(map identity {:sunlight-reaction "Glitter!"})
(into {} (map identity {:sunlight-reaction "Glitter!"}))

(map identity [:garlic :sesame-oil :fried-eggs])
(into [] (map identity [:garlic :sesame-oil :fried-eggs]))
(into ["cherry"] '("pine" "spruce"))

(conj ["cherry"] ["pine" "spruce"])
(conj ["cherry"] "pine" "spruce")
(conj {:time "midnight"} [:place "ye olde cemetarium"])

(max 0 1 2)
(apply max [0 1 2]) ;; exploding the seq to seperate args

(def add10 (partial + 10))
(add10 3) 



