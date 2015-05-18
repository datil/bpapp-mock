(ns bpapp-mock.water-test
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

;;; GET /water-services

(deftest lists-water-services-accounts-test
  (is (=
       (json-body (response-for service :get "/water-services"))
       (:water-services rsc))))

;;; GET /water-services/[:account-number]

(deftest returns-water-service-account-data-test
  (is (=
       (json-body (response-for service :get "/water-services/1234"))
       (:water-services-account rsc))))

;;; POST /water-services/[:account-number]

(deftest returns-water-service-payment-confirmation-test
  (is (=
       (json-body (response-for service
                                :post "/water-services/1234"
                                :headers {"Content-Type" "application/json"}
                                :body (json/generate-string {:a :b})))
       (:water-services-payment rsc))))
