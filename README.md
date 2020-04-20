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
	- [Using the signedStatus Method](#using-the-signedstatus-method)
	- [Using the PSAcceptanceViewController](#using-the-psacceptanceviewcontroller)
	- [Using signedStatus Method and Present Alert](#using-signedstatus-method-and-present-alert)
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
Add the following dependency to your `build.app` gradle file. 
```kotlin
implementation("com.pactsafe.androidsdk:{Version}")
```
## Configure and Initalize the PactSafe SDK
It is recommended that you initialize the sdk in the `onCreate` in your `MainApplication` class. Your call might look something like this: 

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

The easiest way of getting started with using the PactSafe clickwrap is by utilizing our `PSClickWrapActivity` class to dynamically load your contracts into a Layout. The `PSClickWrapActivity` class extends AppCompatActivity, which allows you to easily customize and format the clickwrap as needed.

```kotlin
class YourActivity: PSClickWrapActivity() {}
```

### Starting a Clickwrap activity
There are two types of clickwraps available from the SDK: 
1. Checkbox Acceptance
2. Alert Modal Acceptance

`PSClickWrapActivity` provides and easy way to create either. 
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

#### Check if Checkbox is Selected

#### Sending Acceptance

## Checking Acceptance

##### Receive Notice of Acceptance

### Using signedStatus Method and Present Alert

## Sending Activity Manually

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