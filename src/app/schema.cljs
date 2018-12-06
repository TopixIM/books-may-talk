
(ns app.schema )

(def section {:id nil, :text nil, :notes-index []})

(def book
  {:id nil,
   :name nil,
   :author-name nil,
   :avatar nil,
   :notes-amount 0,
   :sections (do section {})})

(def mark {:book-id nil, :section-id nil, :section-idx nil, :time nil})

(def note {:id nil, :book-id nil, :section-id nil, :text nil, :time nil, :author-id nil})

(def router {:name nil, :title nil, :data {}, :router nil})

(def session
  {:user-id nil,
   :id nil,
   :nickname nil,
   :router (do router {:name :home, :data nil, :router nil}),
   :messages {}})

(def user
  {:name nil,
   :id nil,
   :nickname nil,
   :avatar nil,
   :password nil,
   :notes-index [],
   :marks (do mark {})})

(def database
  {:sessions (do session {}), :users (do user {}), :books (do book {}), :notes (do note {})})
