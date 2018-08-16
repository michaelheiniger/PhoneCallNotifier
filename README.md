![phonecallnotifier](https://user-images.githubusercontent.com/37498811/44225890-71c74180-a1b0-11e8-898e-d3843a6c4f57.png)

# PhoneCallNotifier
[![Build Status](https://travis-ci.com/michaelheiniger/PhoneCallNotifier.svg?branch=master)](https://travis-ci.com/michaelheiniger/PhoneCallNotifier)

This small Android app allows to define simple regex of phone numbers in order to reject the calls automatically.
The idea came when one day I received many calls from some number of the Seychelles.
These were obviously attempts to make me answer a toll call. By automatically rejecting the calls coming from some
numbers, I didn't need switch my phone into silent mode to not be bothered.

This is also a way for me to practice programming in general and in particular on the Android platform.
Among the dependencies, the project uses [Dagger2](https://google.github.io/dagger/) as the dependency injection framework, 
[RxJava2](https://github.com/ReactiveX/RxJava),  
[Room persistence library](https://developer.android.com/topic/libraries/architecture/room) and [Conductor](https://github.com/bluelinelabs/Conductor) for views (instead of Android Fragments). 

### Features:
  * Allows the user to define phone number formats that can be enabled or disabled
  * When a phone call is received, if the caller number matches one 
  of the enabled phone number formats defined by the user, 
  the phone call is automatically rejected and a information 
  dialog pops-up to inform the user that a phone call was rejected 
  (with the actual caller number)

### Regex format
The format of a phone number (as understood in this app) can contain the following characters:
  * digits [0-9]
  * plus + which is understood as 00
  * spaces which are ignored (it allows to make the format easier to read for humans)
  * dash # which is a placeholder for one digit. 
  Using # allows to determine the number of digits that contain the numbers to be blocked. There is no wildcard allowing to block numbers with an arbitrary number of digits to avoid user mistakes (which could lead to block unexpected numbers)
Any other characters are ignored

Examples:
  * +41 78 772 23 23 is the same as 0041 78 772 23 23 and corresponds to as single phone number
  * 00248 ### ### ## corresponds to any phone number starting with 00248 followed by any 8 digits
  * 0aq034gx?$ / corresponds to the number 0034
  
  
### Permissions:
  * android.permission.READ_PHONE_STATE: required to detect incoming phone calls
  * android.permission.CALL_PHONE: required to automatically reject the phone call
  
  
