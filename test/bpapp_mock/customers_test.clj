(ns bpapp-mock.customers-test
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

(deftest returns-200-status-test
  (is (=
       (:status (response-for service :get "/customers"))
       200)))

(deftest returns-json-content-type-test
  (is (=
       (get (:headers (response-for service :get "/customers")) "Content-Type")
       "application/json;charset=UTF-8")))

(deftest returns-corresponding-response-body-test
  (is (=
       (json-body (response-for service :get "/customers"))
       (:customers rsc))))
