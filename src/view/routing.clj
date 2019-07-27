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
            [database.users_repo :as users :refer (users)]
            [database.books_repo :as dbb]
            [view.templates :as templates]))

(defroutes beer-routes
  (GET "/login" [] (html/emit* (templates/show-login)))
  (GET "/" request
    (if (= #{::users/user}
           (:roles (friend/current-authentication)))
      (resp/redirect "/home")
      (resp/redirect "/admin")))

  (GET "/home" request
    (friend/authorize #{::users/user} (html/emit*
                                       (templates/show-home
                                        (:current (friend/identity request))
                                        (into []
                                              (take 1000 (sort-by str (dbb/get-books))))))))
  (GET "/admin" request
    (friend/authorize #{::users/admin} (html/emit* (templates/show-admin (into []
                                                                               (dbb/get-books))))))
  (route/resources "/")
  (friend/logout (ANY "/logout" request (resp/redirect "/login")))
  (route/not-found "Page not found"))

(def app
  (-> (handler/site
       (friend/authenticate beer-routes
                            {:allow-anon? true
                             :credential-fn #(creds/bcrypt-credential-fn @users %)
                             :workflows [(workflows/interactive-form)]}))
      (session/wrap-session)))

(defn start-server []
  (ring.adapter.jetty/run-jetty #'app {:port 8080 :join? false}))
