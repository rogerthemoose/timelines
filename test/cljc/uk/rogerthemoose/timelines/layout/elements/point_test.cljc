(ns uk.rogerthemoose.timelines.layout.elements.point-test
  (:require [clojure.test :refer :all]
            [tick.core :as t]
            [uk.rogerthemoose.timelines.layout.element :refer [bounds-of-element render-element]]
            [uk.rogerthemoose.timelines.layout.elements.point :refer [point]]))

(def ^:private c-fn (fn [line date] {:x (str date) :y line}))

(deftest point-element

  (let [p (point {:line 1 :at "2019-09-01" :radius 3})
        larger-p (point {:line 1 :at "2019-09-01" :radius 10})]

    (testing "has bounds"
      (is (= {:from-date (t/date "2019-09-01")
              :to-date   (t/date "2019-09-01")
              :from-line 1
              :to-line   1
              :left      4
              :right     4
              :top       4
              :bottom    4}
             (bounds-of-element p))
          )

      (is (= {:from-date (t/date "2019-09-01")
              :to-date   (t/date "2019-09-01")
              :from-line 1
              :to-line   1
              :left      11
              :right     11
              :top       11
              :bottom    11}
             (bounds-of-element larger-p))
          )
      )

    (testing "renders to svg"
      (is (= [:circle.point {:cx "2019-09-01" :cy 1 :r 3}]
             (render-element c-fn p))))))

