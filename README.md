
# Android Promise Repository [![](https://jitpack.io/v/android-promise/model.svg)](https://jitpack.io/#android-promise/model)

The promise repositories library

### Table of Contents
**[Setup](##Setup)**<br>
**[Initialization](##Initialization)**<br>
**[Repositories](##Rrepositories)**<br>
**[PreferenceStore](##PreferenceStore)**<br>
**[PreferenceDatabase](##PreferenceDatabase)**<br>
**[Caching](##Caching)**<br>
**[Next Steps, Credits, Feedback, License](#next-steps)**<br>

## Setup

#### build.gradle
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

android {
    compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
}

dependencies {
     implementation 'com.github.android-promise:model:TAG'
     implementation 'com.github.android-promise:commons:1.1-alpha07'
}

```
## Initialization
Initialize Promise in your main application file, entry point

#### App.java
```java
public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    // 10 is the number of threads allowed to run in the background
    AndroidPromise.init(this, 10, BuildConfig.DEBUG);
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    AndroidPromise.instance().terminate();
  }
}

```

## Repositories
A repository is a virtual store for items of the same data type, This store can be queried to fetch, add, update and remove items. For instance, a repository of contacts can be queried to get the contacts, a contact can be added to the repository, 
a contact can be updated as well as removed from the repository.

Manipulating items in the store can be done synchronously or asynchronously.

Manipulating synchronously is beneficial when consuming the repository from background services and asynchronously beneficial when consuming the repository from UI thread.

For this reason, each repository must take in asynchronous and synchronous stores.

Each store can then have the network interfaces, databases, cache to where it retrieves this data.

A repository should not do any form of data manipulation. 

A repository just merges the different data sources appears as if its one source of data.

> Note these repositories once created exist throught the lifecycle of the whole application, If a change happens that requires the 
> repositories to be cleared of the system, for instance a user logout, use clear static method of the repository to cleare cached
> repositories


##### Will cover this in the next sections
```kotlin
class ComplexModel : Identifiable<Int> {
  var uId = 0
  var name: String = ""
  var isModel = false

  override fun toString(): String = "ComplexModel(id=$uId)"
  override fun getId(): Int = uId
  override fun setId(t: Int) {
    this.uId = t
  }
}

const val NUMBER_ARG = "number_arg"
const val TIMES_ARG = "times_arg"
const val ID_ARG = "id_arg"

/**
 * sample store for complex models
 *
 */
class SyncComplexModelStore(private val preferenceDatabase: PreferenceDatabase<Int, ComplexModel>) : AbstractSyncIDataStore<ComplexModel>() {
  val TAG = LogUtil.makeTag(SyncComplexModelStore::class.java)

  override fun all(args: Map<String, Any?>?): Pair<List<ComplexModel>?, Any?> {
    if (args == null) throw IllegalArgumentException("number and times args must be passed")
    val number = args[NUMBER_ARG] as Int
    val times = args[TIMES_ARG] as Int
    LogUtil.e(TAG, "repo args ", number, times)
    val savedModels = preferenceDatabase.all().first
    return if (savedModels.isEmpty()) {
      val models = List<ComplexModel>()
      (0 until number * times).forEach {
        models.add(ComplexModel().apply {
          uId = it + 1
          name = getId().toString() + "n"
          isModel = getId().rem(2) == 0
        })
      }
      Pair(models, preferenceDatabase.save(models))
    } else Pair(List(savedModels), "from cache")
  }

  override fun one(args: Map<String, Any?>?): Pair<ComplexModel?, Any?> {
    if (args == null || !args.containsKey(ID_ARG)) throw IllegalArgumentException("ID_ARG must be passed in args")
    return preferenceDatabase.one(args)
  }
}

class AsyncComplexModelStore : AbstractAsyncIDataStore<ComplexModel>()

val complexStore: StoreRepository<ComplexModel> by lazy {
  StoreRepository.of(
      SyncComplexModelStore::class,
      AsyncComplexModelStore::class,
      arrayOf(preferenceDatabase))
}
```
#### Repo Setup
Each repository should have two stores, asynchronous store and synchronous store.

##### Asynchronous store
```kotlin
class AsyncComplexModelStore : AbstractAsyncIDataStore<ComplexModel>()
```
In this example, no methods are overriden as the asynchronous version of the repo is not consumed
##### Synchronous store
```kotlin
class SyncComplexModelStore(private val preferenceDatabase: PreferenceDatabase<Int, ComplexModel>) : AbstractSyncIDataStore<ComplexModel>() {
  val TAG = LogUtil.makeTag(SyncComplexModelStore::class.java)

  override fun all(args: Map<String, Any?>?): Pair<List<ComplexModel>?, Any?> {
    if (args == null) throw IllegalArgumentException("number and times args must be passed")
    val number = args[NUMBER_ARG] as Int
    val times = args[TIMES_ARG] as Int
    LogUtil.e(TAG, "repo args ", number, times)
    val savedModels = preferenceDatabase.all().first
    return if (savedModels.isEmpty()) {
      val models = List<ComplexModel>()
      (0 until number * times).forEach {
        models.add(ComplexModel().apply {
          uId = it + 1
          name = getId().toString() + "n"
          isModel = getId().rem(2) == 0
        })
      }
      Pair(models, preferenceDatabase.save(models))
    } else Pair(List(savedModels), "from cache")
  }

  override fun one(args: Map<String, Any?>?): Pair<ComplexModel?, Any?> {
    if (args == null || !args.containsKey(ID_ARG)) throw IllegalArgumentException("ID_ARG must be passed in args")
    return preferenceDatabase.one(args)
  }
}
```
In this store, all method is overriden to fetch all items from the preferenceDatabase, one method is overriden to fetch one item from preference database

> If there's need to pass extra arguments to the store methods, these arguments are passed in the args parameter, 
> In our case, number and times are passed through the args parameter of the all method

##### Repository object
```kotlin
val complexStore: StoreRepository<ComplexModel> by lazy {
  StoreRepository.of(
      SyncComplexModelStore::class,
      AsyncComplexModelStore::class,
      arrayOf(preferenceDatabase))
}
```
The static of method models the object, passed to it are the two classes and array of parameters of constructors of the classes, for instance, the SyncComplexModelStore takes PreferenceDatabase dependency, so an instance of Preference database is passed in in arrayOf argument of Repository.of method

### Consuming the repository
Our repository is consumed in the main activity.
> note here it is consumed synchronously as we only implemented methods in the synchronous store of the repo,
> this all method of the Repo also takes in callbacks, providing those callbacks would consume the repo asynchronously
> and we'll have to implement methods in the asynchronous store of the repo
```kotlin
...
override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    val models = complexStore.all(ArrayMap<String, Any>().apply {
      put(NUMBER_ARG, 10)
      put(TIMES_ARG, 2)
    })
    val result = "${models.first.toString()} \n meta \n ${models.second}"
    main_textview.text = result
  }
...
```

> We have to pass the number and times arguments to the map argument

## PreferenceStore
This is useful for storing a list of items in a key in a preference file
```kotlin
val preferenceStore: PreferenceStore<ComplexModel> = object : PreferenceStore<ComplexModel>("name of file", 
    object: DoubleConverter<ComplexModel, JSONObject, JSONObject> {
      override fun deserialize(e: JSONObject): ComplexModel = ComplexModel().apply { 
        uId = e.getInt("uId")
        name = e.getString("name")
        isModel = e.getBoolean("isModel")
      }

      override fun serialize(t: ComplexModel): JSONObject = JSONObject().apply { 
        put("uId", t.uId)
        put("name", t.name)
        put("isModel", t.isModel)
      }
    }) {
  override fun findIndexFunction(t: ComplexModel): FilterFunction<JSONObject> =
      FilterFunction<JSONObject> { t.uId == it.getInt("uId") }

}
```
#### Consuming the preference store
This is like consuming a normal preference file, all the crud functionality take a key, in this case, 'models'
Saving items
```kotlin
preferenceStore.save("models", 
        List.fromArray(ComplexModel().apply { 
          uId = 3
          name = "name"
          isModel = true
        }, ComplexModel().apply {
          uId = 5
          name = "name1"
          isModel = false
        }),
        PromiseResult<Boolean, Throwable>()
        .withCallback { 
          
        })
```
Retrieving items
```kotlin
preferenceStore.get("models", PromiseResult<Store.Extras<ComplexModel>, Throwable>()
        .withCallback {
          val items = it.all()
          // use items
        })
```
## PreferenceDatabase
This is database implemented in preferences, it uses the whole preference file as the underlying database
In this case, the keys in the preference file will be integers
Setup
```kotlin
val preferenceDatabase: PreferenceDatabase<Int, ComplexModel> = PreferenceDatabase("models",
    object : DoubleConverter<Int, String, String> {
      override fun deserialize(e: String): Int = e.toInt()
      override fun serialize(t: Int): String = t.toString()
    },
    object : DoubleConverter<ComplexModel, JSONObject, JSONObject> {
      override fun deserialize(e: JSONObject): ComplexModel = ComplexModel().apply {
        name = e.getString("name")
        isModel = e.getBoolean("isModel")
      }

      override fun serialize(t: ComplexModel): JSONObject = JSONObject().apply {
        put("name", t.name)
        put("isModel", t.isModel)
      }
    })
```
Consuming the database
> number and times are provided abitrarily for the loop
```kotlin
...
val savedModels = preferenceDatabase.all().first
    if (savedModels.isEmpty()) {
      val models = List<ComplexModel>()
      (0 until number * times).forEach {
        models.add(ComplexModel().apply {
          uId = it + 1
          name = getId().toString() + "n"
          isModel = getId().rem(2) == 0
        })
      }
      preferenceDatabase.save(models)
```
## Caching
Retrieving and storing data from app cache
```kotlin
CacheUtil.instance().writeObject("/complexModel/1",  ComplexModel().apply {
      uId = 5
      name = "name1"
      isModel = false
    })
    
val complexModel = CacheUtil.instance().readObject("/complexModel/1", ComplexModel::class.java)
```

## New features on the way
watch this repo to stay updated

# Developed By
* Peter Vincent - <dev4vin@gmail.com>
# Donations
If you'd like to support this library development, you could buy me coffee here:
* [![Become a Patreon]("https://c6.patreon.com/becomePatronButton.bundle.js")](https://www.patreon.com/bePatron?u=31932751)

Thank you very much in advance!

#### Pull requests / Issues / Improvement requests
Feel free to contribute and ask!<br/>

# License

    Copyright 2018 Peter Vincent

    Licensed under the Apache License, Version 2.0 Android Promise;
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

