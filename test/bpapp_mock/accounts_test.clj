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

;;; /accounts

(deftest returns-corresponding-response-body-test
  (is (=
       (json-body (response-for service :get "/accounts"))
       (:accounts rsc))))

;;; /accounts/[account-id]

(deftest returns-corresponding-response-body-for-account-number-test
  (is (=
       (json-body (response-for service :get "/accounts/1063365620"))
       (:accounts-detail rsc))))

;;; /accounts/[:account-id]/transactions

(deftest returns-corresponding-response-body-for-account-transactions-test
  (is (=
       (json-body (response-for service :get "/accounts/12/transactions"))
       (:accounts-transactions rsc))))
