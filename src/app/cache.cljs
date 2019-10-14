
(ns app.cache (:require ["path" :as path] ["fs" :as fs]))

(def books-cache
  {"cicero" (string/split
             (fs/readFileSync (path/join js/__dirname "books" "cicero.text"))
             (str "\n" "\n"))})
