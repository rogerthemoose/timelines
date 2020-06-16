(ns uk.rogerthemoose.timelines.layout.elements.point
  (:require [tick.alpha.api :as t]
            [uk.rogerthemoose.timelines.layout.element :refer [bounds-of-element render-element xy]]))

(defn point [{:keys [line at]}]
  {:element  :point
   :line     line
   :at       (t/date at)})

(defmethod bounds-of-element :point
  [{:keys [at line]}]
  {:from-date at
   :to-date   at
   :from-line line
   :to-line   line})

(defmethod render-element :point
  [c-fn {:keys [at line]}]
  (let [[x y] (xy (c-fn line at))]
    [:circle.point {:cx x :cy y :r 5}]))





