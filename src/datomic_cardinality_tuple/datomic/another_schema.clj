(ns datomic-cardinality-tuple.datomic.another-schema)

(def db-name "another")

(def schema
  [{:db/ident       :another/id
    :db/unique      :db.unique/identity
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one}
   {:db/ident       :another/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one}
   {:db/ident       :another/date
    :db/valueType   :db.type/instant
    :db/cardinality :db.cardinality/one}
   {:db/ident       :another/location                       ; heterogeneous tuples https://docs.datomic.com/on-prem/schema/schema.html#heterogeneous-tuples
    :db/valueType   :db.type/tuple
    :db/tupleTypes  [:db.type/long :db.type/long]
    :db/cardinality :db.cardinality/one}
   {:db/ident       :another/dates                          ; homogeneous tuples https://docs.datomic.com/on-prem/schema/schema.html#homogeneous-tuples
    :db/valueType   :db.type/tuple
    :db/tupleType  :db.type/instant
    :db/cardinality :db.cardinality/one}
   {:db/ident       :another/name+date                      ; composite tuples https://docs.datomic.com/on-prem/schema/schema.html#composite-tuples
    :db/valueType   :db.type/tuple
    :db/tupleAttrs  [:another/name :another/date]
    :db/cardinality :db.cardinality/one}])
