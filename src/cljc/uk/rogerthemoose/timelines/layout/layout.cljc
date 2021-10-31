(ns uk.rogerthemoose.timelines.layout.layout
  (:require [tick.core :as t]
            [uk.rogerthemoose.timelines.layout.element :as element :refer [xy]]
            [uk.rogerthemoose.timelines.layout.elements.x-spacer :refer [x-spacer]]))

(defn coordinator-for
  ([bounds]
   (coordinator-for bounds {:line-height 0 :x-padding 0 :y-top 0 :y-bottom 0}))
  ([{:keys [from-date from-line top left]} {:keys [line-height x-padding y-top]}]
   (let [line-midpoint-start (+ y-top (int (/ line-height 2)))]
     (fn [line date]
       (let [line-number (- line from-line)]
         {:x (+ x-padding
                left
                (t/days (t/between (t/midnight from-date)
                                   (t/midnight date))))

          :y (if (> line-height 0)
               (+ y-top top line-midpoint-start (* line-number line-height))
               (+ y-top top line-number))})))))

(defn view-box-for [elements {:keys [x-padding y-top y-bottom] :as padding}]
  (let [{:keys [to-date to-line top bottom left right] :as bounds} (element/bounds-containing-all-elements elements)
        coordinator (coordinator-for bounds padding)
        [x y] (xy (coordinator to-line to-date))]
    {:x      0
     :y      0
     :width  (inc (+ x x-padding left right))
     :height (inc (+ y y-top y-bottom top bottom))}))

(defn x-spacer-for [elements]
  (let [{:keys [from-date to-date from-line]} (element/bounds-containing-all-elements elements)]
    (x-spacer {:from from-date :to to-date :line from-line})))

(defn layout [elements {:keys [render-bounds?] :as options}]
  (let [{:keys [x y width height]} (view-box-for elements options)]
    (let [coordinator (coordinator-for (element/bounds-containing-all-elements elements) options)
          svg-options {:viewBox             (str x " " y " " width " " height)
                       :preserveAspectRatio "xMidYMid meet"}
          element-svg (remove nil? (map (partial element/render-element coordinator) elements))
          element-bounds-svg (when render-bounds? (remove nil? (map (partial element/render-bounds coordinator) elements)))]
      `[:svg ~svg-options
        ~@element-svg
        ~@element-bounds-svg])))
