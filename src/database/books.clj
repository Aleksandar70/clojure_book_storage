(ns database.books
  (:require [incanter.core :as core]
            [incanter.io :as io]
            [somnium.congomongo :as cm]
            [incanter.mongodb :as db]
            [ring.util.response :as resp]))

(defn get-books []
  (distinct (flatten (map vals
                          (cm/fetch
                           :books :only
                           {:_id false
                            :isbn true})))))

(defn book-exists? [isbn]
  "Check if book with supplied isbn exists in the database."
  (if (not= 0 (count
               (filter not-empty
                       (cm/fetch :books :only {:_id false} :where {:isbn isbn})))) true false))

(defn insert-book [title authors isbn publication-year count]
  "Insert new book under condition that there isn't another one with same isbn."
  (if-not (book-exists? isbn)
    (do
      (cm/insert! :books {:title title
                          :isbn isbn
                          :authors authors
                          :original_publication_year publication-year
                          :books_count count})
      (resp/response "Book added to the database")
      (resp/response "Book is already in the database"))
    (resp/response "Book exists!")))

(defn delete-book [isbn]
  "Delete book with supplied isbn."
  (do
    (cm/destroy! :books {:isbn isbn})
    (resp/response "Book deleted!")))

(defn get-book [isbn]
  (filter not-empty (cm/fetch :books :only {:_id false} :where {:isbn isbn})))

(defn update-book [title authors isbn year count]
  "Update book info."
  (let [old-book (cm/fetch-one :books :where {:isbn (Integer/parseInt isbn)})]
    (do
      (cm/update! :books old-book (merge old-book {:title title
                                                   :authors authors
                                                   :original_publication_year year
                                                   :books_count count}))
      (resp/response "Book updated."))))