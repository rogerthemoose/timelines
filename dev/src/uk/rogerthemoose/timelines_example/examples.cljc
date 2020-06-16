(ns uk.rogerthemoose.timelines-example.examples
  (:require [uk.rogerthemoose.timelines.api :as api]))

(def layout-options {:x-padding      150
                     :y-top          0
                     :y-bottom       20
                     :line-height    50
                     :render-bounds? false})

(def example-css [[:.viz
                   [:svg {:stroke :black}
                    [:.go [:circle {:stroke :green :fill :green}]]
                    [:.stop [:circle {:stroke :red :fill :red}]]
                    [:.timeline {:stroke :orange}]
                    [:.arrow {:stroke :blue :stroke-dasharray 4}]
                    [:.point {:r 3}]]]])

(def label-sizes
  (let [options (assoc layout-options :y-top 5 :y-bottom 5 :line-height 10 :render-bounds? true)]
    [:div
     [:div.viz (api/svg-for [(api/timeline {:line 1 :from "2019-08-01" :to "2019-10-01"})
                             (api/label {:at "2019-09-01" :line 1 :text "xxxxxxx" :v-align :top :align :middle})]
                            options)]

     [:div.viz (api/svg-for [(api/timeline {:line 1 :from "2019-08-01" :to "2019-10-01"})
                             (api/label {:at "2019-09-01" :line 1 :text "xxxxxxxx" :v-align :middle :align :middle})]
                            options)]

     [:div.viz (api/svg-for [(api/timeline {:line 1 :from "2019-08-01" :to "2019-10-01"})
                             (api/label {:at "2019-09-01" :line 1 :text "xxxxxxxx" :v-align :bottom :align :middle})]
                            options)]]))

(def examples
  [:div
   [:div.viz (api/svg-for [(api/timeline {:line 1 :from "2019-09-01" :to "2020-09-01"})

                           (api/group
                             :.go
                             [(api/point {:at "2019-09-01" :line 1})
                              (api/label {:at "2019-09-01" :line 1 :v-align :bottom :align :start :text "WIBBLE"})])

                           (api/group :.go [(api/event {:at "2020-01-01" :line 1 :format "MMM-YY"})])

                           (api/arrow {:at "2020-02-01" :from-line 1 :to-line 2})

                           (api/timeline {:line 2 :from "2020-01-01" :to "2021-01-01"})

                           (api/group :.stop [(api/event {:at "2020-01-01" :line 2 :format "MMM-YY"})])

                           (api/group :.go [(api/event {:at "2020-04-01" :line 2 :format "MMM-YY"})])])]])