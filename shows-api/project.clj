(defproject shows-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                [ring/ring-core "1.9.5"]
                [ring/ring-jetty-adapter "1.9.5"]
                [compojure "1.6.2"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler shows-api.core/app}
  :repl-options {:init-ns shows-api.core})
