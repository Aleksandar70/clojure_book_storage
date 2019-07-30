(ns database.books
  (:require [incanter.core :as core]
            [incanter.io :as io]
            [somnium.congomongo :as cm]
            [incanter.mongodb :as db]
            [ring.util.response :as resp]))

;;get books from books collection
(defn get-books []
  (distinct (flatten (map vals
                          (cm/fetch
                           :books :only
                           {:_id false
                            :isbn true})))))

;;check if book exists
(defn book-exists? [isbn]
  "Check if book with supplied isbn exists in the database."
  (if (not= 0 (count
               (filter not-empty
                       (cm/fetch :books :only {:_id false} :where {:isbn isbn})))) true false))

;;insert book in books collection
(defn insert-book [title authors isbn publication-year count]
  "Insert new book under condition that there isn't another one with same isbn."
  (if-not (book-exists? isbn)
    (do
      (cm/insert! :books {:title title 
                          :isbn isbn 
                          :authors authors 
                          :original_publication_year publication-year 
                          :books_count count
                          :language_code "eng"})
      (resp/response "Book added to the database")
      (resp/response "Book is already in the database"))
    (resp/response "Book exists!")))

