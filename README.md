# Book storage
This is a system for administrators where they can manage database of books. This web application allows administrators to manage their libraries in one place. They can add a new book, edit existing book, even delete if they want it out of their library. In the beginning, after log in, the home page is shown with a list of all the books in the library. It also allows users with admin privileges to create new users (admins or users) and delete them.

## Usage
Book storage application is using mongo database and you need to install mongodb (I used version 3.6.8). To use this application you need to install leiningen also (You can follow instructions on https://leiningen.org/). Database of this application you can find on https://www.kaggle.com/zygmunt/goodbooks-10k#books.csv, it contains more than a thousand books.

To initialize the database with the necessary data you need to set :main to **database.init** in project.clj file. Then you run cmd and navigate to the project folder and type lein run. After the database is filled with the data message "Initialization done!" will be displayed on the console. To start the web application you will need to change :main to **core** in project.clj file. You can now find a web app in your browser by typing http://localhost:8080/.

## References
* https://github.com/cgrand/enlive
* https://github.com/ring-clojure/ring
* https://github.com/weavejester/compojure
* https://github.com/incanter/incanter
* https://github.com/cemerick/friend
* https://github.com/clojure/clojure/

## License
Copyright © 2019

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
