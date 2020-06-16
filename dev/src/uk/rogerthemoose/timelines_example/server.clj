(ns uk.rogerthemoose.timelines-example.server
  (:require [hiccup.page :refer [html5 include-js]]
            [ring.util.response :refer [response content-type]]))

(defn index-html []
  (html5
    [:head
     [:meta {:charset "UTF-8"}]
     [:meta {:name    "viewport"
             :content "width=device-width, initial-scale=1"}]
     [:style#style]]
    [:body
     [:div#app]
     (include-js "/cljs-out/dev-main.js")]))

(defn handler [_]
  (-> (response (index-html))
      (content-type "text/html")))