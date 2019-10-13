
(ns app.comp.home
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.comp.space :refer [=<]]
            [respo.core :refer [defcomp <> action-> span div]]
            [app.config :as config]))

(defcomp comp-home () (div {:style {:padding "8px 16px"}} (<> "Home")))
