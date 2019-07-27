(ns database.books_repo
  (:require [incanter.core :as core]
            [incanter.io :as io]
            [somnium.congomongo :as cm]
            [incanter.mongodb :as db]
            [ring.util.response :as resp]))

;;get books from book_data collection
(defn get-books []
  (distinct (flatten (map vals 
                          (cm/fetch 
                            :books :only
                            {:_id false
                             :isbn true})))))

;;check if book exists
(defn book-exists? [book]
  "Check if book with supplied isbn exists in the database."
  (if (not= 0 (count 
                (filter not-empty 
                        (cm/fetch :books :only {:_id false} :where {:isbn book})))) true false))

;;insert book in books collection
(defn insert-book [book]
  "Insert new book under condition that there isn't another one with same isbn."
  (if-not (book-exists? book)
    (do
      (cm/insert! :books {:isbn book})
      (resp/response "Book added to the database")
      (resp/response "Book is already in the database"))
    (resp/response "Book exists!")))

