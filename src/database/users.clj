(ns database.users
  (:require [somnium.congomongo :as cm]
            [cemerick.friend.credentials :as creds]
            [ring.util.response :as resp]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])))

;;define connection to db booksdb
(def connection
  (cm/make-connection "booksdb"
                      :host "127.0.0.1"
                      :port 27017))

;;set connection globally
(cm/set-connection! connection)
(cm/set-write-concern connection :strict)

;;disconnect
(defn disconnect [conn]
  "Disconnect from database."
  (cm/close-connection conn))


;;fetch users from database
(def users (atom
            (into {} (let [map (cm/fetch :users :only {:_id false})
                           users {}]
                       (for [x map]
                         (if (= ["user"] (get x :roles))
                           (assoc users  (:username (into {} x)) (assoc (into {} x) :roles #{::user}))
                           (assoc users  (:username (into {} x)) (assoc (into {} x) :roles #{::admin}))))))))

;;update users after updating fields in users collection
(defn update-users []
  (reset! users
          (into {} (let [map (cm/fetch :users :only {:_id false})
                         users {}]
                     (for [x map]
                       (if (= ["user"] (get x :roles))
                         (assoc users  (:username (into {} x)) (assoc (into {} x) :roles #{::user}))
                         (assoc users  (:username (into {} x)) (assoc (into {} x) :roles #{::admin}))))))))


;;check if user exists in fetched users
(defn user-exists? [username]
  "Check if user with supplied username exists in the database."
  (if (not= 0 (count
               (filter not-empty
                       (cm/fetch :users :only {:_id false} :where {:username username})))) true false))



;;insert data for the user into db - collection users
(defn insert-user [username password confirm-password role request]
  "Insert new user under condition that there isn't another one with same username."
  (if (= password confirm-password)
    (if-not (user-exists? username)
      (do (cm/insert! :users
                      {:username username
                       :password (creds/hash-bcrypt password)
                       :roles #{role}})
          (resp/redirect (str (nth (vals (get request :headers)) 8) "?success")))
      (resp/redirect (str (nth (vals (get request :headers)) 8) "?error-user")))
    (resp/redirect (str (nth (vals (get request :headers)) 8) "?error-password"))))

;;get all users in users collection 
(defn get-all-users []
  "Get all users in :users collection from mongodb."
  (cm/fetch :users :only {:_id false :password false}))

;;get user by supplied username
(defn get-user [username]
  (filter not-empty (cm/fetch :users :only {:_id false} :where {:username username})))

;;update user data
(defn update-user [username role]
  "Update user password & role."
  (let [old-user (cm/fetch-one :users :where {:username username})]
    (do
      (cm/update! :users old-user (merge old-user {:roles #{role}}))
      (resp/response "User updated!"))))

;;delete user with supplied username
(defn reset-password [username password]
  "Reset user password."
  (let [user (cm/fetch-one :users :where {:username username})]
    (do
      (cm/update! :users user (merge user {:password (creds/hash-bcrypt password)}))
      (resp/response "Pasword sucessfully reset!"))))

;;delete user with supplied username
(defn delete-user [username request]
  "Delete user with supplied username."
  (if (user-exists? username)
    (do
      (cm/destroy! :users {:username username})
      (resp/redirect (str (nth (vals (get request :headers)) 8) "?success")))
    (resp/redirect (str (nth (vals (get request :headers)) 8) "?error"))))

; (defn display-message [param]
;   (resp/redirect (str (nth (vals (get request :headers)) 8) param)))
