(ns bpapp-mock.debit-accounts-test
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

;;; /debit-accounts

(deftest returns-debit-accounts-test
  (is (=
       (json-body (response-for service :get "/debit-accounts"))
       (:default (:debit-accounts rsc)))))

;;; /debit-accounts?type=10

(deftest filters-savings-account-type-test
  (is (=
       (json-body (response-for service :get "/debit-accounts?type=10"))
       (:10 (:type (:debit-accounts rsc))))))

;;; /debit-accounts?type=1

(deftest filters-credit-cards-type-test
  (is (=
       (json-body (response-for service :get "/debit-accounts?type=1"))
       (:1 (:type (:debit-accounts rsc))))))

;;; /debit-accounts?type=1

(deftest filters-checking-cards-type-test
  (is (=
       (json-body (response-for service :get "/debit-accounts?type=11"))
       (:11 (:type (:debit-accounts rsc))))))
