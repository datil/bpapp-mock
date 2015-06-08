(ns bpapp-mock.energy-test
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

;;; GET /energy-services

(deftest lists-energy-service-accounts-test
  (is (=
       (json-body (response-for service :get "/energy-services"))
       (:default (:energy-services rsc)))))

;;; GET /energy-services/[account-number]

(deftest returns-energy-service-account-data-test
  (is (=
       (json-body (response-for service :get "/energy-services/12345"))
       (:default (:energy-services-account rsc)))))

;;; POST /energy-services/[account-number]

(deftest returns-energy-service-payment-confirmation-test
  (is (=
       (json-body (response-for service
                                :post "/energy-services/12345"
                                :headers {"Content-Type" "application/json"}
                                :body (json/generate-string {:test :data})))
       (:default (:energy-services-payment rsc)))))
