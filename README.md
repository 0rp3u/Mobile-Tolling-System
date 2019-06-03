### Mobile Tolling System

This application was developed as final project for the course of computer science at [ISEL].

<a href="https://github.com/0rp3u/Mobile-Tolling-System/raw/master/Documentation/views_graph.png">
<img src="https://github.com/0rp3u/Mobile-Tolling-System/raw/master/Documentation/views_graph.png">
</a>


The application explores the possibility of providing an alternative for OBU units used for tolling in Portugal, it lets the user:

* Start tolling detection;
* Visualize current position and tolls detected;
* Correct possible errors in detection;
* Visualize history of tolls used;
* Use multiple vehicles for tolling;
* Receive notifications of interest;
* Use functionalities in offline mode.

### 
The source of data is provided from an API developed by my project colleague, its main responsibility is to do the geo-spatial calculations used for detection and for data persistence.

### Technologies
Principal technologies used for the development of this project.

| Technology | WHERE | README |
| ------ | ------ | ------ |
| [Kotlin] |App & Backend | Concise programming language that fully supports Android development |
| [Coroutines] |App | Powerful library for asynchronous programming |
| [Google play services] |App | Services used to provide maps and geo-localization information  |
| [Retrofit] |App | type safe HTTP client library|
| [Room] |App |  Library provides an abstraction layer over SQLite for data persistence on mobile device |
| [Work Manager] |App | Android API for background scheduling that respects Android O background restrictions |
| [Dagger] |App | Dependency injector |
| [Leak Canary] |App | Memory leak detection library |
| [MVP] |App | Architectural pattern used for responsibility separation |

[ISEL]: <https://www.isel.pt/en>
[Kotlin]: <https://kotlinlang.org/>
[Coroutines]: <https://kotlinlang.org/docs/reference/coroutines-overview.html/>
[Retrofit]: <https://square.github.io/retrofit/>
[Google play services]: <https://developers.google.com/android/guides/overview>
[Room]: <https://developer.android.com/topic/libraries/architecture/room>
[Work Manager]: <https://developer.android.com/topic/libraries/architecture/workmanager>
[Dagger]: <https://google.github.io/dagger/>
[Leak Canary]: <https://github.com/square/leakcanary>
[MVP]: <https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter>

