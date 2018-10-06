
(ns app.config (:require [app.util :refer [get-env!]]))

(def bundle-builds #{"release" "local-bundle"})

(def dev?
  (if (exists? js/window)
    (do ^boolean js/goog.DEBUG)
    (not (contains? bundle-builds (get-env! "mode")))))

(def site
  {:storage-key "books-may-talk",
   :port 11013,
   :title "Books may talk",
   :icon "http://cdn.tiye.me/logo/cumulo.png",
   :dev-ui "http://localhost:8100/main.css",
   :release-ui "http://cdn.tiye.me/favored-fonts/main.css",
   :cdn-url "http://cdn.tiye.me/books-may-talk/",
   :cdn-folder "tiye.me:cdn/books-may-talk",
   :upload-folder "tiye.me:repo/TopixIM/books-may-talk/",
   :server-folder "tiye.me:servers/books-may-talk",
   :theme "#eeeeff"})
