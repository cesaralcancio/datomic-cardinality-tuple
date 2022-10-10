(ns datomic-cardinality-tuple.cardinality
  (:require [datomic-cardinality-tuple.datomic.config :as datomic.config]
            [datomic-cardinality-tuple.datomic.something_schema :as datomic.schema]
            [datomic.api :as d])
  (:import (java.util UUID Date)))

(defn now
  [] (new Date))

(datomic.config/create-database! datomic.config/base-uri datomic.schema/db-name)
(def conn (datomic.config/connect! datomic.config/base-uri datomic.schema/db-name))
(datomic.config/create-schema! conn datomic.schema/something-schema)

(datomic.config/list-databases! datomic.config/base-uri)

(def something-id (UUID/randomUUID))
(def something-name "One")

(d/transact conn
            [[:db/add "temporary-new-db-id" :something/id something-id]
             [:db/add "temporary-new-db-id" :something/name something-name]
             [:db/add "temporary-new-db-id" :something/dates (now)]])

(def something-id2 (UUID/randomUUID))
(def something-name2 "Two")
(def date1 (now))
(def date2 (now))
(d/transact conn [{:something/id    something-id2
                   :something/name  something-name2
                   :something/dates [date1 date2]}])

(d/q '[:find ?id ?name ?dates
       :keys id name dates
       :where
       [?e :something/id ?id]
       [?e :something/name ?name]
       [?e :something/dates ?dates]]
     (d/db conn))

(d/q '[:find (pull ?something [*])
       :where [?something :something/id ?id]]
     (d/db conn))

(def items (d/q '[:find ?id ?dates
                  :keys id dates
                  :where
                  [?e :something/id ?id]
                  [?e :something/dates ?dates]]
                (d/db conn)))

(doseq [item items]
  (let [id (:id item)]
    (println id)
    (d/transact
      conn
      [[:db/retract [:something/id id] :something/id]
       [:db/retract [:something/id id] :something/name]
       [:db/retract [:something/id id] :something/dates]])))
