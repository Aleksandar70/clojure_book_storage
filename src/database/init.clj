(ns database.init
  (:require [incanter.core :as core]
            [incanter.io :as io]
            [somnium.congomongo :as cm]
            [database.books_repo :only [insert-book get-books]]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])))

;;read dataset from .csv; delimiter is comma
(def data
  (io/read-dataset
   "books.csv"
   :header true))

;;view cols names
;;(core/col-names data)

;;view number of rows & cols
;;(core/dim data)

;;define connection to db booksdb 
(def connection
  (cm/make-connection "booksdb"
                      :host "127.0.0.1"
                      :port 27017))

;;set connection globally
(cm/set-connection! connection)
(cm/set-write-concern connection :strict)

;;insert books from book_data collection to books collection
(defn insert-books-from-collection []
  (for [book (database.books_repo/get-books)]
    (database.books_repo/insert-book book)))

;;initial admin username "admin", password "pass"
(defn insert-admin []
  "Initially insert user with admin role to be able to use the application."
  (cm/insert! :users
              {:username "admin"
               :password (creds/hash-bcrypt "nimda")
               :roles #{"admin"}}))

;;initial insertion of dataset read from .csv file
;;create book collection
(defn initialization []
  (do
    (dorun (cm/mass-insert! :book_data (:rows data)))
    (cm/create-collection! :books)
    (insert-books-from-collection)
    (insert-admin)
    (println "Initialization done!")))


(defn -main [& args]
  (initialization))


