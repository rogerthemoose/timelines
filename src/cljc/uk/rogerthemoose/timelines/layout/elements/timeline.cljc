(ns uk.rogerthemoose.timelines.layout.elements.timeline
  (:require [tick.alpha.api :as t]
            [uk.rogerthemoose.timelines.specs :as s]
            [uk.rogerthemoose.timelines.layout.element :refer [bounds-of-element render-element xy]]))

(defn timeline [{:keys [line from to] :as m}]
  {:pre [(s/check ::s/timeline m)]
   :post [(s/check ::s/element %)]}
  {:element   :timeline
   :line      line
   :from-date (t/date from)
   :to-date   (t/date to)})

(defmethod bounds-of-element :timeline
  [{:keys [from-date to-date line]}]
  {:post [(s/check ::s/bounds %)]}
  {:from-date from-date
   :to-date   to-date
   :from-line line
   :to-line   line})

(defmethod render-element :timeline
  [c-fn {:keys [from-date to-date line]}]
  (let [[x1 y1] (xy (c-fn line from-date))
        [x2 y2] (xy (c-fn line to-date))]
    [:line.timeline {:x1 x1
                     :x2 x2
                     :y1 y1
                     :y2 y2}]))
