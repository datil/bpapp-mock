(ns bpapp-mock.debit-credit-cards-test
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

;;; /debit-credit-cards

(deftest returns-debit-accounts-test
  (is (=
       (json-body (response-for service :get "/debit-credit-cards"))
       (:debit-credit-cards rsc))))
