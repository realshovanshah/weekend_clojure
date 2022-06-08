(ns learn-clojure.dsa)

;; FIXME: Valid Parentheses
;; (defn valid-parentheses? [str]
;;     (dotimes [i (count "lmao")]
;;         (if (even? i)
;;             (prn (str "Even: " i))
;;             (prn (str "Odd: " i)))))

(defn valid-parentheses? [str]
    (reduce (fn [val [i x]]
            (prn i)
          (if (odd? i) false true))
        str))

(comment 
    (true? [true])
    (even? 0)
    (list "()()")
    (valid-parentheses? "()()"))