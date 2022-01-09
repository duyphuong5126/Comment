## Table of content
* Done items
* Architecture & Techniques
* Project structure
* Main libraries

## Done items
Features:
* Users can input comments using the text input component. Every comment will show up on a list. 
* The app will extract links and mentions from every comment and add them to that list.
* Use `CommentInfoExtractor` as a library to extract links and mentions from comments.

## Architecture & Techniques
* Architecture: Clean Architecture
* Main principles: SOLID principles
* Pattern: MVVM, Repository, Factory
* Important techniques and libraries: ReactiveX (with RxJava)

## Project structure
Main packages:
* `domain`: defines the domain layer that contains the business logic of the app, including:
	      * Entity: the main entities of the app, including: `Links`, `Mentions`.
	      * Use-case: the use-cases that can happen within the app, including extracting links and mentions from comments.
	      * Repository interface: can be used by the use-cases to extract links and mentions from comments.

* `presentation`: defines the presentation layer. This layer communicates with the `domain` layer to extract links and mentions from comments and transform them into displayable data. This package includes:
	      * View Model
	
* `data`: defines the implementation of the repository and its gateways.
	      * Repository implementation
	      * Remote data source: the remote gateway of the repository. This gateway gets the title of any website using its URL. 
	      * `CommentInfoExtractor`: a service to extract links and mentions from comments.

* `uimodel`: the data models that can display on UI without further transformations.

* `factory`: defines some factory classes to instantiate the classes from the following packages: `domain`, `presentation`, and `data`.



## Main libraries
* RxJava
* Material
* Gson
* Jsoup (to get website title from URLs)
* Mockito
* Junit
