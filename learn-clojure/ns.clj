(ns learn-clojure.ns)

(def great-books ["East of Eden" "The Glass Bead Game"])

;; current ns
(ns-name *ns*)

;; get the current namespace refs
(ns-interns *ns*)

(get (ns-interns *ns*) 'great-books)

(deref #'learn-clojure.ns/great-books)

(def great-books ["The Power of Bees" "Journey to Upstairs"])


(create-ns 'cheese.taxonomy)
(ns-name (create-ns 'cheese.taxonomy))

(in-ns 'cheese.analysis)
(ns-name *ns*)

(clojure.core/refer 'cheese.taxonomy :rename {'bries 'yummy-bries} :exclude ['bries] :only ['bries])

(defn- private-function
  "Just an example function that does nothing"
  [])

(clojure.core/alias 'taxonomy 'cheese.taxonomy)

(require '[the-divine-cheese-code.visualization.svg :as svg])
(require 'the-divine-cheese-code.visualization.svg)
;; ==
(alias 'svg 'the-divine-cheese-code.visualization.svg)

(require 'the-divine-cheese-code.visualization.svg)
(refer 'the-divine-cheese-code.visualization.svg)
;; ==
(use 'the-divine-cheese-code.visualization.svg)

(require 'the-divine-cheese-code.visualization.svg)
(refer 'the-divine-cheese-code.visualization.svg)
(alias 'svg 'the-divine-cheese-code.visualization.svg)
;; ==
(use '[the-divine-cheese-code.visualization.svg :as svg])

(require 'the-divine-cheese-code.visualization.svg)
(refer 'the-divine-cheese-code.visualization.svg :as :only ['points])
;; ==
(use '[the-divine-cheese-code.visualization.svg :as svg :only [points]])
(refer 'the-divine-cheese-code.visualization.svg :as :only ['points])




