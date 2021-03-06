(ns bpapp-mock.test-utils
  (:require [cheshire.core :as json]))

(defn json-body
  [response]
  (json/parse-string (:body response) true))

(defn content-type
  [response]
  (get (:headers response) "Content-Type"))
