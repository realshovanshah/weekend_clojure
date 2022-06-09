(ns learn-clojure.fp)

(defn sum
      ([vals] (sum vals 0)) 
      ([vals accumulating-total]
        (if (empty? vals)  
           accumulating-total
           (sum (rest vals) (+ (first vals) accumulating-total)))))

(sum [39 5 1]) ; single-arity body calls two-arity body
(sum [39 5 1] 0)
(sum [5 1] 39)
(sum [1] 44)
(sum [] 45) ; base case is reached, so return accumulating-total

(defn sum
  ([vals] (sum vals 0))
  ([vals accumulating-total]
     (if (empty? vals)
       accumulating-total
       (recur (rest vals) (+ (first vals) accumulating-total)))))