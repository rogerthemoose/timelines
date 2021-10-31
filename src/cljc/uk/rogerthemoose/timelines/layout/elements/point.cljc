(ns uk.rogerthemoose.timelines.layout.elements.point
  (:require [tick.core :as t]
            [uk.rogerthemoose.timelines.specs :as s]
            [uk.rogerthemoose.timelines.layout.element :refer [bounds-of-element render-element xy]]))

(defn point [{:keys [line at radius] :as m}]
  {:pre  [(s/check ::s/point m)]
   :post [(s/check ::s/element %)]}
  {:element :point
   :line    line
   :at      (t/date at)
   :radius  (or radius 3)})

(defmethod bounds-of-element :point
  [{:keys [at line radius]}]
  {:post [(s/check ::s/bounds %)]}
  (let [margin (inc radius)]
    {:from-date at
     :to-date   at
     :from-line line
     :to-line   line
     :top       margin
     :bottom    margin
     :left      margin
     :right     margin}))

(defmethod render-element :point
  [c-fn {:keys [at line radius]}]
  (let [[x y] (xy (c-fn line at))]
    [:circle.point {:cx x :cy y :r radius}]))





