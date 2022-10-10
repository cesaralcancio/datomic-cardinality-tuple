(ns datomic-cardinality-tuple.datomic.something_schema)

(def db-name "something")

(def something-schema
  [{:db/ident       :something/id
    :db/unique      :db.unique/identity
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one}
   {:db/ident       :something/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident       :something/dates
    :db/valueType   :db.type/instant
    :db/cardinality :db.cardinality/many}])
