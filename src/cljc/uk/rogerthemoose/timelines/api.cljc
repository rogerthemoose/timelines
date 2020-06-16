(ns uk.rogerthemoose.timelines.api
  (:require [uk.rogerthemoose.timelines.layout.elements.timeline :as timeline-element]
            [uk.rogerthemoose.timelines.layout.elements.group :as group-element]
            [uk.rogerthemoose.timelines.layout.elements.point :as point-element]
            [uk.rogerthemoose.timelines.layout.elements.arrow :as arrow-element]
            [uk.rogerthemoose.timelines.layout.elements.label :as label-element]
            [uk.rogerthemoose.timelines.layout.elements.event :as event-element]
            [uk.rogerthemoose.timelines.layout.layout :as layout]))


(def group group-element/group)

(def timeline timeline-element/timeline)

(def point point-element/point)

(def arrow arrow-element/arrow)

(def label label-element/label)

(def event event-element/event)

(defn svg-for
  ([elements]
   (layout/layout elements {:x-padding 150 :y-top 0 :y-bottom 0 :line-height 50}))
  ([elements options]
   (layout/layout elements options)))