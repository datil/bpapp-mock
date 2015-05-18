(ns bpapp-mock.util)

(defn load-resource
  "La forma canónica de leer recursos."
  [filename]
  (read-string
   (slurp
    (clojure.java.io/reader
     (clojure.java.io/resource
      filename)))))
