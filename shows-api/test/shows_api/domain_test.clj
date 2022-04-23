(ns shows-api.domain-test

  (:require [clojure.test :refer :all]

            [shows-api.domain :refer :all]))

(deftest a-test

  (testing "has some default shows"

    (is (not (empty? @shows)))))



