(defproject typer-service-api "0.1.0-SNAPSHOT"


  :dependencies
  [[org.clojure/clojure "1.8.0"]
   [funcool/cuerdas "2.0.3"]
   [io.pedestal/pedestal.jetty "0.5.2"]
   [io.pedestal/pedestal.service "0.5.2"]
   [com.walmartlabs/lacinia-pedestal "0.3.0"]
   [ch.qos.logback/logback-classic "1.2.3"]]
  
  
  :plugins
  [[lein-ancient "0.6.10"]
   [lein-bikeshed "0.4.1"]
   [lein-kibit "0.1.6-beta2"]
   [macluck/lein-docker "1.3.0"]]


  :min-lein-version
  "2.5.3"


  :source-paths
  ["src/clj"
   "config"]


  :main
  ^{:skip-aot true} typer-service-api.server

  
  :clean-targets
  ^{:protect false}
  ["target"]


  :profiles
  {:dev {:dependencies [[org.clojure/test.check "0.10.0-alpha2"]]
         :plugins []}
   :uberjar {:aot [typer-service-api.server]}}
  

  :docker
  {:image-name "typer/typer-service-api"
   :dockerfile "Dockerfile"
   :build-dir  "."}


  :aliases
  {"build" ["do"
            ["bikeshed"]
            ["kibit"]
            ["deps"]
            ;["doo" "phantom" "test" "once"]
            ["uberjar"]
            ["docker" "build"]]})
