
(ns app.twig.user (:require [recollect.twig :refer [deftwig]]))

(deftwig twig-user (user) (select-keys user [:id :name :nickname :avatar]))
