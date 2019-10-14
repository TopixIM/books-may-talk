
(ns app.comp.home
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.comp.space :refer [=<]]
            [respo.core :refer [defcomp <> action-> list-> span div]]
            [app.config :as config]
            [respo.util.list :refer [map-val]]))

(defcomp
 comp-home
 (books)
 (div
  {:style {:padding "8px 16px"}}
  (div {:style {:font-family ui/font-fancy, :font-size 16, :font-weight 300}} (<> "Books"))
  (list->
   {}
   (->> books
        (map-val
         (fn [book]
           (div
            {:style {:border-bottom (str "1px solid " (hsl 0 0 90)),
                     :cursor :pointer,
                     :padding "0 8px"},
             :class-name "list-item",
             :on-click (fn [e d! m!] (d! :router/change {:name :reading, :data (:id book)}))}
            (<> (:title book) {:line-height "32px", :display :inline-block})
            (=< 8 nil)
            (<> (:id book) {:font-size 12, :color (hsl 0 0 80), :font-style :italic}))))))))
