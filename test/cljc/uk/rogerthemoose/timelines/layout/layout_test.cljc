(ns uk.rogerthemoose.timelines.layout.layout-test
  (:require [clojure.test :refer :all]
            [tick.alpha.api :as t]
            [uk.rogerthemoose.timelines.api :refer [point timeline x-spacer]]
            [uk.rogerthemoose.timelines.layout.layout :as l]))

(deftest determining-bounds-of-elements

  (testing "canvas size to encompass elements is a function of padding and bounds"

    (is (= (l/view-box-for [(point {:line 1 :at "2019-08-01"})]
                           {:line-height 0
                            :y-top       0
                            :y-bottom    0
                            :x-padding   0})
           {:x      0
            :y      0
            :width  13
            :height 13}))

    (is (= (l/view-box-for [(point {:line 1 :at "2019-08-01"})
                            (point {:line 4 :at "2019-08-10"})]
                           {:line-height 30
                            :y-top       10
                            :y-bottom    10
                            :x-padding   20})
           {:x      0
            :y      0
            :width  62
            :height 158}))

    (is (= (l/view-box-for [(timeline {:line 1 :from "2019-09-01" :to "2020-09-01"})
                            (timeline {:line 2 :from "2020-01-01" :to "2021-01-01"})]
                           {:line-height 20
                            :x-padding   0
                            :y-top       0
                            :y-bottom    0})
           {:x      0
            :y      0
            :width  489
            :height 31}))))

(deftest coordinates-of-a-date-on-a-line

  (let [bounds {:from-date (t/date "2019-09-01")
                :to-date   (t/date "2020-09-01")
                :from-line 1
                :to-line   3
                :top       0
                :bottom    0
                :left      0
                :right     0}
        padding {:line-height 30
                 :y-top       10
                 :y-bottom    0
                 :x-padding   20}
        coords-without-padding (l/coordinator-for bounds)
        coords-with-padding (l/coordinator-for bounds padding)]

    (testing "it depends on the bounds"

      (testing "top-left"
        (is (= (coords-without-padding 1 (t/date "2019-09-01"))
               {:x 0 :y 0}))

        (is (= (coords-with-padding 1 (t/date "2019-09-01"))
               {:x 20 :y 35})))

      (testing "top-right"

        (is (= (coords-without-padding 1 (t/date "2020-09-01"))
               {:x 366 :y 0}))

        (is (= (coords-with-padding 1 (t/date "2020-09-01"))
               {:x 386 :y 35})))

      (testing "bottom left"
        (is (= (coords-without-padding 3 (t/date "2019-09-01"))
               {:x 0 :y 2}))

        (is (= (coords-with-padding 3 (t/date "2019-09-01"))
               {:x 20 :y 95})))

      (testing "bottom right"

        (is (= (coords-without-padding 3 (t/date "2020-09-01"))
               {:x 366 :y 2}))

        (is (= (coords-with-padding 3 (t/date "2020-09-01"))
               {:x 386 :y 95}))))))

(deftest can-layout-various-abstractions

  (testing "a single timeline element"
    (is (= (l/layout [(timeline {:line 1 :from "2019-09-01" :to "2020-09-01"})]
                     {:line-height 0 :x-padding 0 :y-top 0 :y-bottom 0})

           [:svg {:viewBox             "0 0 367 1"
                  :preserveAspectRatio "xMidYMid meet"}
            [:line.timeline {:x1 0 :x2 366 :y1 0 :y2 0}]])))

  (testing "a couple of timelines with an overlap"
    (is (= (l/layout [(timeline {:line 1 :from "2019-09-01" :to "2020-09-01"})
                      (timeline {:line 2 :from "2020-01-01" :to "2021-01-01"})]
                     {:line-height 20 :x-padding 0 :y-top 0 :y-bottom 0})

           [:svg {:viewBox             "0 0 489 31"
                  :preserveAspectRatio "xMidYMid meet"}
            [:line.timeline {:x1 0 :x2 366 :y1 10 :y2 10}]
            [:line.timeline {:x1 122 :x2 488 :y1 30 :y2 30}]])))

  (testing "a non rendering x-spacer can be used to force layouts onto the same horizontal scale"
    (let [common-x-spacer (x-spacer {:line 1 :from "2019-09-01" :to "2021-01-01"})
          common-options {:line-height 20 :x-padding 0 :y-top 0 :y-bottom 0}
          layout-1 (l/layout [common-x-spacer
                              (timeline {:line 1 :from "2020-01-01" :to "2021-01-01"})]
                             common-options)
          layout-2 (l/layout [common-x-spacer
                              (timeline {:line 1 :from "2019-09-01" :to "2020-09-01"})]
                             common-options)]
      (is (= layout-1
             [:svg {:viewBox             "0 0 489 11"
                    :preserveAspectRatio "xMidYMid meet"}
              [:line.timeline {:x1 122 :x2 488 :y1 10 :y2 10}]]))

      (is (= layout-2
             [:svg {:viewBox             "0 0 489 11"
                    :preserveAspectRatio "xMidYMid meet"}
              [:line.timeline {:x1 0 :x2 366 :y1 10 :y2 10}]])))))
