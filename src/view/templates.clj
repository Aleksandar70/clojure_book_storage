(ns view.templates
  (:require [net.cgrand.enlive-html :as html]
            [ring.util.response :as resp]))

(defn show-page-not-found [])
(html/at (html/html-resource "public/html/page_not_found.html"))

(defn show-home [user books]
  (html/at (html/html-resource "public/html/home.html")
           [:select.selectpicker [:option html/first-of-type]]
           (html/clone-for [book books] (html/content book))
           [:h3#current-user] (html/content user)))

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

(defn show-edit-user [user]
  (if (= user nil)
    (html/at (html/html-resource "public/html/admin/edit_user.html"))
    (html/at (html/html-resource "public/html/admin/edit_user.html")
             [:input#username] (html/set-attr :value (get-in (first user) [:username]))
             (if (= "admin" (first (get-in (first user) [:roles])))
               ([:option#selected] (html/content "ADMIN"))
               ([:option#selected] (html/content "USER")))
             (if (= "user" (first (get-in (first user) [:roles])))
               ([:option#not_selected] (html/content "ADMIN"))
               ([:option#not_selected] (html/content "USER"))))))

(defn show-edit-book [book]
  (if (= book nil)
    (html/at (html/html-resource "public/html/admin/edit_book.html"))
    (html/at (html/html-resource "public/html/admin/edit_book.html")
             [:input#title] (html/set-attr :value (get-in (first book) [:title]))
             [:input#authors] (html/set-attr :value (get-in (first book) [:authors]))
             [:input#isbn] (html/set-attr :value (get-in (first book) [:isbn]))
             [:input#year] (html/set-attr :value (int (get-in (first book) [:original_publication_year])))
             [:input#count] (html/set-attr :value (get-in (first book) [:books_count])))))