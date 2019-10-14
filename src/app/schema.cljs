
(ns app.schema (:require [clojure.string :as string]))

(def router {:name nil, :title nil, :data {}, :router nil})

(def session
  {:user-id nil,
   :id nil,
   :nickname nil,
   :router (do router {:name :home, :data nil, :router nil}),
   :messages {}})

(def user {:name nil, :id nil, :nickname nil, :avatar nil, :password nil})

(def database
  {:sessions (do session {}), :users (do user {}), :books {"cicero" {:title nil}}})
