(ns lingo.core-test
  (:require [clojure.test :refer :all]
            [lingo.core :as lingo]))

(def generator (lingo/make-gen))

(deftest clause-generation
  (testing "simple clause generation"
    (let [dog-and-rabbit {:> :clause
                          :+ [{:> :subject :+ "dog"}
                              {:> :verb :+ "chase"}
                              {:> :object :+ "rabbit"}]}]
      (is (= ((:! generator) dog-and-rabbit)
             "Dog chases rabbit."))

      (let [dog-and-rabbit (assoc dog-and-rabbit :* {:feature [:why :?]})]
        (is (= ((:! generator) dog-and-rabbit)
               "Why does dog chase rabbit?")))

      (let [subject (assoc (first (:+ dog-and-rabbit))
                           :* [:specifier "the"])
            dog-and-rabbit (assoc dog-and-rabbit
                                  :+
                                  (concat [subject] (rest (dog-and-rabbit :+))))]
        (is (= ((:! generator) dog-and-rabbit)
               "The dog chases rabbit."))))))
