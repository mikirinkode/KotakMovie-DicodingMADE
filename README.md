# KotakMovie-DicodingMADE
A movie catalogue app that implement clean architecture, MVVM, Hilt, ROOM, Coroutines, Flow. This project is also a submission for Dicoding Class Menjadi Android Developer Expert.

<br>

<img src="https://github.com/mikirinkode/KotakFilm-DicodingMADE/blob/master/previews/kotakmovie_preview.png" alt="KotakMovie Preview">

## Tech Stack
- Minimum SDK Level 21
- <a href="https://kotlinlang.org/">Kotlin</a> based
- Coroutines + Flow for asynchronous
- <a href="https://dagger.dev/hilt/">Dagger Hilt</a> for dependency Injection
- Implement Clean Architecture and MVVM Architecture
- <a href="https://developer.android.com/reference/android/arch/persistence/room/RoomDatabase"></a>ROOM for local database
  - +Database Encryption using <a href="https://github.com/sqlcipher/sqlcipher">SQLCipher</a>
- <a href="https://github.com/square/retrofit">Retrofit2</a> to get data from Network
  - +Certificate Pinning
- Modularization
- Dynamic Feature
- Obfuscation with Proguard
- <a href="https://github.com/bumptech/glide">Glide</a> to load an image
- <a href="https://github.com/facebook/shimmer-android">Shimmer</a> as shiny loading
- <a href="https://github.com/PierfrancescoSoffritti/android-youtube-player">Youtube Video Player</a> to play movie trailer in app
- <a href="https://github.com/androidbroadcast/ViewBindingPropertyDelegate">View Binding Delegate</a> to make view binding simpler

## Features
- Search Movie
- Play Movie trailer directly in the app
- Add Movie to a playlist
- Share Movie to other application

## If you want to Clone this Repo
You need to have an API Key, because the data is from <a href="https://www.themoviedb.org/">TheMovieDatabase</a>. To get the api key you can go to this <a href="https://developers.themoviedb.org/3/">link</a>, sign up and you will get one. Then you can put it at Constants.kt class in Core module (package: com.mikirinkode.kotakmovie.core.utils)
```
class Constants {
    companion object{
        ....
        
        const val API_KEY = "Change this with your own api key"
        
        ....
    }
}
```
