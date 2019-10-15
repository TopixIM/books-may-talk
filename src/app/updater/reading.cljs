
(ns app.updater.reading (:require [app.schema :as schema]))

(defn touch-book [db op-data sid op-id op-time]
  (let [book-id op-data, user-id (get-in db [:sessions sid :user-id])]
    (update-in
     db
     [:users user-id :readings]
     (fn [readings]
       (if (get readings book-id)
         readings
         (assoc
          readings
          book-id
          (merge
           schema/reading
           {:book-id book-id,
            :progress 0,
            :messages {:op-id (merge
                               schema/message
                               {:id op-id,
                                :text "Let's start reading!",
                                :time op-time,
                                :book-index 0,
                                :kind :hint})}})))))))
