(ns uk.rogerthemoose.timelines.layout.elements.box
  (:require [tick.alpha.api :as t]
            [uk.rogerthemoose.timelines.specs :as s]
            [uk.rogerthemoose.timelines.layout.element :refer [bounds-of-element render-element xy]]))

(defn box [{:keys [line at width height] :as m}]
  {:pre  [(s/check ::s/box m)]
   :post [(s/check ::s/element %)]}
  {:element :box
   :line    line
   :at      (t/date at)
   :width   (or width 2)
   :height  (or height 8)})

(defmethod bounds-of-element :box
  [{:keys [at line width height]}]
  {:post [(s/check ::s/bounds %)]}
  (let [margin-x (int (/ width 2.0))
        margin-y (int (/ height 2.0))]
    {:from-date at
     :to-date   at
     :from-line line
     :to-line   line
     :top       margin-y
     :bottom    margin-y
     :left      margin-x
     :right     margin-x}))

(defmethod render-element :box
  [c-fn {:keys [line at width height] :as this}]
  (let [{:keys [top left]} (bounds-of-element this)
        [x y] (xy (c-fn line at))
        top (- y top)
        left (- x left)]
    [:rect.box {:x left :y top :width width :height height}]))




