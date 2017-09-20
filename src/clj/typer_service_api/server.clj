(ns typer-service-api.server
  (:gen-class)
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [io.pedestal.http :as server]
            [io.pedestal.http.route :as route]
            [com.walmartlabs.lacinia.pedestal :as lacinia]
            [com.walmartlabs.lacinia.schema :as schema]
            [com.walmartlabs.lacinia.util :as util]))


(defn resolve-course-exercises [ctx args value]
  [{:id 1
    :name "name1"
    :description "desc1"}
   {:id 2
    :name "name2"
    :description "desc2"}])


(defn resolve-course [ctx args value]
  (let [id (:id args)]
    {:id id
     :name (str "course" id)}))


(defn resolve-exercise [ctx args value]
  (let [id (:id args)]
    {:id id
     :time 100
     :text (str "text" id " text" id)}))


(defn schema []
  (-> (io/resource "schema.edn")
      (slurp)
      (edn/read-string)
      (util/attach-resolvers {:course-exercises resolve-course-exercises
                              :course resolve-course
                              :exercise resolve-exercise})
      (schema/compile)))


(def service
  (lacinia/pedestal-service (schema)
                            {:graphiql true}))


(defonce runnable-service (server/create-server service))


(defn -main
  [& args]
  (server/start runnable-service))
