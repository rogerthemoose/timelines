(ns uk.rogerthemoose.timelines.layout.elements.event
  (:require [tick.alpha.api :as t]
            [tick.locale-en-us]
            [uk.rogerthemoose.timelines.specs :as s]
            [uk.rogerthemoose.timelines.layout.element :as e :refer [bounds-of-element render-element render-bounds xy]]
            [uk.rogerthemoose.timelines.layout.elements.point :refer [point]]
            [uk.rogerthemoose.timelines.layout.elements.label :refer [label]]))

(defn event [{:keys [line at format v-align align] :as m}]
  {:pre [(s/check ::s/event m)]
   :post [(s/check ::s/element %)]}
  {:element     :event
   :composed-of [(point {:at at :line line})
                 (label {:v-align (or v-align :bottom)
                         :align   (or align :middle)
                         :text    (t/format (or format "yyyy-MM-dd") (t/date at))
                         :line    line
                         :at      at})]})

(defmethod bounds-of-element :event
  [{:keys [composed-of]}]
  {:post [(s/check ::s/bounds %)]}
  (e/bounds-containing-all-elements composed-of))

(defmethod render-element :event
  [c-fn {:keys [composed-of]}]
  (let [rendered (doall (map (partial render-element c-fn) composed-of))]
    `[:g ~@rendered]))

(defmethod render-bounds :event
  [c-fn {:keys [composed-of]}]
  (let [rendered (doall (map (partial render-bounds c-fn) composed-of))]
    `[:g ~@rendered]))
