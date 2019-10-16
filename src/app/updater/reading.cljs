
(ns app.updater.reading
  (:require [app.schema :as schema]
            [app.cache :refer [books-cache]]
            [clojure.string :as string]))

(defn add-note [db op-data sid op-id op-time]
  (let [book-id (:book-id op-data)
        text (:text op-data)
        user-id (get-in db [:sessions sid :user-id])]
    (update-in
     db
     [:users user-id :readings book-id]
     (fn [reading]
       (update
        reading
        :messages
        (fn [messages]
          (if (string/blank? text)
            (do (println "Not accept empty content") messages)
            (assoc
             messages
             op-id
             (merge
              schema/message
              {:id op-id,
               :time op-time,
               :text text,
               :kind :note,
               :book-index (:progress reading)})))))))))

(defn read-next [db op-data sid op-id op-time]
  (let [book-id op-data
        user-id (get-in db [:sessions sid :user-id])
        book-content (get books-cache book-id)]
    (update-in
     db
     [:users user-id :readings book-id]
     (fn [reading]
       (if (< (:progress reading) (dec (count book-content)))
         (-> reading
             (update :progress inc)
             (update
              :messages
              (fn [messages]
                (assoc
                 messages
                 op-id
                 (merge
                  schema/message
                  {:kind :content,
                   :text (get book-content (:progress reading)),
                   :time op-time,
                   :id op-id,
                   :book-index (:progress reading)})))))
         (-> reading
             (update
              :messages
              (fn [messages]
                (assoc
                 messages
                 op-id
                 (merge
                  schema/message
                  {:kind :hint,
                   :text "Fin.",
                   :time op-time,
                   :id op-id,
                   :book-index (:progress reading)}))))))))))

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
