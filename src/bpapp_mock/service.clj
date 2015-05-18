(ns bpapp-mock.service
  (:require [bpapp-mock.util :as u]
            [io.pedestal.http :as bootstrap]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [io.pedestal.interceptor.helpers :refer [defbefore]]
            [ring.util.response :as ring-resp]))

(def rsc (u/load-resource "responses.edn"))

(defn home-page
  [request]
  (ring-resp/response "Hello World!"))

(defbefore mock-response
  [context]
  (let [request (:request context)
        response (ring-resp/response
                  (get rsc (:route-name (:route context))))]
    (assoc context :response response)))

(defroutes routes
  [[["/" {:get home-page}
     ^:interceptors [(body-params/body-params)
                     bootstrap/json-body]
     ["/customers" {:get [:customers mock-response]}]
     ["/detectid-images" {:get [:detectid-images mock-response]}]
     ["/accounts" {:get [:accounts mock-response]}]
     ["/accounts/:account-id" {:get [:accounts-detail mock-response]}]
     ["/accounts/:account-id/transactions" {:get [:accounts-transactions
                                                  mock-response]}]
     ["/water-services" {:get [:water-services mock-response]}]]]])

;; Consumed by bpapp-mock.server/create-server
;; See bootstrap/default-interceptors for additional options you can configure
(def service {:env :prod
              ;; You can bring your own non-default interceptors. Make
              ;; sure you include routing and set it up right for
              ;; dev-mode. If you do, many other keys for configuring
              ;; default interceptors will be ignored.
              ;; ::bootstrap/interceptors []
              ::bootstrap/routes routes

              ;; Uncomment next line to enable CORS support, add
              ;; string(s) specifying scheme, host and port for
              ;; allowed source(s):
              ;;
              ;; "http://localhost:8080"
              ;;
              ;;::bootstrap/allowed-origins ["scheme://host:port"]

              ;; Root for resource interceptor that is available by default.
              ::bootstrap/resource-path "/public"

              ;; Either :jetty, :immutant or :tomcat (see comments in project.clj)
              ::bootstrap/type :jetty
              ;;::bootstrap/host "localhost"
              ::bootstrap/port 8080})
