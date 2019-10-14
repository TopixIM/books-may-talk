
(ns app.comp.reading-room
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.comp.space :refer [=<]]
            [respo.core :refer [defcomp <> action-> list-> button span div]]
            [app.config :as config]
            [respo.util.list :refer [map-val]]))

(defcomp
 comp-reading-room
 (book reading)
 (if (nil? reading)
   (div
    {:style {:padding "24px 16px"}}
    (div {} (<> (:title book) {:font-size 24}))
    (=< nil 16)
    (div
     {}
     (button
      {:style ui/button,
       :inner-text "Start Reading",
       :on-click (fn [e d! m!] (d! :reading/start (:id book)))})))
   (div
    {:style (merge ui/expand ui/column {:padding "16px"})}
    (div {} (<> "Messages" {:font-family ui/font-fancy, :font-size 16}))
    (list->
     {:style ui/expand}
     (->> (:messages reading)
          (map-val
           (fn [message] (div {} (<> "BOOK:") (<> (:text message)) (<> (:time message)))))))
    (div {} (<> "toolbar")))))
