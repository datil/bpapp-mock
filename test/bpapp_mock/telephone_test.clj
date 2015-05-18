(ns bpapp-mock.telephone-test
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

;;; GET /telephone-services

(deftest lists-telephone-services-accounts-test
  (is (=
       (json-body (response-for service :get "/telephone-services"))
       (:telephone-services rsc))))

;;; GET /telephone-services/[:account-number]

(deftest returns-telephone-service-account-data-test
  (is (=
       (json-body (response-for service :get "/telephone-services/1234"))
       (:telephone-services-account rsc))))

;;; POST /telephone-services/[:account-number]

(deftest returns-telephone-service-payment-confirmation-test
  (is (=
       (json-body (response-for service
                                :post "/telephone-services/1234"
                                :headers {"Content-Type" "application/json"}
                                :body (json/generate-string {:a :b})))
       (:telephone-services-payment rsc))))
