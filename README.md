# Android Promise Repository

The promise repositories library

# Setup
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
     implementation 'com.github.android-promise:commons:Tag'
     implementation 'com.github.android-promise:model:Tag'
}
```

# Initialization
Initialize Promise in your main application file, entry point

#### App.java
```java
  @Override
  public void onCreate() {
    super.onCreate();
    Promise.init(this);
  }
```

> Note these repositories once created exist throught the lifecycle of the whole application, If a change happens that requires the 
> repositories to be cleared of the system, for instance a user logout, use clear static method of the repository to cleare cached
> repositories

#### Stay Updated

# Wiki!
I strongly recommend to read the **[Wiki](https://github.com/android-promise/model/wiki) pages**, where you can find a comprehensive Tutorial.<br/>

### Pull requests / Issues / Improvement requests
Feel free to contribute and ask!<br/>

# License

#### Android Promise

    Copyright 2018 Peter Vincent

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

