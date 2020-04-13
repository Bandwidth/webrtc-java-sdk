# Bandwidth WebRTC Java SDK Documentation 

This SDK is for use in your Java server side application that will manage WebRTC conferences. 

## Get the SDK

Right now, you can download the SDK by cloning this repo:

    $ git clone git@github.com:Bandwidth/webrtc-java-sdk.git
    
## Build the SDK

You will need at least Java 11 and Maven 3 installed to compile the SDK:

    $ cd webrtc-jva-sdk
    $ mvn clean install
    
This will put the SDK in your local M2 cache, and it should be accessible by other Maven projects on your machine now.

## Add the SDK to your project

In your project's `pom.xml` file add the following dependency:
```xml
<dependency>
    <groupId>com.bandwidth</groupId>
    <artifactId>webrtc-java-sdk</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Use the SDK

You can instantiate the SDK and connect like this:

```java
WebRtc webRtc = new WebRtc();
webRtc.connect(
    WebRtcCredentials.builder()
        .accountId("YOUR_ACCOUNT_ID_HERE")
        .password("YOUR_PASSWORD_HERE")
        .username("YOUR_USERNAME_HERE")
        .build();
```

The next step is to set up some event handlers so you'll get notified when important things are happening.

```java
webRtc.setOnParticipantJoined((event) -> {
    String conferenceId = event.getConferenceId();
    String participantId = event.getParticipantId();
    System.out.println(participantId + " joined conference " + conferenceId);
    // TODO: do whatever you need to do when someone new joins
    }
});

webRtc.setOnParticipantPublished((event) -> {
    String conferenceId = event.getConferenceId();
    String participantId = event.getParticipantId();
    String streamId = event.getStreamId();
    System.out.println("participant " + paprticipantId + " published in conference " + conferenceId + " with stream id " + streamId);
    // TODO: do whatever you need to do when someone publishes a new stream
    }
});

webRtc.setOnParticipantUnPublished((event) -> {
    String conferenceId = event.getConferenceId();
    String participantId = event.getParticipantId();
    String streamId = event.getStreamId();
    System.out.println("participant " + paprticipantId + " unpublished in conference " + conferenceId + " stream id " + streamId);
    // TODO: do whatever you need to do when someone stops publishing a stream
    }
});

webRtc.setOnParticipantLeft((event) -> {
    String conferenceId = event.getConferenceId();
    String participantId = event.getParticipantId();
    System.out.println("participant " + paprticipantId + " left  conference " + conferenceId);
    // TODO: do whatever you need to do when someone leaves
    }
});
```

Now that you have your event handlers set up, you can start creating conferences and participants like this:
```java
StartConferenceResponse conferenceResponse = webRtc.startConference();
System.out.println("created conference " + conferenceResponse.getConferenceId());

CreateParticipantResponse participantResponse = webRtc.createParticipant(conferenceResponse.getConferenceId());
System.out.println("created participant " + participantResponse.getParticipantId() + " in conference " + conferenceResponse.getConferenceId());
```

> Note: Right now there is a known issue that prevents the Java SDK from propagating Exceptions up to the client code when a request fails. However, if you are using a logger like SLF4J, you should be able to see the error message in your error log. Bandwidth is working on a fix for this, so client code can catch Exceptions and handle them appropriately in the very near future. 