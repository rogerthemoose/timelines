(ns uk.rogerthemoose.timelines.layout.element-test
  (:require [clojure.test :refer :all]
            [tick.alpha.api :as t]
            [uk.rogerthemoose.timelines.api :refer [timeline point label]]
            [uk.rogerthemoose.timelines.layout.element :as e]))

(deftest groups-of-elements-occupy-the-aggregate-of-their-bounds

  (testing "single element has bounds"

    (is (= (e/bounds-containing-all-elements [(timeline {:line 1 :from "2019-09-01" :to "2020-09-01"})])

           {:from-date (t/date "2019-09-01")
            :to-date   (t/date "2020-09-01")
            :from-line 1
            :to-line   1
            :left      0
            :right     0
            :top       0
            :bottom    0})))

  (testing "multiple elements have bounds that contain all of the elements"

    (is (= (e/bounds-containing-all-elements [(timeline {:line 2 :from "2019-09-01" :to "2020-09-01"})
                                              (point {:line 1 :at "2019-08-01"})
                                              (point {:line 3 :at "2022-08-01"})])

           {:from-date (t/date "2019-08-01")
            :to-date   (t/date "2022-08-01")
            :from-line 1
            :to-line   3
            :left      4
            :right     4
            :top       4
            :bottom    4}))

    (is (= (e/bounds-containing-all-elements [(timeline {:line 1 :from "2019-09-01" :to "2020-09-01"})
                                              (timeline {:line 2 :from "2020-01-01" :to "2021-01-01"})])
           {:from-date (t/date "2019-09-01")
            :to-date   (t/date "2021-01-01")
            :from-line 1
            :to-line   2
            :left      0
            :right     0
            :top       0
            :bottom    0})))

  (testing "elements that define additional padding like a labels"
    (is (= (e/bounds-containing-all-elements [(label {:text "LABEL" :at "2019-09-01" :line 1 :v-align :top :align :start})])
           {:from-date (t/date "2019-09-01")
            :to-date   (t/date "2019-09-01")
            :from-line 1
            :to-line   1
            :left      0
            :right     30
            :top       20
            :bottom    0}))

    (is (= (e/bounds-containing-all-elements [(label {:text "LABEL" :at "2019-09-01" :line 1 :v-align :top :align :start})
                                              (label {:text "LABEL" :at "2019-09-01" :line 1 :v-align :bottom :align :end})])
           {:from-date (t/date "2019-09-01")
            :to-date   (t/date "2019-09-01")
            :from-line 1
            :to-line   1
            :left      30
            :right     30
            :top       20
            :bottom    20}))))