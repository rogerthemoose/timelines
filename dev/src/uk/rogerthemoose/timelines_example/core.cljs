(ns uk.rogerthemoose.timelines-example.core
  (:require [reagent.dom :refer [render]]
            [garden.core :refer [css]]
            [uk.rogerthemoose.timelines-example.examples :refer [example-css examples]]))

(render (css example-css)
        (-> js/document
            (.getElementById "style")))

(render examples
        (-> js/document
            (.getElementById "app")))