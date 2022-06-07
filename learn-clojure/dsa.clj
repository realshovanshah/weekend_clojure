(ns learn-clojure.dsa)

;; Valid Parentheses
(defn valid-parentheses? [str]
    (dotimes [i (count "lmao")]
        (if (even? i)
            (prn (str "Even: " i))
            (prn (str "Odd: " i)))))

(comment 
    (valid-parentheses? "()"))