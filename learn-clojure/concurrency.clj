(ns learn-clojure.concurrency)

(future (Thread/sleep 4000)
        (println "I'll print after 4 seconds"))
(println "I'll print immediately")

(let [result (future (println "this prints once")
                     (+ 1 1))]
  (println "deref: " (deref result))
  (println "@: " @result))

(let [result (future (Thread/sleep 3000)
                     (+ 1 1))]
  (println "The result is: " @result)
  (println "It will be at least 3 seconds before I print"))

(deref (future (Thread/sleep 1000) 0) 10 5)

(realized? (future (Thread/sleep 1000)))
(let [f (future)]
  @f
  (realized? f))

(def jackson-5-delay
  (delay (let [message "Just call my name and I'll be there"]
           (println "First deref:" message)
           message)))
(force jackson-5-delay)
@jackson-5-delay


(def gimli-headshots ["serious.jpg" "fun.jpg" "playful.jpg"])
(defn email-user
  [email-address]
  (println "Sending headshot notification to" email-address))
(defn upload-document
  "Needs to be implemented"
  [headshot]
  true)
(let [notify (delay (email-user "and-my-axe@gmail.com"))]
  (doseq [headshot gimli-headshots]
    (future (upload-document headshot)
            (force notify))))


(def my-promise (promise))
(deliver my-promise (+ 1 2))
@my-promise


(def yak-butter-international
  {:store "Yak Butter International"
    :price 90
    :smoothness 90})
(def butter-than-nothing
  {:store "Butter Than Nothing"
   :price 150
   :smoothness 83})
;; This is the butter that meets our requirements
(def baby-got-yak
  {:store "Baby Got Yak"
   :price 94
   :smoothness 99})

(defn mock-api-call
  [result]
  (Thread/sleep 1000)
  result)

(defn satisfactory?
  "If the butter meets our criteria, return the butter, else return false"
  [butter]
  (and (<= (:price butter) 100)
       (>= (:smoothness butter) 97)
       butter))

(time (some (comp satisfactory? mock-api-call)
            [yak-butter-international butter-than-nothing baby-got-yak]))

(time
 (let [butter-promise (promise)]
   (doseq [butter [yak-butter-international butter-than-nothing baby-got-yak]]
     (future (if-let [satisfactory-butter (satisfactory? (mock-api-call butter))]
               (deliver butter-promise satisfactory-butter))))
   (println "And the winner is:" @butter-promise)))

(let [p (promise)]
  (deref p 100 "timed out"))

(let [ferengi-wisdom-promise (promise)]
  (future (println "Here's some Ferengi wisdom:" @ferengi-wisdom-promise))
  (Thread/sleep 100)
  (deliver ferengi-wisdom-promise "Whisper your way to success."))


(def fred (atom {:cuddle-hunger-level 0
                 :percent-deteriorated 0}))

(let [zombie-state @fred]
  (if (>= (:percent-deteriorated zombie-state) 50)
    (future (println (:cuddle-hunger-level zombie-state)))))

(swap! fred
       (fn [current-state]
         (merge-with + current-state {:cuddle-hunger-level 1})))
(swap! fred
       (fn [current-state]
         (merge-with + current-state {:cuddle-hunger-level 1
                                      :percent-deteriorated 1})))
(swap! fred update-in [:cuddle-hunger-level] + 10)

(let [num (atom 1)
      s1 @num]
  (swap! num inc)
  (println "State 1:" s1)
  (println "Current state:" @num))

(reset! fred {:cuddle-hunger-level 0
              :percent-deteriorated 0})


(defn shuffle-speed
  [zombie]
  (* (:cuddle-hunger-level zombie)
     (- 100 (:percent-deteriorated zombie))))

(defn shuffle-alert
  [key watched old-state new-state]
  (let [sph (shuffle-speed new-state)]
    (if (> sph 5000)
      (do
        (println "Run, you fool!")
        (println "The zombie's SPH is now " sph)
        (println "This message brought to your courtesy of " key))
      (do
        (println "All's well with " key)
        (println "Cuddle hunger: " (:cuddle-hunger-level new-state))
        (println "Percent deteriorated: " (:percent-deteriorated new-state))
        (println "SPH: " sph)))))

(reset! fred {:cuddle-hunger-level 22
              :percent-deteriorated 2})
(add-watch fred :fred-shuffle-alert shuffle-alert) 
(swap! fred update-in [:percent-deteriorated] + 1)


;; (defn percent-deteriorated-validator
;;   [{:keys [percent-deteriorated]}]
;;   (and (>= percent-deteriorated 0)
;;        (<= percent-deteriorated 100)))
(defn percent-deteriorated-validator
  [{:keys [percent-deteriorated]}]
  (or (and (>= percent-deteriorated 0)
           (<= percent-deteriorated 100))
      (throw (IllegalStateException. "That's not mathy!"))))
(def bobby
  (atom
   {:cuddle-hunger-level 0 :percent-deteriorated 0}
    :validator percent-deteriorated-validator))
(swap! bobby update-in [:percent-deteriorated] + 200)


(def sock-varieties
  #{"darned" "argyle" "wool" "horsehair" "mulleted"
    "passive-aggressive" "striped" "polka-dotted"
    "athletic" "business" "power" "invisible" "gollumed"})
(defn sock-count
  [sock-variety count]
  {:variety sock-variety
   :count count})
