(ns learn-clojure.sequences)
;; sequences are lazy and unrealized, when accessing it, many of them are preemtively realized

(defn titleize [topic]
    (str topic " for the Brave and True"))

;; map calls seq first on the coll
(map titleize ["Hamsters" "Ragnarok"])
(map titleize '("Empathy" "Decorating"))
(map titleize #{"Elbows" "Soap Carving"})
(map #(titleize (second %)) {:uncomfortable-thing "Winking"})

(map str ["a" "b" "c"] ["A" "B" "C"])
;; (list (str "a" "A") (str "b" "B") (str "c" "C"))

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(map #(% [3 4 10]) [sum count avg])

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])
(map :real identities)


;; reduce - process a collection and build a result (can be a larger result too)
(defn my-reduce
  ([f initial coll]
   (loop [result initial
          remaining coll]
     (if (empty? remaining)
       result
       (recur (f result (first remaining)) (rest remaining)))))
  ([f [head & tail]]
   (my-reduce f head tail)))
(my-reduce + [1 2 3 4])

(reduce + [1 2 3 4])
(+ (+ (+ 1 2) 3) 4)
(reduce + 15 [1 2 3 4])

(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
        {}
        {:max 30 :min 10}) ; {:max 30 :min 10} is treated as ([:max 30] [:min 10])
;; similar to:
(assoc (assoc {} :max (inc 30))
       :min (inc 10))

(reduce (fn [new-map [key val]]
          (if (> val 4)
            (assoc new-map key val)
            new-map))
        {}
        {:human 4.1 :critter 3.9})

;; map impl using reduce
(defn my-map [f coll]
    (reduce (fn [new-coll x]
              (conj new-coll (f x)))
            [] coll))
(my-map dec [1 2 3])

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])
(take-while #(< (:month %) 3) food-journal)
(drop-while #(< (:month %) 3) food-journal)
;; filter will process every element unlike %-while
(filter #(< (:human %) 5) food-journal)
(filter #(< (:month %) 3) food-journal)
(some #(> (:critter %) 5) food-journal) ;;to check
(some #(> (:critter %) 3) food-journal)
(some #(and (> (:critter %) 3) %) food-journal)
(sort ["aaa" "c" "bb"])
(sort-by count ["aaa" "c" "bb"])

(concat (take 8 (repeat "na")) ["Batman!"])
(take 3 (repeatedly (fn [] (rand-int 10))))




(comment 
    (second {:a "rnd"})
    (sum [2 3 4])
    (first (seq {:a "rnd"})))