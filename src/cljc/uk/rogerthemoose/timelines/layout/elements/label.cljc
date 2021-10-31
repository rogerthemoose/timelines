(ns uk.rogerthemoose.timelines.layout.elements.label
  (:require [tick.core :as t]
            [uk.rogerthemoose.timelines.specs :as s]
            [uk.rogerthemoose.timelines.layout.element :refer [bounds-of-element render-element xy]]))

(defn label [{:keys [text at line v-align align] :as m}]
  {:pre [(s/check ::s/label m)]
   :post [(s/check ::s/element %)]}
  {:element :label
   :text    text
   :at      (t/date at)
   :line    line
   :align   (or align :middle)
   :v-align (or v-align :bottom)})

(defmethod bounds-of-element :label
  [{:keys [at line v-align align text]}]
  {:post [(s/check ::s/bounds %)]}
  (let [width (int (Math/ceil (* 6 (count text))))
        left (case align :start 0 :middle (int (/ width 2.0)) :end width)
        right (case align :start width :middle (int (/ width 2.0)) :end 0)
        top (case v-align :top 20 :middle 10 :bottom 0)
        bottom (case v-align :top 0 :middle 10 :bottom 20)]
    {:from-date at
     :to-date   at
     :from-line line
     :to-line   line
     :left      left
     :right     right
     :top       top
     :bottom    bottom}))

(defmethod render-element :label
  [c-fn {:keys [at line text v-align align]}]
  (let [[x y] (xy (c-fn line at))
        y-op (case v-align :top #(- % 7) :bottom #(+ % 12) #(+ % 2))]
    [:text.label {:x x :y (y-op y) :text-anchor align :font-size "10px" :font-family :monospace} text]))
