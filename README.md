# torch-cljs
A simple classpath manager for lumo ClojureScript projects.

# Movtivation
[Lumo](https://github.com/anmonteiro/lumo) is an interesting project that allows ClojureScript code targeting NodeJS to run quickly, without compilation, and depend easily on Node modules from NPM. The fact that lumo isn't tied to the JVM and is able to leverage the NPM ecosystem makes it an interesting platform for which to develop. The goal of torch-cljs is to make it easy to depend on other ClojureScript files in your project, as well as depend on ClojureScript NPM packages.

# Dependencies
Torch depends on lumo, and will not work unless lumo is on the path.

# Installation
`npm install -g torch-cljs`

# Usage
Torch projects should be set up in a similar manner to traditional Clojure projects. There should be a source directory or directories that contain directories with names that corrospond to the namespaces of the files (with '-' replaced by '_'). Instead of a build.boot or project.clj, Torch leverages the package.json in order to make integration with NPM easier. In the package.json, define a "cljs-source" property to be an array of source directories. Define the "main" property to be a path to the entrypoint of your project. If the "dependencies" property is up to date, this should be sufficient for running Torch. Simply run the `torch` command in the root of the project.

# NPM Integration
Torch projects can be easily published to NPM, as it uses the package.json file. In addition, valid Torch projects can depend on other valid Torch projects hosted on NPM. Simply `npm install --save <package-name>` and require the namespace in your `ns` macro and Torch ensures that the file is on the classpath, and that the namespace is useable.
