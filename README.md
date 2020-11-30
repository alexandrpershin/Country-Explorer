# Solution summary

## About app architecture

I used Single Activity Application approach and Model-View-ViewModel architectural pattern.

## Tech stack
 
 1. Kotlin Coroutines for network requests and background operations.
 2. Room database for data persistence.
 3. Navigation controller for navigation between fragments.
 4. Koin library for dependency injection.
 5. Chuck interceptor library to visualise network requests (is disabled for release version).
 6. Ktx library for some cool extensions.
 7. Retrofit library for networking.
 8. Glide for loading images.
 9. Toasty library to show beautiful toasts.

## How app works?

1. Countries list screen <br />
At this screen app loads all countries from server and stores it in database.
Data from database is displayed on the screen. If data is empty or internet connectivity 
unavailable - app notifies user about it. To see the country details user can tap on country item.

2. Countries search screen <br /> 
App performs search on server with given country name. To see the country details user can tap on country item.
If data is empty or internet connectivity unavailable - app notifies user about it. 

3. Country details screen <br />
App shows some details of selected country.
 
4. Unit tests  <br />
I implemented some unit tests.
 

