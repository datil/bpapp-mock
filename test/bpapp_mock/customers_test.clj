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

;;; GET /customers

(deftest returns-a-customer-account-test
  (is (=
       (json-body (response-for service :get "/customers"))
       (:default (:customers rsc)))))
