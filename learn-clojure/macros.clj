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

