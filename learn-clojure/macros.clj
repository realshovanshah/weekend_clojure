(ns learn-clojure.macros)

(defmacro backwards
  [form]
  (reverse form))

;; (backwards (" backwards" " am" "I" str))

(def addition-list (list + 1 2))
(eval addition-list)

(eval (concat addition-list [10]))
(eval (list 'def 'lucky-number (concat addition-list [10])))
(eval (list 'def 'lucky-number (+ 1 2)))
lucky-number

(read-string "(+ 1 2)")
(list? (read-string "(+ 1 2)"))
(conj (read-string "(+ 1 2)") :zagglewag)
(eval (read-string "(+ 1 2)"))

(read-string "#(+ 1 %)")
(read-string "'(a b c)")
(read-string "@var")
(read-string "; ignore!\n(+ 1 2)")

(eval
 (let [infix (read-string "(1 + 1)")]
   (list (second infix) (first infix) (last infix))))

(defmacro ignore-last-operand
  [function-call]
  (butlast function-call))

(ignore-last-operand (+ 1 2 10))
(macroexpand '(ignore-last-operand (+ 1 2 10)))

(eval (list 'prn "Name: Jen, Movie: Jendaya's Adventures"))

(list '+ 1 (inc 1))
`(+ 1 ~(inc 1))

(defmacro code-critic
  "Phrases are courtesy Hermes Conrad from Futurama"
  [bad good]
  (list 'do
        (list 'println
              "Great squid of Madrid, this is bad code:"
              (list 'quote bad))
        (list 'println
              "Sweet gorilla of Manila, this is good code:"
              (list 'quote good))))
(defmacro code-critic
  "Phrases are courtesy Hermes Conrad from Futurama"
  [bad good]
  `(do (println "Great squid of Madrid, this is bad code:"
                (quote ~bad))
       (println "Sweet gorilla of Manila, this is good code:"
                (quote ~good))))

(defn criticize-code
  [criticism code]
  `(println ~criticism (quote ~code)))
(defmacro code-critic
  [bad good]
  `(do ~(criticize-code "Cursed bacteria of Liberia, this is bad code:" bad)
       ~(criticize-code "Sweet sacred boa of Western and Eastern Samoa, this is good code:" good)))
(defmacro code-critic
  [bad good]
  `(do ~(map #(apply criticize-code %)
             [["Great squid of Madrid, this is bad code:" bad]
              ["Sweet gorilla of Manila, this is good code:" good]])))
(defmacro code-critic
  [{:keys [good bad]}]
  `(do ~@(map #(apply criticize-code %)
              [["Sweet lion of Zion, this is bad code:" bad]
               ["Great cow of Moscow, this is good code:" good]])))

(def message "Good job!")
(defmacro with-mischief
  [& stuff-to-do]
  (concat (list 'let ['message "Oh, big deal!"])
          stuff-to-do))
(def message "Good job!")
(defmacro with-mischief
  [& stuff-to-do]
  `(let [message "Oh, big deal!"]
     ~@stuff-to-do))

(gensym)
(gensym 'message)

(defmacro without-mischief
  [& stuff-to-do]
  (let [macro-message (gensym 'message)]
    `(let [~macro-message "Oh, big deal!"]
       ~@stuff-to-do
       (println "I still need to say: " ~macro-message))))

`(blarg# blarg#)
`(let [name# "Larry Potter"] name#)

(defmacro report
  [to-try]
  `(if ~to-try
     (println (quote ~to-try) "was successful:" ~to-try)
     (println (quote ~to-try) "was not successful:" ~to-try)))
     
;; Thread/sleep takes a number of milliseconds to sleep for
(report (do (Thread/sleep 1000) (+ 1 1)))

(defmacro report
  [to-try]
  `(let [result# ~to-try]
     (if result#
       (println (quote ~to-try) "was successful:" result#)
       (println (quote ~to-try) "was not successful:" result#))))

