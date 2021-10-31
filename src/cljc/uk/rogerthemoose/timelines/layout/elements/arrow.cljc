(ns uk.rogerthemoose.timelines.layout.elements.arrow
  (:require [tick.core :as t]
            [uk.rogerthemoose.timelines.specs :as s]
            [uk.rogerthemoose.timelines.layout.element :refer [bounds-of-element render-element xy]]))

(defn arrow [{:keys [at from-line to-line margin margin-top margin-bottom] :as m}]
  {:pre  [(s/check ::s/arrow m)]
   :post [(s/check ::s/element %)]}
  {:element       :arrow
   :at            (t/date at)
   :from-line     from-line
   :to-line       to-line
   :margin-top    (or margin-top margin 0)
   :margin-bottom (or margin-bottom margin 0)})

(defmethod bounds-of-element :arrow
  [{:keys [at from-line to-line]}]
  {:post [(s/check ::s/bounds %)]}
  {:from-date at
   :to-date   at
   :from-line from-line
   :to-line   to-line})

(defmethod render-element :arrow
  [c-fn {:keys [at from-line to-line margin-top margin-bottom]}]
  (let [[x1 y1] (xy (c-fn from-line at))
        [x2 y2] (xy (c-fn to-line at))
        pointing-up? (> from-line to-line)
        to-tail (if pointing-up? + -)
        to-head (if pointing-up? - +)
        start-y (to-head y1 margin-bottom)
        end-y (to-tail y2 margin-top)]
    [:g.element.arrow
     [:line.connector {:x1 x1
                       :y1 start-y
                       :x2 x2
                       :y2 end-y}]
     [:line.head {:x1 (+ x1 4)
                  :y1 (to-tail end-y 6)
                  :x2 x2
                  :y2 end-y}]
     [:line.head {:x1 (- x1 4)
                  :y1 (to-tail end-y 6)
                  :x2 x2
                  :y2 end-y}]]))