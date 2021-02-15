# Lucferbux Android
> Portfolio in Android with MVVM

## Introduction

The application uses Clean Architecture based on MVVM and Repository patterns. Implemented
Architecture principles follow Google recommended [Guide to app architecture](https://developer.android.com/jetpack/docs/guide).

![Guide to app architecture](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

The application is written entirely in Kotlin.

Android Jetpack is used as an Architecture glue including but not limited to ViewModel, LiveData,
Lifecycles, Navigation, Firebase and Data Binding. 

The application does network over Firebase SDK, then it uses the offline capabilities to store data.

Kotlin Coroutines manage background threads with simplified code and reducing needs for callbacks.
Combination of Coroutines and Kotlin build in functions (transformation, collections) are preferred
over RxJava 2.

Work manager does synchronisation job being compatible with Doze Mode and using battery efficiently.
Navigation component manages in-app navigation.

Glide is used for image loading.

This app consist in several views: Navigation sections, detail and a planned ARCore Section.

Getting Started
---------------
This project uses the Gradle build system. To build this project, use the
`gradlew build` command or use "Import Project" in Android Studio.

There are two Gradle tasks for testing the project:
* `connectedAndroidTest` - for running Espresso on a connected device
* `test` - for running unit tests

Screenshots
-----------

![Home](https://user-images.githubusercontent.com/16117276/107965953-7cded080-6fab-11eb-92b8-511e4d00916a.png)
![Aboutme](https://user-images.githubusercontent.com/16117276/107965959-7e0ffd80-6fab-11eb-8be4-939192a2b4ee.png)
![Home Dark](https://user-images.githubusercontent.com/16117276/107965962-7ea89400-6fab-11eb-99d7-5d09513ceb8d.png)



Android Studio IDE setup
------------------------
For development, the latest version of Android Studio is required. The latest version can be
downloaded from [here](https://developer.android.com/studio/).

App uses [ktlint](https://ktlint.github.io/) to enforce Kotlin coding styles.
Here's how to configure it for use with Android Studio (instructions adapted
from the ktlint [README](https://github.com/shyiko/ktlint/blob/master/README.md)):

- Close Android Studio if it's open

- Download ktlint using these [installation instructions](https://github.com/shyiko/ktlint/blob/master/README.md#installation)

- Inside the project root directory run:

  `./ktlint --apply-to-idea-project --android`

- Start Android Studio
