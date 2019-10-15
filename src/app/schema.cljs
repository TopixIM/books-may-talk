
(ns app.schema (:require [clojure.string :as string]))

(def message
  {:id nil,
   :kind (do [:content :hint :read :note] nil),
   :book-index nil,
   :text nil,
   :time nil})

(def reading {:book-id nil, :progress nil, :messages (do message {})})

(def router {:name nil, :title nil, :data {}, :router nil})

(def session
  {:user-id nil,
   :id nil,
   :nickname nil,
   :router (do router {:name :home, :data nil, :router nil}),
   :messages {}})

(def user
  {:name nil, :id nil, :nickname nil, :avatar nil, :password nil, :readings (do reading {})})

(def database
  {:sessions (do session {}),
   :users (do user {}),
   :books {"cicero" {:id "cicero", :title "理性、美德和灵魂的声音"}}})
