(ns uk.rogerthemoose.timelines.layout.elements.label-test
  (:require [clojure.test :refer :all]
            [tick.core :as t]
            [uk.rogerthemoose.timelines.layout.element :refer [bounds-of-element render-element]]
            [uk.rogerthemoose.timelines.layout.elements.label :refer [label]]))

(deftest label-element

  (let [base {:at "2019-09-01" :line 1 :text "LABEL"}
        base-bounds {:from-line 1 :to-line 1 :from-date (t/date "2019-09-01") :to-date (t/date "2019-09-01")}
        top-left (label (merge base {:v-align :top :align :end}))
        top-middle (label (merge base {:v-align :top :align :middle}))
        top-right (label (merge base {:v-align :top :align :start}))
        bottom-left (label (merge base {:v-align :bottom :align :end}))
        bottom-middle (label (merge base {:v-align :bottom :align :middle}))
        bottom-right (label (merge base {:v-align :bottom :align :start}))]

    (testing "has bounds determined by the label position"

      (is (= (merge base-bounds {:left 30 :right 0 :top 20 :bottom 0})
             (bounds-of-element top-left)))
      (is (= (merge base-bounds {:left 30 :right 0 :top 0 :bottom 20})
             (bounds-of-element bottom-left)))

      (is (= (merge base-bounds {:left 15 :right 15 :top 20 :bottom 0})
             (bounds-of-element top-middle)))
      (is (= (merge base-bounds {:left 15 :right 15 :top 0 :bottom 20})
             (bounds-of-element bottom-middle)))

      (is (= (merge base-bounds {:left 0 :right 30 :top 20 :bottom 0})
             (bounds-of-element top-right)))
      (is (= (merge base-bounds {:left 0 :right 30 :top 0 :bottom 20})
             (bounds-of-element bottom-right))))))
