(ns learn-clojure.control-flow)

;; use do for multiple statements
(if (even? 5)
  (do (println "even")
      true)
  (do (println "odd")
      false))

;; when supports multiple statements
(when (neg? x)
  (throw (RuntimeException. (str "x must be positive: " x))))

;; cond evaluates in order & returns the first true value 
(let [x 5]
  (cond
    (< x 2) "x is less than 2"
    (< x 10) "x is less than 10"
    (< x 5) "x is less than 5"))

;; returns nil
(let [x 11]
  (cond
    (< x 2)  "x is less than 2"
    (< x 10) "x is less than 10"))

;; convention -> use :else for truthy 
(let [x 11]
  (cond
    (< x 2)  "x is less than 2"
    (< x 10) "x is less than 10"
    :else  "x is greater than or equal to 10"))

;; in constant time, for literals
(defn foo [x]
         (case x
           5 "x is 5"
           10 "x is 10"))
(foo 10)

;; default case
(defn foo [x]
         (case x
           5 "x is 5"
           10 "x is 10"
           "x isn't 5 or 10"))
(foo 6)


(dotimes [i 3]
    (prn i))

(doseq [i '(1 2 3)]
    (prn i))

(doseq [letter [:a :b]
               number (range 2)] ; list of 0, 1, 2
         (prn [letter number]))

        
;; list comprehension
(def list-of-letter-num (for [letter [:a :b] 
        number (range 2)]
        [letter number]))

;; loop & recur
(loop [i 0]
  (if (< i 10)
    (recur (inc i))
    i))

(defn increase [i]
  (if (< i 10)
    (recur (inc i))
    i))
(increase 0)

;; try, catch, finally
(try
  (/ 2 1)
  (catch ArithmeticException e
    "divide by zero")
  (finally
    (println "cleanup")))

(try
  (throw (Exception. "something went wrong"))
  (catch Exception e (.getMessage e)))

;; with clojure data
(try
  (throw (ex-info "There was a problem" {:detail 42}))
  (catch Exception e
    (prn (:detail (ex-data e)))))


(let [f (clojure.java.io/writer "/tmp/new")]
  (try
    (.write f "some text")
    (finally
      (.close f))))

;; Can be written:
(with-open [f (clojure.java.io/writer "/tmp/new")]
  (.write f "some text"))