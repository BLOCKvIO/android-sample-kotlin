# blockv-android-sample

=======================

Android sample application demoing features of the Blockv platform

<h2>Contents</h2>

This sample demos the following features of the BLOCKv Android Sdk

* **Checking if a user is logged in**. [Code](./app/src/main/java/io/blockv/sample/feature/inventory/InventoryPresenterImpl.kt#L46)

* **Logging in via phone number**. [Code](./app/src/main/java/io/blockv/sample/feature/login/LoginPresenterImpl.kt#L49) [Docs](https://developer.blockv.io/docs/reference/v1/android/user/login)

* **Registration**. [Code](./app/src/main/java/io/blockv/sample/feature/registration/RegistrationPresenterImpl.kt#L26) [Docs](https://developer.blockv.io/docs/reference/v1/android/user/register)

* **Fetching current user's profile**. [Code](./app/src/main/java/io/blockv/sample/feature/profile/ProfilePresenterImpl.kt#L35) [Docs](https://developer.blockv.io/docs/reference/v1/android/user/get-profile)

* **Fetching current user's Tokens**. [Code](./app/src/main/java/io/blockv/sample/feature/profile/ProfilePresenterImpl.kt#L48) [Docs](https://developer.blockv.io/docs/reference/v1/android/user/get-user-tokens)

* **Updating current user's profile**. [Code](./app/src/main/java/io/blockv/sample/feature/profile/ProfilePresenterImpl.kt#L70) [Docs](https://developer.blockv.io/docs/reference/v1/android/user/update-profile)

* **Uploading an avatar**. [Code](./app/src/main/java/io/blockv/sample/feature/profile/ProfilePresenterImpl.kt#L148) [Docs](https://developer.blockv.io/docs/reference/v1/android/user/upload-avatar)

* **Logging out**. [Code](./app/src/main/java/io/blockv/sample/feature/profile/ProfilePresenterImpl.kt#L124) [Docs](https://developer.blockv.io/docs/reference/v1/android/user/logout)

* **Fetching user's inventory**. [Code](./app/src/main/java/io/blockv/sample/feature/inventory/InventoryPresenterImpl.kt#L49) [Docs](https://developer.blockv.io/docs/reference/v1/android/user/vatom/inventory)

* **Fetching vAtoms by id**. [Code](./app/src/main/java/io/blockv/sample/feature/activated/ActivatedPresenterImpl.kt#L66) [Docs](https://developer.blockv.io/docs/reference/v1/android/user/vatom/get-by-id)

* **Adding VatomView to a layout**. [Code](./app/src/main/res/layout/activity_vatom_activated.xml#L52)

* **Loading a VatomView**. [Code](./app/src/main/java/io/blockv/sample/feature/activated/ActivatedScreenImpl.kt#L41)

* **Customising the face selection procedure**. [Code](./app/src/main/java/io/blockv/sample/feature/activated/ActivatedScreenImpl.kt#42)

* **Using a VatomView in a RecyclerView**.  [Code Part 1](./app/src/main/java/io/blockv/sample/feature/inventory/InventoryAdapter.kt#L38) [Code Part 2](./app/src/main/java/io/blockv/sample/feature/inventory/InventoryViewHolder.kt#L21)

<h3>Building using Android Studio...</h3>

1. Open Android Studio and select *Open an existing Android Studio project*
1. Select the **blockv-android-example** directory.

<h3>Modify IDs, compile and run</h3>

To set up the example:

1. Change [*replace-with-your-app-id*](./app/src/main/java/io/blockv/sample/feature/BlockvModule.kt#L22) in the BlockvModule.java file to your own **App ID**. See [FAQ](https://developer.blockv.io/docs/faq)
1. Compile and run.

<h3>Building</h3>
To build the sample after you have applied the changes above, use the build/run option in Android Studio.

<h3>Dependencies</h3>

1. [Dagger 2](https://github.com/google/dagger) for singleton management

