(ns view.templates
  (:require [net.cgrand.enlive-html :as html]
            [hiccup.core :as hc]
            [ring.util.response :as resp]))

(defn show-home [user books]
  (html/at (html/html-resource "public/html/home.html")
           [:div#book-table] (html/content (hc/html [:h1 "List of all books"]
                                                    [:table
                                                     [:tr (map (fn [x] [:th x]) ["ISBN" "Title" "Author" "Publication Year" "Count"])]
                                                     (for [[authors count year title isbn] books]
                                                       [:tr (map (fn [x] [:td x]) [isbn title authors year count])])]))))

(defn show-login []
  (html/at (html/html-resource "public/html/login.html")))

(defn show-add-book []
  (html/at (html/html-resource "public/html/admin/add_book.html")))

(defn show-delete-book []
  (html/at (html/html-resource "public/html/admin/delete_book.html")))

(defn show-add-user []
  (html/at (html/html-resource "public/html/admin/add_user.html")))

(defn show-delete-user []
  (html/at (html/html-resource "public/html/admin/delete_user.html")))

(defn show-edit-book [book]
  (if (= book nil)
    (html/at (html/html-resource "public/html/admin/edit_book.html"))
    (if-not (= (get book :body) "")
      (html/at (html/html-resource "public/html/admin/edit_book.html")
               [:input#title] (html/set-attr :value (get-in (first book) [:title]))
               [:input#authors] (html/set-attr :value (get-in (first book) [:authors]))
               [:input#isbn] (html/set-attr :value (get-in (first book) [:isbn]))
               [:input#year] (html/set-attr :value (int (get-in (first book) [:original_publication_year])))
               [:input#count] (html/set-attr :value (get-in (first book) [:books_count])))
      (html/at (html/html-resource "public/html/admin/edit_book.html")))))

(defn map-tag [tag xs]
  (map (fn [x] [tag x]) xs))

(defn create-table [books]
  (list
   [:h1 "Books"]
   [:hr]
   [:table {:style "border: 0; width: 90%"}
    [:tr (map-tag :th ["ISBN" "Title" "Author" "Publication Year" "Count"])]
    [:tr (for [[authors count year title isbn] books]
           (list* [:tr [:td isbn]]
                  [:tr [:td title]]
                  [:tr [:td authors]]
                  [:tr [:td year]]
                  [:tr [:td count]]))]]))
