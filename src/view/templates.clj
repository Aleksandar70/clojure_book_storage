(ns view.templates
  (:require [net.cgrand.enlive-html :as html]))

(defn show-page-not-found [])
(html/at (html/html-resource "public/html/page_not_found.html"))

(defn show-home [user books]
  (html/at (html/html-resource "public/html/home.html")
           [:select.selectpicker [:option html/first-of-type]]
           (html/clone-for [book books] (html/content book))
           [:h3#current-user] (html/content user)))

(defn show-login []
  (html/at (html/html-resource "public/html/login.html")))


(defn show-admin [books]
  (html/at (html/html-resource "public/html/admin.html")
           [:select.selectpicker [:option html/first-of-type]]
           (html/clone-for [book books] (html/content (str book)))))