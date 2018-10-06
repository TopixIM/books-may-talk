
(ns app.schema )

(def book {:id nil, :name nil, :author-name nil, :notes-amount 0, :sections {}})

(def database {:sessions {}, :users {}, :books {}})

(def note {:id nil, :text nil, :time nil, :author-id nil})

(def notification {:id nil, :kind nil, :text nil})

(def router {:name nil, :title nil, :data {}, :router nil})

(def section {:id nil, :text nil, :notes #{}})

(def session
  {:user-id nil,
   :id nil,
   :nickname nil,
   :router {:name :home, :data nil, :router nil},
   :messages {}})

(def user
  {:name nil, :id nil, :nickname nil, :avatar nil, :password nil, :notes {}, :marks {}})
