(ns uk.rogerthemoose.timelines.layout.elements.x-spacer
  (:require [tick.core :as t]
            [uk.rogerthemoose.timelines.specs :as s]
            [uk.rogerthemoose.timelines.layout.element :refer [bounds-of-element render-element xy]]))

(defn x-spacer [{:keys [line from to] :as m}]
  {:pre  [(s/check ::s/x-spacer m)]
   :post [(s/check ::s/element %)]}
  {:element   :x-spacer
   :from-date (t/date from)
   :to-date   (t/date to)
   :line      line})

(defmethod bounds-of-element :x-spacer
  [{:keys [line from-date to-date]}]
  {:post [(s/check ::s/bounds %)]}
  {:from-date from-date
   :to-date   (t/>> to-date (t/new-period 1 :days))
   :from-line line
   :to-line   line})

(defmethod render-element :x-spacer [_ _])