(defn generate-sock-gnome
  "Create an initial sock gnome state with no socks"
  [name]
  {:name name
   :socks #{}})

(def sock-gnome (ref (generate-sock-gnome "Barumpharumph")))
(def dryer (ref {:name "LG 1337"
                 :socks (set (map #(sock-count % 2) sock-varieties))}))
(:socks @dryer)
(defn steal-sock
  [gnome dryer]
  (dosync
   (when-let [pair (some #(if (= (:count %) 2) %) (:socks @dryer))]
     (let [updated-count (sock-count (:variety pair) 1)]
       (alter gnome update-in [:socks] conj updated-count)
       (alter dryer update-in [:socks] disj pair)
       (alter dryer update-in [:socks] conj updated-count)))))
(steal-sock sock-gnome dryer)
(:socks @sock-gnome)

(def counter (ref 0))
(future
  (dosync
   (alter counter inc)
   (println @counter)
   (Thread/sleep 500)
   (alter counter inc)
   (println @counter)))
(Thread/sleep 250)
(println @counter)

;; safe
(defn sleep-print-update
  [sleep-time thread-name update-fn]
  (fn [state]
    (Thread/sleep sleep-time)
    (println (str thread-name ": " state))
    (update-fn state)))
(def counter (ref 0))
(future (dosync (commute counter (sleep-print-update 100 "Thread A" inc))))
(future (dosync (commute counter (sleep-print-update 150 "Thread B" inc))))

;; unsafe
(def receiver-a (ref #{}))
(def receiver-b (ref #{}))
(def giver (ref #{1}))
(do (future (dosync (let [gift (first @giver)]
                      (Thread/sleep 10)
                      (commute receiver-a conj gift)
                      (commute giver disj gift))))
    (future (dosync (let [gift (first @giver)]
                      (Thread/sleep 50)
                      (commute receiver-b conj gift)
                      (commute giver disj gift)))))
@receiver-a
@receiver-b
@giver


(def ^:dynamic *notification-address* "dobby@elf.org")

(binding [*notification-address* "test@elf.org"]
  *notification-address*)
(binding [*notification-address* "tester-1@elf.org"]
  (println *notification-address*)
  (binding [*notification-address* "tester-2@elf.org"]
    (println *notification-address*))
  (println *notification-address*))

(defn notify
  [message]
  (str "TO: " *notification-address* "\n"
       "MESSAGE: " message))
(notify "I fell.")
(binding [*notification-address* "test@elf.org"]
  (notify "test!"))

(binding [*out* (clojure.java.io/writer "print-output")]
  (println "A man who carries a cat by the tail learns 
something he can learn in no other way.
-- Mark Twain"))
(slurp "print-output")

(println ["Print" "all" "the" "things!"])
(binding [*print-length* 1]
  (println ["Print" "just" "one!"]))

(def ^:dynamic *troll-thought* nil)
(defn troll-riddle
  [your-answer]
  (let [number "man meat"]
     (when (thread-bound? #'*troll-thought*)
       (set! *troll-thought* number))
    (if (= number your-answer)
      "TROLL: You can cross the bridge!"
      "TROLL: Time to eat you, succulent human!")))
(binding [*troll-thought* nil]
  (println (troll-riddle 2))
  (println "SUCCULENT HUMAN: Oooooh! The answer was" *troll-thought*))

(.write *out* "prints to repl")
(.start (Thread. #(.write *out* "prints to standard out")))

(let [out *out*]
  (.start
   (Thread. #(binding [*out* out]
               (.write *out* "prints to repl from thread")))))
(.start (Thread. (bound-fn [] (.write *out* "prints to repl from thread"))))

(def power-source "hair")
(alter-var-root #'power-source (fn [_] "7-eleven parking lot"))
(with-redefs [*out* *out*]
        (doto (Thread. #(println "with redefs allows me to show up in the REPL"))
          .start
          .join))


(defn always-1
  []
  1)
(take 5 (repeatedly always-1))
(take 5 (repeatedly (partial rand-int 10)))

(def alphabet-length 26)
(def letters (mapv (comp str char (partial + 65)) (range alphabet-length)))
(defn random-string
  "Returns a random string of specified length"
  [length]
  (apply str (take length (repeatedly #(rand-nth letters)))))        
(defn random-string-list
  [list-length string-length]
  (doall (take list-length (repeatedly (partial random-string string-length)))))
(def orc-names (random-string-list 3000 7000))

(time (dorun (map clojure.string/lower-case orc-names)))
(time (dorun (pmap clojure.string/lower-case orc-names)))

;; slower due to overhead
(def orc-name-abbrevs (random-string-list 20000 300))
(time (dorun (map clojure.string/lower-case orc-name-abbrevs)))
(time (dorun (pmap clojure.string/lower-case orc-name-abbrevs)))

(def numbers [1 2 3 4 5 6 7 8 9 10])
(partition-all 3 numbers)

(pmap inc numbers)
(pmap (fn [number-group] (doall (map inc number-group)))
      (partition-all 3 numbers))
(apply concat
       (pmap (fn [number-group] (doall (map inc number-group)))
             (partition-all 3 numbers)))
(time
 (dorun
  (apply concat
         (pmap (fn [name] (doall (map clojure.string/lower-case name)))
               (partition-all 1000 orc-name-abbrevs)))))



