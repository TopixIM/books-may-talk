
(ns app.twig.container
  (:require [recollect.twig :refer [deftwig]]
            [app.twig.user :refer [twig-user]]
            ["randomcolor" :as color]))

(deftwig
 twig-members
 (sessions users)
 (->> sessions
      (map (fn [[k session]] [k (get-in users [(:user-id session) :name])]))
      (into {})))

(deftwig
 twig-reading-room
 (reading)
 (update
  reading
  :messages
  (fn [messages]
    (->> messages (sort-by (fn [[k message]] (:time message))) (take-last 20) (into {})))))

(deftwig
 twig-container
 (db session records)
 (let [logged-in? (some? (:user-id session))
       router (:router session)
       base-data {:logged-in? logged-in?, :session session, :reel-length (count records)}
       books (:books db)]
   (merge
    base-data
    (if logged-in?
      (let [user (get-in db [:users (:user-id session)])]
        {:user (twig-user user),
         :router (assoc
                  router
                  :data
                  (case (:name router)
                    :home (:books db)
                    :profile (twig-members (:sessions db) (:users db))
                    :reading
                      (let [book-id (:data router)]
                        {:book (get books book-id),
                         :reading (twig-reading-room (get (:readings user) book-id))})
                    {})),
         :count (count (:sessions db)),
         :color (color/randomColor)})
      nil))))
