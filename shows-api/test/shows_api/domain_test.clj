;; (ns shows-api.domain-test
;;   (:require [clojure.test :refer :all]
;;             [shows-api.domain :as domain]))

;; ; todo: find a way to mock methods

;; (deftest stored-shows-tests
;;   (testing "has some default shows"
;;     (is (not (empty? @shows))))
;;   (testing "shows have a id"
;;     (is (contains? (first @shows) :id)))
;; )

;; (deftest retrieve-shows
;;   (testing "returns all shows"
;;     (is (not (empty? (domain/get-all-shows))))))



