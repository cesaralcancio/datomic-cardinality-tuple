(ns datomic-cardinality-tuple.datomic.config
  (:require [datomic.api :as d]))

; https://docs.datomic.com/on-prem/clojure/index.html
(def base-uri "datomic:dev://localhost:4334/")

(defn list-databases! [uri]
  (d/get-database-names (str uri "*")))
; (list-databases! datomic-uri)

(defn create-database! [uri db-name]
  (d/create-database (str uri db-name)))
; (create-database! datomic-uri db-name-transactions)

(defn delete-database! [uri db-name]
  (d/delete-database (str uri db-name)))
; (delete-database! datomic-uri db-name-transactions)

(defn connect!
  [uri db-name]
  (d/connect (str uri db-name)))
; (connect! db-uri)

(defn create-schema!
  [conn schema]
  (d/transact conn schema))
