(defproject book_storage "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [enlive "1.1.6"]
                 [ring "1.7.1"]
                 [compojure "1.6.1"]
                 [incanter "1.5.6"]
                 [com.cemerick/friend "0.2.3"]]
  :repl-options {:init-ns core}
  :main core)
