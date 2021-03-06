(ns uk.rogerthemoose.timelines.specs
  (:require
    #?(:clj  [clojure.spec.alpha :as s]
       :cljs [cljs.spec.alpha :as s])))

(defn check [spec data]
  (or (s/valid? spec data) (s/explain spec data)))

(s/def ::line pos-int?)
(s/def ::date (complement nil?))

(s/def ::from-line ::line)
(s/def ::to-line ::line)
(s/def ::from-date ::date)
(s/def ::to-date ::date)
(s/def ::at ::date)

(s/def ::radius pos-int?)
(s/def ::width pos-int?)
(s/def ::height pos-int?)
(s/def ::margin nat-int?)
(s/def ::margin-top nat-int?)
(s/def ::margin-bottom nat-int?)

(defmulti element-type :element)

(s/def ::timeline (s/keys :req-un [::line ::from ::to]))

(defmethod element-type :timeline
  [_]
  (s/keys :req-un [::line ::from-date ::to-date]))

(s/def ::x-spacer (s/keys :req-un [::line ::from ::to]))

(defmethod element-type :x-spacer
  [_]
  (s/keys :req-un [::from-date ::to-date ::line]))

(s/def ::point (s/keys :req-un [::line ::at]
                       :opt-un [::radius]))

(defmethod element-type :point
  [_]
  (s/keys :req-un [::line ::at]))

(s/def ::box (s/keys :req-un [::line ::at]
                     :opt-un [::width ::height]))

(defmethod element-type :box
  [_]
  (s/keys :req-un [::line ::at ::width ::height]))

(s/def ::arrow (s/keys :req-un [::from-line ::to-line ::at]
                       :opt-un [::margin ::margin-top ::margin-bottom]))

(defmethod element-type :arrow
  [_]
  (s/keys :req-un [::from-line ::to-line ::at ::margin-top ::margin-bottom]))

(s/def ::label (s/keys :req-un [::line ::at ::text]))

(defmethod element-type :label
  [_]
  (s/keys :req-un [::line ::at ::text ::align ::v-align]))

(defmethod element-type :group
  [_]
  (s/keys :req-un [::classes ::composed-of]))

(s/def ::event (s/keys :req-un [::line ::at]))

(defmethod element-type :event
  [_]
  (s/keys :req-un [::composed-of]))

(s/def ::element (s/multi-spec element-type :element))

(s/def ::bounds (s/keys :req-un [::from-line ::to-line ::from-date ::to-date]
                        :opt-un [::top ::bottom ::left ::right]))