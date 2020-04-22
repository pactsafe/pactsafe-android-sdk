![Package Publish CI](https://github.com/pactsafe/pactsafe-android-sdk/workflows/Package%20Publish%20CI/badge.svg)
# PactSafe Android SDK

- [Requirements](#requirements)
- [Notes Before Getting Started](#notes-before-getting-started)
- [Installation](#installation)
- [Configure and Initalize the PactSafe SDK](#configure-and-initalize-the-pactsafe-sdk)
    - [Preloading Clickwrap Data](#preloading-clickwrap-data)
- [PSClickWrapActivity](#psclickwrapactivity)
    - [Starting a Clickwrap Activity](#starting-a-click-wrap-activity)
    - [Configure Contracts Link Behavior](#configure-contracts-link-tap-behavior)
    - [Check if Checkbox is Selected](#check-if-checkbox-is-selected)
	- [Sending Acceptance](#sending-acceptance)
- [Checking Acceptance](#checking-acceptance)
- [Sending Activity Manually](#sending-activity-manually)
- [Customizing Acceptance Data](#customizing-acceptance-data)
	- [Connection Data](#connection-data)
	- [Custom Data](#custom-data)

## Requirements

- Android Min SDK 22
- PactSafe Published Contracts in Public Group
- PactSafe Group Key
- PactSafe Site Access ID
- PactSafe API Access

## Notes Before Getting Started
Both the SDK and Demo app are written in Kotlin

### Demo Android App
As you follow along in this guide, you may want to look at the PactSafe Android Demo App as an example.

## Installation

First, aurthorize your app to use GitHub Packages: [GitHub Packages Authorization](https://help.github.com/en/packages/using-github-packages-with-your-projects-ecosystem/configuring-gradle-for-use-with-github-packages)

Add the following dependency to your `build.app` gradle file. 
```kotlin
implementation("com.pactsafe.androidsdk:{Version}")
```
## Configure and Initalize the PactSafe SDK
It is recommended that you initialize the sdk in `onCreate` in your `MainApplication` class. Your call might look something like this: 

```kotlin
PSApp.init(
            "Site Access ID",
            "Group Key",
            this,
            debug = true,
            testData = true
        )
```
### Preloading Clickwrap Data

Since your `PSClickWrapActivity` class will load contracts for the specified PactSafe group, you may want to preload the data using your group key before displaying the clickwrap. By preloading, a user will be less likely see loading when they get to the screen that contains the `PSClickWrapActivity`.

To preload your PactSafe group data, you can use the `preload` method on the PSApp object within your `MainApplication`. Example below:

```kotlin
PSApp.preload()
```
This will fetch group data and cache it for later use.

Note: The `debug` and `testData` flags are defaulted to `false`.

### Debug Mode
Setting the `debug` flag to true will display additional information in your console.

### Test Mode
Optionally, set `testMode` to true as you are testing your implementation. This allows you to delete test data in your PactSafe site.

Note: Don't forget to turn `testMode` off before you are finished!

### Data Types

Before you start to implement, you may want to become familiar with a few data types used by the Android SDK.

| Name             | Description                                                  |
| ---------------- | ------------------------------------------------------------ |
| PSSignerID       | `PSSignerID` is a typealias for a String.                    |
| PSSigner         | `PSSigner` is an object that you'll use to send over your signer information. You must include a `signerId`, which is a `PSSignerID` or String that holds your unique signer ID that PactSafe holds. You can optionally pass over additional custom data with a `PSCustomData` object, which is covered below. |
| PSCustomData     | `PSCustomData` holds additional information about your signer and can be customized. Please see the properties that are available to be set in the [Customizing Acceptance Data](#customizing-acceptance-data) section. |
| PSGroup          | `PSGroup` is an object that holds information about a speciifc group (uses PactSafe group key) that is loaded from the PactSafe API. |
| PSContract       | `PSContract` is an object that holds information about contracts within a PactSafe `PSGroup`. |
| PSConnectionData | The `PSConnectionData` object [Customizing Acceptance Data](#customizing-acceptance-data) section. |

## PSClickWrapActivity

The easiest way of getting started with using the PactSafe clickwrap is by utilizing `PSClickWrapActivity` class to dynamically load your contracts into a Layout. The `PSClickWrapActivity` class extends AppCompatActivity, which allows you to easily customize and format the clickwrap as needed.

```kotlin
class YourActivity: PSClickWrapActivity() {}
```

### Starting a Clickwrap activity
There are three types of clickwraps available from the SDK: 
1. Checkbox Acceptance
2. Alert Modal Acceptance
3. Checkbox Within Existing View

`PSClickWrapActivity` provides and easy way to create either of the first two. 
`PSClickWrapActivity.create()` accepts, along with `Context` and `Class<T>`, `ClickWrapType`. Choose from `CHECKBOX` or `ALERT`.

You may start an activity like so: 

```kotlin
startActivity(
    PSClickWrapActivity.create(
                    this,
                    YourClickwrapActivity::class.java,
                    PSClickWrapActivity.ClickWrapType.CHECKBOX
                )
)
```

### Interacting with Your Clickwrap

`PSClickWrapActivity` requires implementation of the following methods: 

```kotlin 
override fun onPreLoaded(psGroup: PSGroup) {}

override fun onContractLinkClicked(title: String, url: String) {}

override fun onAcceptanceComplete(checked: Boolean) {}

override fun onSendAgreedComplete(downloadUrl: String) {}

override fun onSignedStatusFetched(status: Map<String, Boolean>) {}
```


#### Configure Contracts Link Tap Behavior

In the third case, you may be uilding an acceptance view for your users to create a user, for instance, you can utilalize `PSCheckBoxView` in your layout as so: 

```xml
<com.pactsafe.pactsafeandroidsdk.ui.PSCheckBoxView
            android:id="@+id/ps_checkbox_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>
```
If you wish the contract links to use your OS's native browser when tapped, just set the `useOSBrowser` attribute to `true`.  It defaults to false.

When using the `CHECKBOX` Clickwrap, you can set this preference directly on the Activity by calling the `setUsesOSBrowser(Boolean)` function.

If you use the default link tap setting, you will be handed a url in the `onContractLinkClicked` function.  Your implementation may look someting like: 
```kotlin
override fun onContractLinkClicked(title: String, url: String) {
        loadWebView(title, url)
}
```

#### Check if Checkbox is Selected

When your users tick the checkbox, you will be alerted via the `onAcceptanceComplete` function. Here you might enable a submit button or something similar so that you can send acceptance assured that the box has been ticked.

```kotlin
override fun onAcceptanceComplete(checked: Boolean) {
        btn_login.isEnabled = checked
}
```


#### Sending Acceptance

If you're using the `PSCheckBoxView` directly in your PS create a `PSSigner` object and include any custom data you wish. When you're ready, invoke the `sendAgreed` method available on the Activity. It will require a `PSSigner` object and an `EventType`
```kotlin
btn_signup.setOnClickListener {

            it.isEnabled = false

            val signer = PSSigner(
                edit_email.text.toString(),
                PSCustomData(edit_first_name.text.toString(), edit_last_name.text.toString())
            )

            sendAgreed(signer, EventType.AGREED)
        }
```
Once the acceptance has been submitted successfully, you will be notified via `onSendAgreedComplete`.

```kotlin
override fun onSendAgreedComplete(downloadUrl: String) {
        navigateToHome()
}
```
## Checking Acceptance
##### Receive Notice of Acceptance
In order to determine if a user has accepted all of the latest contract language, you may invoke the `fetchSignedStatus` method on the Activity. This will return a `Map<String, Boolean>` called `status` for any contracts that need accepted. You'll need to see if there any in the list that require attention. If so, you must create a `PSSigner` object using the `username` and pass it to `showTermsIntercept` on the Activity.  An easy way to do so is: 
```kotlin
override fun onSignedStatusFetched(status: Map<String, Boolean>) {

        val updateSignedStatus = status.values.any { !it }

        val signer = PSSigner(edit_username.text.toString())

        if (updateSignedStatus) {
            showTermsIntercept(ALERT_TYPE, status, signer)
        } else {
            navigateToHome()
        }
}
```
Note: `ALERT_TYPE` is set `onCreate` for the parent activity and may be accessed here. Depending on the type of activity you've created, it will indicate to PSClickWrapActivity how to behave.

## Sending Activity Manually
In the occurence that you would need to send an Activity Manually, you may simply invoke `PSApp.sendActivity` directly. This call will return a `Single<Boolean>` observable.  If you don't already, in this case, you will need to implement RXJava/RxAndroid

## Customizing Acceptance Data
### Connection Data
Below, you'll find information on what to expect the SDK to send over as part of the activity event as "Connection Data", which is viewable within a PactSafe activity record. Many of the properties are set upon initialization except the optional properties (marked as optional below) using the following Apple APIs: `UIDevice`, `Locale`, and `TimeZone`. If you need further information about these properties, please reach out to us directly.

| Property                | Description                                                  | Overridable |
| ----------------------- | ------------------------------------------------------------ | ----------- |
| `clientLibrary`         | The client library name being used that is sent as part of the activity. | No          |
| `clientVersion`         | The client library version being used that is sent as part of the activity. | No          |
| `deviceFingerprint`     | The unique identifier that is unique and usable to this device. | No          |
| `environment`           | The mobile device category being used (e.g,. tablet or mobile). | No          |
| `operatingSystem`       | The operating system and version of the device.              | No          |
| `screenResolution`      | The screen resolution of the device.                         | No          |
| `browserLocale`         | The current locale identifier of the device.                 | Yes         |
| `browserTimezone`       | The current time zone identifier of the device.              | Yes         |
| `pageDomain` (Optional) | The domain of the page being viewed. *Note: This is normally for web pages but is available to be populated if needed.* | Yes         |
| `pagePath` (Optional)   | The path of the page being viewed. *Note: This is normally for web pages but is available to be populated if needed.* | Yes         |
| `pageQuery` (Optional)  | The query path on the page being viewed. *Note: This is normally for web pages but is available to be populated if needed.* | Yes         |
| `pageTitle` (Optional)  | The title of the page being viewed. *Note: This is normally for web pages but is available to be populated if you'd like to use the title of the screen where the PactSafe activity is occurring.* | Yes         |
| `pageUrl` (Optional)    | The URL of the page being viewed. Note: This is normally for web pages but is available to be populated if needed. | Yes         |
| `referrer` (Optional)   | The referred of the page being viewed. *Note: This is normally for web pages but is avaialble to be populated if needed.* | Yes         |

### Custom Data

Custom Data will typically house additional information that you'd like to pass over that will be appended to the activty event. By adding Custom Data to the event, you'll be able to search and filter based on specific custom data within the PactSafe app, which can be beneficial when you have many activity events.

Before sending an activity event, you may want to customize properties on `PSCustomData` that can be set. Be sure to note that properties such as `firstName`, `lastName`, `companyName`, and `title` that are properties on `PSCustomData` are reserved for PactSafe usage only (like seeing the name of an individual within the PactSafe app).

| Property        | Description                                                  | Overridable |
| --------------- | ------------------------------------------------------------ | ----------- |
| `androidDeviceName` | The name of the user's Android device (e.g., John Doe's Pixel XL). | No          |
| `firstName`     | First Name is a reserved property for custom data in PactSafe but can be set. | Yes         |
| `lastName`      | Last Name is a reserved property for custom data in PactSafe but can be set. | Yes         |
| `companyName`   | Company Name is a reserved property for custom data in PactSafe but can be set. | Yes         |
| `title`         | Title is a reserved property for custom data in PactSafe but can be set. | Yes         |