(ns view.routing
  (:use (compojure handler
                   [core :only (GET POST ANY defroutes) :as compojure]))
  (:require [net.cgrand.enlive-html :as html]
            [ring.adapter.jetty :only [run-jetty]]
            [ring.util.response :as resp]
            [ring.middleware.session :as session]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [cemerick.friend :as friend]
            [cemerick.friend.credentials :as creds]
            [cemerick.friend.workflows :as workflows]
            [database.users :as users :refer (users)]
            [database.books :as books]
            [view.templates :as templates]))

(defroutes book-routes
  (GET "/login" [] (html/emit* (templates/show-login)))
  (GET "/" request
    (if (= #{::users/user}
           (:roles (friend/current-authentication)))
      (resp/redirect "/home")
      (resp/redirect "/add-user")))

  (GET "/home" request
    (friend/authorize #{::users/user} (html/emit*
                                       (templates/show-home
                                        (:current (friend/identity request))
                                        (into []
                                              (take 1000 (sort-by str (books/get-books))))))))

  (GET "/add-book" request
    (friend/authorize #{::users/admin} (html/emit* (templates/show-add-book))))

  (POST "/add-book" request
    (let [title (get (:params request) :title)
          authors (get (:params request) :authors)
          isbn (get (:params request) :isbn)
          publication-year (get (:params request) :publication-year)
          count (get (:params request) :count)]
      (books/insert-book title authors isbn publication-year count)))

  (GET "/delete-book" request
    (friend/authorize #{::users/admin} (html/emit* (templates/show-delete-book))))

  (POST "/delete-book" request
    (let [isbn (get (:params request) :isbn)]
      (books/delete-book isbn)))

  (GET "/add-user" request
    (friend/authorize #{::users/admin} (html/emit* (templates/show-add-user))))

  (POST "/add-user" request
    (let [username (get (:params request) :username)
          password (get (:params request) :password)
          confirm-password (get (:params request) :confirm-password)
          role (get (:params request) :role)]
      (users/insert-user username password confirm-password role)))

  (GET "/delete-user" request
    (friend/authorize #{::users/admin} (html/emit* (templates/show-delete-user))))

  (POST "/delete-user" request
    (let [username (get (:params request) :username)]
      (users/delete-user username)))

  (GET "/edit-user" request
    (friend/authorize #{::users/admin}
                      (let [user (users/get-user (get (:params request) :username))]
                        (html/emit* (templates/show-edit-user user)))))

  (POST "/edit-user" request
    (let [username (get (:params request) :username)]
      (users/get-user username)))

  (GET "/edit-book" request
    (friend/authorize #{::users/admin}
                      (if (not= (get (:params request) :isbn) nil)
                        (let [book (books/get-book (Integer/parseInt (get (:params request) :isbn)))]
                          (html/emit* (templates/show-edit-book book)))
                        (html/emit* (templates/show-edit-book nil)))))

  (POST "/edit-book" request
    (let [title (get (:params request) :title)
          authors (get (:params request) :authors)
          isbn (get (:params request) :isbn)
          year (get (:params request) :year)
          count (get (:params request) :count)
          req (get (:params request) :request)]
      (books/update-book title authors isbn year count)))

  (route/resources "/")
  (friend/logout (ANY "/logout" request (resp/redirect "/login")))
  (route/not-found "Page not found"))

(def app
  (-> (handler/site
       (friend/authenticate book-routes
                            {:allow-anon? true
                             :credential-fn #(creds/bcrypt-credential-fn @users %)
                             :workflows [(workflows/interactive-form)]}))
      (session/wrap-session)))

(defn start-server []
  (ring.adapter.jetty/run-jetty #'app {:port 8080 :join? false}))
