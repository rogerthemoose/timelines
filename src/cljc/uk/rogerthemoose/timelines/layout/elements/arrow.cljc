(ns uk.rogerthemoose.timelines.layout.elements.arrow
  (:require [tick.alpha.api :as t]
            [uk.rogerthemoose.timelines.layout.element :refer [bounds-of-element render-element xy]]))

(defn arrow [{:keys [at from-line to-line]}]
  {:element   :arrow
   :at        (t/date at)
   :from-line from-line
   :to-line   to-line})

(defmethod bounds-of-element :arrow
  [{:keys [at from-line to-line]}]
  {:from-date at
   :to-date   at
   :from-line from-line
   :to-line   to-line})

(defmethod render-element :arrow
  [c-fn {:keys [at from-line to-line]}]
  (let [[x1 y1] (xy (c-fn from-line at))
        [x2 y2] (xy (c-fn to-line at))
        y-op (if (> from-line to-line) + -)]
    [:g.element.arrow
     [:line.connector {:x1 x1
                       :y1 y1
                       :x2 x2
                       :y2 y2}]
     [:line.head {:x1 (+ x1 4)
                  :y1 (y-op y2 6)
                  :x2 x2
                  :y2 y2}]
     [:line.head {:x1 (- x1 4)
                  :y1 (y-op y2 6)
                  :x2 x2
                  :y2 y2}]]))