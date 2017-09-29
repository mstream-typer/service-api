(ns typer-service-api.service
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [com.walmartlabs.lacinia.pedestal :as lacinia]
            [com.walmartlabs.lacinia.schema :as schema]
            [com.walmartlabs.lacinia.util :as util]))


(def mocked-state
  {:courses {"1" {:name "QWERTY"
                  :description "Course for the QWERTY keyboard layout."
                  :exercise-ids #{"1" "2"}}
             "2" {:name "DVORAK"
                  :description "Course for the DVORAK keyboard layout."
                  :exercise-ids #{"1" "2"}}}
   :exercises {"1" {:name "f & j"
                    :time 60
                    :text (slurp (io/resource "exercises/1.txt"))}
               "2" {:name "k & d"
                    :time 60
                    :text (slurp (io/resource "exercises/2.txt"))}}})


(defn resolve-course [ctx args value]
  (let [id (:id args)]
    (merge {:id id}
           (get-in mocked-state
                   [:courses :id]))))


(defn resolve-courses [ctx args value]
  (map #(merge {:id (first %)}
               (second %))
       (:courses mocked-state)))


(defn resolve-exercise [ctx args value]
  (let [id (:id args)]
    (merge {:id id}
           (get-in mocked-state
                   [:exercises id]))))


(defn resolve-exercises [ctx args value]
  (let [course ((:courses mocked-state) (:id value))]
    (map #(merge {:id %}
                 (-> (get-in mocked-state
                             [:exercises %])))
         (:exercise-ids course))))


(defn schema []
  (-> (io/resource "schema.edn")
      (slurp)
      (edn/read-string)
      (util/attach-resolvers {:courses resolve-courses
                              :course resolve-course
                              :exercises resolve-exercises
                              :exercise resolve-exercise})
      (schema/compile)))


(def lacinia
  (lacinia/pedestal-service (schema)
                            {:graphiql true}))
