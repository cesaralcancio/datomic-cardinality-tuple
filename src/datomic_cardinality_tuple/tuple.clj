(ns datomic-cardinality-tuple.tuple
  (:require [datomic-cardinality-tuple.datomic.config :as datomic.config]
            [datomic-cardinality-tuple.datomic.another-schema :as datomic.schema]
            [datomic.api :as d])
  (:import (java.util Date UUID)))

(defn now
  [] (new Date))

(datomic.config/create-database! datomic.config/base-uri datomic.schema/db-name)
(def conn (datomic.config/connect! datomic.config/base-uri datomic.schema/db-name))
(datomic.config/create-schema! conn datomic.schema/schema)

(datomic.config/list-databases! datomic.config/base-uri)

(def id (UUID/randomUUID))
(def name "One")
(def date (now))
(def loc1 100)
(def loc2 200)

(d/transact conn
            [[:db/add "temporary-new-db-id" :another/id id]
             [:db/add "temporary-new-db-id" :another/name name]
             [:db/add "temporary-new-db-id" :another/date date]
             [:db/add "temporary-new-db-id" :another/dates [date date]]
             [:db/add "temporary-new-db-id" :another/location [loc1 loc2]]])

(def items (d/q '[:find (pull ?something [*])
                  :where [?something :another/id ?id]]
                (d/db conn)))
(clojure.pprint/pprint (first items))

(doseq [item (first items)]
  (println item)
  (let [id (:another/id item)]
    (println id)
    (d/transact
      conn
      [[:db/retract [:another/id id] :another/id]
       [:db/retract [:another/id id] :another/name]
       [:db/retract [:another/id id] :another/date]
       [:db/retract [:another/id id] :another/dates]
       [:db/retract [:another/id id] :another/location]])))
