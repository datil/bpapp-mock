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
        qs (:query-params request)
        qs-filter (first qs)
        resp (get rsc (:route-name (:route context)))
        resp-content (get
                      (get
                       resp
                       (first qs-filter))
                      (keyword (second qs-filter)))
        mock-resp (if (= resp-content nil)
                    (:default resp)
                    resp-content)]
    (assoc context :response (ring-resp/response mock-resp))))

(defroutes routes
  [[["/" {:get home-page}
     ^:interceptors [(body-params/body-params
                      (body-params/default-parser-map
                        :json-options {:key-fn keyword}))
                     bootstrap/json-body]
     ["/accounts" {:get [:accounts mock-response]}
      ["/:account-id" {:get [:accounts-detail mock-response]}
       ["/transactions" {:get [:accounts-transactions
                               mock-response]}]]]
     ["/credit-accounts" {:get [:credit-accounts mock-response]}]
     ["/customers" {:get [:customers mock-response]}]
     ["/debit-accounts" {:get [:debit-accounts mock-response]}]
     ["/debit-credit-cards" {:get [:debit-credit-cards mock-response]}]
     ["/detectid-images" {:get [:detectid-images mock-response]}]
     ["/water-services" {:get [:water-services mock-response]}
      ["/:account-id" {:get [:water-services-account mock-response]
                       :post [:water-services-payment mock-response]}]]
     ["/energy-services" {:get [:energy-services mock-response]}
      ["/:account-id" {:get [:energy-services-account mock-response]
                       :post [:energy-services-payment mock-response]}]]
     ["/telephone-services" {:get [:telephone-services mock-response]}
      ["/:account-id" {:get [:telephone-services-account mock-response]
                       :post [:telephone-services-payment mock-response]}]]]]])

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
