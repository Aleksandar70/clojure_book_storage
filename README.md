# book_storage

A Clojure library designed to ... well, that part is up to you.

## Usage
Because this application is using mongo database you need to install mongodb (I used version 3.6.8). To use this application you need to install leiningen also (PC users can follow instructions on https://leiningen.org/).

To initialize the database with the necessary data you need to set :main to database.init in project.clj file. Then you run cmd and navigate to the project folder and type lein run. After the database is filled with the data message "Initialization done!" will be displayed on the console. To start the web application you will need to change :main to core in project.clj file. You can now find a web app in your browser by typing http://localhost:8080/.

## References
Database https://www.kaggle.com/zygmunt/goodbooks-10k#books.csv


## License

Copyright © 2019 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
