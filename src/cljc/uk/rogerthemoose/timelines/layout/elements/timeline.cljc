(ns uk.rogerthemoose.timelines.layout.elements.timeline
  (:require [tick.alpha.api :as t]
            [uk.rogerthemoose.timelines.layout.element :refer [bounds-of-element render-element xy]]))

(defn timeline [{:keys [line from to]}]
  {:element :timeline
   :line    line
   :from    (t/date from)
   :to      (t/date to)})

(defmethod bounds-of-element :timeline
  [{:keys [from to line]}]
  {:from-date from
   :to-date   to
   :from-line line
   :to-line   line})

(defmethod render-element :timeline
  [c-fn {:keys [from to line]}]
  (let [[x1 y1] (xy (c-fn line from))
        [x2 y2] (xy (c-fn line to))]
    [:line.timeline {:x1 x1
                     :x2 x2
                     :y1 y1
                     :y2 y2}]))
