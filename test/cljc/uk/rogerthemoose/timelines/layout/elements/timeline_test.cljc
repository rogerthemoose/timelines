(ns uk.rogerthemoose.timelines.layout.elements.timeline_test
  (:require [clojure.test :refer :all]
            [tick.core :as t]
            [uk.rogerthemoose.timelines.layout.element :refer [bounds-of-element render-element]]
            [uk.rogerthemoose.timelines.layout.elements.timeline :refer [timeline]]))

(def ^:private c-fn (fn [line date] {:x (t/days (t/between (t/midnight (t/date "2019-09-01"))
                                                           (t/midnight date))) 0 :y line}))

(deftest timeline-element

  (let [t (timeline {:line 1 :from "2019-08-01" :to "2020-08-31"})]

    (testing "has bounds"
      (is (= {:from-date (t/date "2019-08-01")
              :to-date   (t/date "2020-09-01")
              :from-line 1
              :to-line   1}
             (bounds-of-element t))))

    (testing "renders to svg"
      (is (= [:line.timeline {:x1 0 :x2 31 :y1 1 :y2 1}]
             (render-element c-fn t))))))