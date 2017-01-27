#!/usr/bin/env lumo
(ns aviv.core
	(:require [cljs.nodejs :as nodejs]
            [cognitect.transit :as transit]
            [lumo.classpath :as classpath]
            [lumo.repl :as repl]))

(def fs (nodejs/require "fs"))

(defn read-json
	[path]
  (transit/read (transit/reader :json) (. fs readFileSync path "utf8")))

(defn build-classpath
  ([] (build-classpath [] "."))
	([old-classpath path]
	(let [package (read-json (str path "/package.json"))]
		(if-let [sources (get package "cljs-source")]
      (let [new-classpath (reduce #(conj %1 %2) old-classpath sources)
            dependencies (keys (get package "dependencies"))
            paths (map #(str "node_modules/" %) dependencies)]
        (reduce build-classpath new-classpath paths))
      old-classpath))))

(defn torch-arg?
  [arg]
  (or (= arg "torch") (= arg "./torch") (= arg "torch.cljs") (= arg "./torch.cljs")))

(defn find-torch-arg
  [cmd]
  (first (map
           first
           (filter #(torch-arg? (second %)) (map-indexed vector cmd)))))

(defn build-command
  [argv]
  (reduce #(str %1 " " %2) "" (drop (inc (find-torch-arg argv)) argv)))

(defn -main
  [& args]
  (let [entrypoint (get (read-json "./package.json") "main")]
    (doseq [path (build-classpath)]
      (classpath/add-source! path))
    (repl/execute "file" entrypoint false false nil)))

(-main)
