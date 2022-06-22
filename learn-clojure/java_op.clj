(ns learn-cloure.java-op)

(.toUpperCase "By Bluebeard's bananas!")
(.indexOf "Let's synergize our bleeding edges" "y") 

(java.lang.Math/abs -3) 
(java.lang.Math/PI) 

(macroexpand-1 '(.toUpperCase "By Bluebeard's bananas!"))
;; (. object-expr-or-classname-symbol method-or-member-symbol optional-args*)

(new String)
(String.)
(String. "To Davey Jones's Locker with ye hardies")


(java.util.Stack.)
(let [stack (java.util.Stack.)] 
  (.push stack "Latest episode of Game of Thrones, ho!")
  stack)

(let [stack (java.util.Stack.)]
  (.push stack "Latest episode of Game of Thrones, ho!")
  (first stack))

(doto (java.util.Stack.)
  (.push "Latest episode of Game of Thrones, ho!")
  (.push "Whoops, I meant 'Land, ho!'"))

(macroexpand-1
 '(doto (java.util.Stack.)
    (.push "Latest episode of Game of Thrones, ho!")
    (.push "Whoops, I meant 'Land, ho!'")))


(import java.util.Stack)
(import [java.util Date Stack]
        [java.net Proxy URI])

(Date.)

(ns pirate.talk
  (:import [java.util Date Stack]
           [java.net Proxy URI]))

(System/getenv)
(System/getProperty "user.dir")
(System/getProperty "java.version")

;; #inst "2016-09-19T20:40:02.733-00:00"
;; clj-time

(let [file (java.io.File. "/")]
   (println (.exists file))  
   (println (.canWrite file))
   (println (.getPath file))) 

(spit "/tmp/hercules-todo-list"
"- kill dat lion brov
- chop up what nasty multi-headed snake thing")

(slurp "/tmp/hercules-todo-list")

(let [s (java.io.StringWriter.)]
  (spit s "- capture cerynian hind like for real")
  (.toString s))

(let [s (java.io.StringReader. "- get erymanthian pig what with the tusks")]
  (slurp s))

(with-open [todo-list-rdr (clojure.java.io/reader "/tmp/hercules-todo-list")]
  (println (first (line-seq todo-list-rdr))))

