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

(deftest returns-a-list-of-accounts-test
  (is (=
       (json-body (response-for service :get "/accounts"))
       (:default (:accounts rsc)))))

;;; /accounts/[account-id]

(deftest returns-account-details-test
  (is (=
       (json-body (response-for service :get "/accounts/1063365620"))
       (:default (:accounts-detail rsc)))))

;;; /accounts/[:account-id]/transactions

(deftest returns-a-list-of-transactions-for-an-account-test
  (is (=
       (json-body (response-for service :get "/accounts/12/transactions"))
       (:default (:accounts-transactions rsc)))))
