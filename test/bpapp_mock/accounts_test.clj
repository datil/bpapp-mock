(ns bpapp-mock.accounts-test
  (:require [cheshire.core :as json]
            [clojure.test :refer :all]
            [io.pedestal.test :refer :all]
            [io.pedestal.http :as bootstrap]
            [bpapp-mock.service :as service]
            [bpapp-mock.util :as u]
            [bpapp-mock.test-utils :refer [json-body content-type]]))

(def service
  (::bootstrap/service-fn (bootstrap/create-servlet service/service)))

(def rsc (u/load-resource "responses.edn"))

(deftest returns-200-status-test
  (is (=
       (:status (response-for service :get "/accounts"))
       200)))

(deftest returns-json-content-type-test
  (is (=
       (get (:headers (response-for service :get "/accounts")) "Content-Type")
       "application/json;charset=UTF-8")))

(deftest returns-corresponding-response-body-test
  (is (=
       (json-body (response-for service :get "/accounts"))
       (:accounts rsc))))

(deftest returns-200-status-for-account-number-test
  (is (=
       (:status (response-for service :get "/accounts/1063365620"))
       200)))

(deftest returns-json-content-type-for-account-number-test
  (is (=
       (content-type (response-for service :get "/accounts/1063365620"))
       "application/json;charset=UTF-8")))

(deftest returns-corresponding-response-body-for-account-number-test
  (is (=
       (json-body (response-for service :get "/accounts/1063365620"))
       (:accounts-detail rsc))))
