(ns uk.rogerthemoose.timelines.layout.elements.group-test
  (:require [clojure.test :refer :all]
            [tick.alpha.api :as t]
            [uk.rogerthemoose.timelines.layout.element :refer [bounds-of-element render-element]]
            [uk.rogerthemoose.timelines.layout.elements.point :refer [point]]
            [uk.rogerthemoose.timelines.layout.elements.group :refer [group]]))

(def ^:private c-fn (fn [line date] {:x (str date) :y line}))

(deftest group-element

  (let [p1 (point {:line 1 :at "2019-09-01"})
        p2 (point {:line 2 :at "2019-10-01"})
        g (group "a.b.c" [p1 p2])]

    (testing "has bounds of it's contents"
      (is (= {:from-date (t/date "2019-09-01")
              :to-date   (t/date "2019-10-01")
              :from-line 1
              :to-line   2
              :top       0
              :bottom    0
              :left      0
              :right     0}
             (bounds-of-element g))))

    (testing "renders to svg"
      (is (= [:g.a.b.c
              [:circle.point {:cx "2019-09-01" :cy 1 :r 3}]
              [:circle.point {:cx "2019-10-01" :cy 2 :r 3}]]
             (render-element c-fn g)))))

  (testing "classes can be specified in a few different ways"
    (is (= [:g.a.b.c]
           (render-element c-fn (group "a.b.c" nil))))

    (is (= [:g.a.b.c]
           (render-element c-fn (group :.a.b.c nil))))

    (is (= [:g.a.b.c]
           (render-element c-fn (group "a b c" nil))))))