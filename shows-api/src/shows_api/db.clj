(ns shows-api.db
  (:require [next.jdbc :as next]))

(def db-config {:dbtype "sqlite" :dbname "sqlite.db"})

(def db (next/get-datasource db-config))

(defn create-db
  "create shows table and populate it"
  []
  (do
      ;; create shows table
      (next/execute! db ["
  create table shows (
  id integer primary key,
  name varchar(50),
  description varchar(100))"])

      ;; populate members table
      (next/execute! db ["
 insert into shows (name, description)
 values
 ('Utopia', 'A show about future and the possible absence of it.');"])
 ))

;; (create-db)

;; (get-all-shows)

;; (get-show-by-id 1)

;; (create-show! {:name "Utopia" :description "A show about future and the possible absence of it."})

(defn get-all-shows []
  (next/execute! db ["select * from shows"]))

(defn get-show-by-id [id]
  (next/execute! db [ (str "select * from shows where id = " id)] ))

(defn create-show! [show]
  "show is a map with :name, :description"
  (next/execute! db [ (str "insert into shows (name, description) values ('" (:name show) "', '" (:description show) "')")]))

(defn delete-show! [id]
  (next/execute! db [ (str "delete from shows where id = " id)]))

(defn empty-db 
  "delete shows db table"
    []
    (next/execute! db ["drop table shows"]))