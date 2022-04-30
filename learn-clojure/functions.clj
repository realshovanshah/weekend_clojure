(ns learn-clojure.functions)

(defn greet [] (println "Hello"))
(def greet (fn [] (println "Hello")))
(def greet #(println "Hello"))

(greet)


(defn greeting 
    ([] "Hello, World!")
    ([x] (str "Hello, " x "!"))
    ([x y] (str x ", " y "!")))

(assert (= "Hello, World!" (greeting)))
(assert (= "Hello, Clojure!" (greeting "Clojure")))
(assert (= "Good morning, Clojure!" (greeting "Good morning" "Clojure")))


(defn do-nothing [x] x)
(do-nothing [1 2 3])


(defn always-thing [& x] 100)
(always-thing 1 2 3)

(defn make-thingy [x] (fn [& any] x))
(let [n (rand-int Integer/MAX_VALUE)
      f (make-thingy n)]
  (assert (= n (f)))
  (assert (= n (f 123)))
  (assert (= n (apply f 123 (range)))))

(defn triplicate [f] (f) (f) (f))
(triplicate #(prn "Hello"))

(defn opposite [f]
  (fn [& args] (fn [& any] (not (apply f args))))) ;;!

(defn triplicate2 [f & args]
  (triplicate (fn [] (apply f args)))) ;;!

(assert (== -1 (Math/cos Math/PI)))
(assert (== 1 (+ (Math/pow (Math/sin 1) 2) (Math/pow (Math/cos 1) 2))))

(defn http-get [url]
  (slurp (.openStream (java.net.URL. url))))
(assert (.contains (http-get "https://www.w3.org") "html"))

(defn one-less-arg [f x]
  (fn [& args] (apply f x args))) ;;!

(defn two-fns [f g]
  (fn [x] (f (g x))))