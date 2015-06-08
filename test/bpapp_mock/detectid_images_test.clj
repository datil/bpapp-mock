(ns bpapp-mock.detectid-images-test
  (:require [cheshire.core :as json]
            [clojure.test :refer :all]
            [io.pedestal.test :refer :all]
            [io.pedestal.http :as bootstrap]
            [bpapp-mock.service :as service]
            [bpapp-mock.util :as u]
            [bpapp-mock.test-utils :refer [json-body]]))

(def service
  (::bootstrap/service-fn (bootstrap/create-servlet service/service)))

(def rsc (u/load-resource "responses.edn"))

(deftest returns-a-detectid-record-test
  (is (=
       (json-body (response-for service :get "/detectid-images"))
       (:default (:detectid-images rsc)))))
