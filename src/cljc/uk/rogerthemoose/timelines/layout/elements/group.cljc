(ns uk.rogerthemoose.timelines.layout.elements.group
  (:require [clojure.string :as str]
            [uk.rogerthemoose.timelines.layout.element :as e :refer [bounds-of-element render-element render-bounds]]))


(defn group [classes elements]
  {:element     :group
   :classes     classes
   :composed-of elements})

(defmethod bounds-of-element :group
  [{:keys [composed-of]}]
  (e/bounds-containing-all-elements composed-of))

(defn- g-tag-for [classes]
  (cond
    (keyword? classes)
    (keyword (str "g" (name classes)))

    :else
    (keyword (str "g." (str/join "." (str/split classes #"\s+"))))))

(defmethod render-element :group
  [c-fn {:keys [classes composed-of]}]
  (let [rendered (doall (map (partial render-element c-fn) composed-of))]
    `[~(g-tag-for classes) ~@rendered]))

(defmethod render-bounds :group
  [c-fn {:keys [classes composed-of]}]
  (let [rendered (doall (map (partial render-bounds c-fn) composed-of))]
    `[~(g-tag-for classes) ~@rendered]))