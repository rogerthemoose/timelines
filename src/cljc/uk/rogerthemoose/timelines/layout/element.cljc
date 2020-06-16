(ns uk.rogerthemoose.timelines.layout.element
  (:require [tick.alpha.api :as t]))

(defmulti bounds-of-element :element)

(defmethod bounds-of-element :default
  [element]
  (throw (ex-info "Bounds not implemented for element" element)))

(defmulti render-element (fn [_ {:keys [element]}] element))

(defmethod render-element :default
  [_ element]
  (throw (ex-info "Render not implemented for element" element)))

(defmulti render-bounds (fn [_ {:keys [element]}] element))

(defmethod render-bounds :default [_ _])

(defn xy [{:keys [x y]}]
  [x y])

(defn bounds-containing-all-elements [elements]
  (reduce
    (fn [bounds element]
      (let [new-bounds (bounds-of-element element)]
        {:from-date (t/min (:from-date bounds) (:from-date new-bounds))
         :to-date   (t/max (:to-date bounds) (:to-date new-bounds))
         :from-line (min (:from-line bounds) (:from-line new-bounds))
         :to-line   (max (:to-line bounds) (:to-line new-bounds))
         :left      (max (:left bounds) (or (:left new-bounds) 0))
         :right     (max (:right bounds) (or (:right new-bounds) 0))
         :top       (max (:top bounds) (or (:top new-bounds) 0))
         :bottom    (max (:bottom bounds) (or (:bottom new-bounds) 0))}))
    (merge {:left 0 :right 0 :top 0 :bottom 0} (bounds-of-element (first elements)))
    (rest elements)))