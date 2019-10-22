
(ns app.comp.reading-room
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.comp.space :refer [=<]]
            [respo.core
             :refer
             [defcomp <> action-> cursor-> list-> button span div textarea defeffect]]
            [app.config :as config]
            [respo.util.list :refer [map-val]]
            ["dayjs" :as dayjs]
            [respo-alerts.comp.alerts :refer [comp-prompt]]
            [clojure.string :as string]
            [cumulo-util.core :refer [delay!]]))

(defeffect
 effect-bottom
 (reading)
 (reading')
 (action el)
 (comment println "effect" action (set (keys (:messages reading))))
 (case action
   :mount
     (let [target (.querySelector el "#reading-room")]
       (if (some? target)
         (.scroll target (clj->js {:top (.-scrollHeight target), :behavior "smooth"}))
         (js/console.warn "No scroll target")))
   :update
     (let [target (.querySelector el "#reading-room")]
       (if (some? target)
         (when (not= (set (keys (:messages reading))) (set (keys (:messages reading'))))
           (.scroll target (clj->js {:top (.-scrollHeight target), :behavior "smooth"})))
         (js/console.warn "No scroll target")))
   (do (js/console.log "unmount"))))

(defcomp
 comp-reading-room
 (states book reading)
 [(effect-bottom reading)
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
     {:style (merge ui/expand ui/column {})}
     (list->
      {:style (merge
               ui/expand
               {:background-color (hsl 0 0 92),
                :padding "100px 16px 120px",
                :scroll-behavior :smooth}),
       :id "reading-room"}
      (->> (:messages reading)
           (sort-by (fn [[k message]] (:time message)))
           (map
            (fn [[k message]]
              [k
               (if (or (= (:kind message) :hint) (= (:kind message) :content))
                 (div
                  {:style {:margin-bottom 12}}
                  (div
                   {}
                   (<> (or (:author book) "BOOK") {:color (hsl 0 0 60)})
                   (=< 8 nil)
                   (<>
                    (-> (:time message) (dayjs) (.format "MM-DD HH:mm"))
                    {:font-size 12, :color (hsl 0 0 70)}))
                  (div
                   {:style {:background-color (hsl 0 0 100),
                            :padding 6,
                            :border-radius "4px",
                            :line-height "24px",
                            :max-width 600}}
                   (<> (:text message))))
                 (div
                  {:style ui/column}
                  (div
                   {:style ui/row-parted}
                   (span nil)
                   (div
                    {}
                    (<> "Me")
                    (=< 8 nil)
                    (<>
                     (-> (:time message) (dayjs) (.format "MM-DD HH:mm"))
                     {:font-size 12, :color (hsl 0 0 70)})))
                  (div
                   {:style ui/row-parted}
                   (span nil)
                   (div
                    {:style {:background-color (hsl 0 0 100),
                             :padding 6,
                             :border-radius "4px",
                             :line-height "24px",
                             :max-width 600}}
                    (<> (:text message))))))]))))
     (div
      {:style (merge
               ui/row-parted
               {:padding "16px", :border-top (str "1px solid " (hsl 0 0 90))})}
      (span {} (<> (:progress reading)))
      (div
       {}
       (button
        {:style ui/button,
         :inner-text "Next",
         :on-click (fn [e d! m!] (d! :reading/next (:id book)))})
       (=< 8 nil)
       (cursor->
        :note
        comp-prompt
        states
        {:trigger (button {:style ui/button, :inner-text "Add note"}),
         :multiline? true,
         :button-text "Add note",
         :validator (fn [x] (if (string/blank? x) "Need content" nil))}
        (fn [result d! m!] (d! :reading/note {:book-id (:id book), :text result}))))
      (span {}))))])
