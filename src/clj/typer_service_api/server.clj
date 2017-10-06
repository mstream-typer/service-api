(ns typer-service-api.server
  (:gen-class)
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.cors :as cors]
            [io.pedestal.http.route :as route]
            [typer-service-api.service :as service]))


(defn add-cors-config [service]
  (assoc service
         ::http/interceptors
         (cons (cors/allow-origin (constantly true))
               (::http/interceptors service))))


(defn add-port-config [service]
  (assoc service
         ::http/port
         8080))


(def service
  (-> service/lacinia
      (add-cors-config)
      (add-port-config)))


(defonce runnable-service (http/create-server service))


(defn -main
  [& args]
  (http/start runnable-service))
