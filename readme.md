# transformers-battles
Demo app

This app performs battles between Autobots and Decepticons.


### Getting started

1. Download the repository
2. Open Android Studio
3. Include a google-services.json file , in the ./app directory, granting access to this project id io.banana.transformersbattle
4. Run the emulator

### Battles assumptions

1. If all battles are ties, it will one the last one who stands even if there is no rival to battle

### Points to improve

1. User Interface
2. User Experience


### Known issues

1. Lack of support por Android 4.4 and below 
  Due to several changes on the network security in the Transport Layer, this app doesn't complete the handshake with the server. 
  An implementation was provided using a solution from the Android Developers site, but it hasn't been
  completely tested on a physical device, the problem is still present in the Emulator.
  Solution implemented: https://developer.android.com/training/articles/security-gms-provider
  TLS test ran with: https://www.cdn77.com/tls-test
2. Sometimes Glide fails to fetch the team icon
3. Extract string resources
