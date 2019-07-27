(ns core
  (:require [view.routing :as route]
            [somnium.congomongo :as cm]))

(defn -main [& args]
  (route/start-server))
