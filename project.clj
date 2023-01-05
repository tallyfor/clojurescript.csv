(defproject tallyfor/clojurescript.csv "0.5.3-TALLYFOR"
  :description "A ClojureScript library for reading and writing comma (and other) separated values."
  :url "https://github.com/testdouble/clojurescript.csv"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.10.126"]]
  :plugins [[lein-cljsbuild "1.1.8"]
            [lein-bump-version "0.1.6"]
            [lein-tools-deps "0.4.5"]]
  :middleware [lein-tools-deps.plugin/resolve-dependencies-with-deps-edn]
  ;; run lein install with LEIN_SNAPSHOTS_IN_RELEASE=true lein install
  :lein-tools-deps/config {:config-files [:install :user :project]}
  :hooks [leiningen.cljsbuild]
  :cljsbuild {:builds [{:id "whitespace"
                        :source-paths ["src" "test"]
                        :compiler {:output-to "target/js/whitespace.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}
                       {:id "simple"
                        :source-paths ["src" "test"]
                        ;; Change your environment variables (maybe editing .zshrc or .bashrc) to have:
                        ;; export LEIN_USERNAME="pdelfino"
                        ;; export LEIN_PASSWORD="your-personal-access-token-the-same-used-on-.npmrc"
                        ;; LEIN_PASSWORD should use the same Token used by .npmrc
                        ;; Also, do "LEIN_SNAPSHOTS_IN_RELEASE=true lein install" or edit your .zshrc:
                        ;; export LEIN_SNAPSHOTS_IN_RELEASE=true
                        :repositories {"releases"  {:url           "https://maven.pkg.github.com/tallyfor/*"
                                                    :username      :env/LEIN_USERNAME ;; change your env
                                                    :password      :env/LEIN_PASSWORD}}

                        :pom-addition [:distribution-management [:repository [:id "github"]
                                                                 [:name "GitHub Packages"]
                                                                 [:url "https://maven.pkg.github.com/tallyfor/dataflow"]]]
                                              :compiler {:output-to "target/js/simple.js"
                                                         :optimizations :simple
                                                         :pretty-print true}}
                       {:id "advanced"
                        :source-paths ["src" "test"]
                        :compiler {:output-to "target/js/advanced.js"
                                   :optimizations :advanced
                                   :pretty-print false}}]
              :test-commands {; PhantomJS tests
                              "phantom-whitespace" ["phantomjs" "phantom/runner.js" "test-resources/html/whitespace.html"]
                              "phantom-simple" ["phantomjs" "phantom/runner.js" "test-resources/html/simple.html"]
                              ;; "phantom-advanced" ["phantomjs" "phantom/runner.js" "test-resources/html/advanced.html"]
                              }}
  :aliases {"cleantest" ["do" "clean," "test"]
            "release" ["do" "clean," "deploy" "clojars"]})
