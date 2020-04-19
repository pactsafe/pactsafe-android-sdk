# PactSafe Android SDK

- [Requirements](#requirements)
- [Notes Before Getting Started](#notes-before-getting-started)
- [Installation](#installation)
- [Configure and Initalize the PactSafe SDK](#configure-and-initalize-the-pactsafe-sdk)
- [PSClickWrapView](#psclickwrapview)
	- [Preloading Clickwrap Data](#preloading-clickwrap-data)
    - [Layout](#layout)
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
Both the sdk and Demo app are written in Kotlin

### Demo Android App
As you follow along in this guide, you may want to look at the PactSafe Android Demo App as an example.

## Installation
Add the following dependency to your `build.app` gradle file. 
```kotlin
implementation("com.pactsafe.androidsdk:{Version}")
```
## Configure and Initalize the PactSafe SDK
It is recommended that you initialize the sdk in the `onCreate` in your `MainApplication` class. Your call might look something like this: 

```
PSApp.init(
            BuildConfig.SITE_ACCESS_ID,
            BuildConfig.GROUP_KEY,
            this,
            debug = true,
            testData = true
        )
```

*Note that the `debug` and `testData` flags are defaulted to `false`.

### Debug Mode
Something not quite working the way you expect or you need additional information as to what might not be working? No problem. Simply enable the `debugMode` property on `PSApp.shared`.

### Test Mode
Optionally, set `testMode` to true as you are testing your implementation. This allows you to delete test data in your PactSafe site.

Note: Don't forget to remove this line before you are finished!

### Data Types

Before you start to implement, you may want to become familiar with a few data types used by the iOS SDK.

| Name             | Description                                                  |
| ---------------- | ------------------------------------------------------------ |
| PSSignerID       | `PSSignerID` is a typealias for a String.                    |
| PSSigner         | `PSSigner` is a struct that you'll use to send over your signer information. You must include a `signerId`, which is a `PSSignerID` or String that holds your unique signer ID that PactSafe holds. You can optionally pass over additional custom data with a `PSCustomData` struct, which is covered below. |
| PSCustomData     | `PSCustomData` holds additional information about your signer and can be customized. Please see the properties that are available to be set in the [Customizing Acceptance Data](#customizing-acceptance-data) section. |
| PSGroup          | `PSGroup` is a struct that holds information about a speciifc group (uses PactSafe group key) that is loaded from the PactSafe API. |
| PSContract       | `PSContract` is a struct that holds information about contracts within a PactSafe `PSGroup`. |
| PSConnectionData | The `PSConnectionData` struct [Customizing Acceptance Data](#customizing-acceptance-data) section. |

## PSClickWrapView

### Preloading Clickwrap Data

### Loading Your Clickwrap


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