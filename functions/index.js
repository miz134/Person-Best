const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.addTimeStamp = functions.firestore
   .document('Chats/{chatId}/messages/{messageId}')
   .onCreate((snap, context) => {
     if (snap) {
       return snap.ref.update({
                   timestamp: admin.firestore.FieldValue.serverTimestamp()
               });
     }

     return "snap was null or empty";
   });

exports.addTimeStamp2 = functions.firestore
  .document('users/{userId}/WalkRuns/{walkRunId}')
  .onCreate((snap, context) => {
    if (snap) {
      return snap.ref.update({
                  timestamp: admin.firestore.FieldValue.serverTimestamp()
              });
    }

    return "snap was null or empty";
  });

exports.sendChatNotifications = functions.firestore
   .document('Chats/{chatId}/messages/{messageId}')
   .onCreate((snap, context) => {
     // Get an object with the current document value.
     // If the document does not exist, it has been deleted.
     const document = snap.exists ? snap.data() : null;

     if (document) {
       var message = {
         notification: {
           title: document.from + ' sent you a message',
           body: document.text
         },
         topic: context.params.chatId
       };

       return admin.messaging().send(message)
         .then((response) => {
           // Response is a message ID string.
           console.log('Successfully sent message:', response);
           return response;
         })
         .catch((error) => {
           console.log('Error sending message:', error);
           return error;
         });
     }

     return "document was null or emtpy";
   });

/*
exports.sendNotification = functions.firestore
    .document("Notifications/{notificationsId}/notification/{notifications}")
    .onCreate((snap, context) => {    //This will be the notification model that we push to firebase

     const document = snap.exists ? snap.data() : null;

     if (document) {
       var message = {
         notification: {
           title: "Hello World",
           body: "Come on baby"
         },
         topic: context.params.notificationId
       };

       return admin.messaging().send(message)
         .then((response) => {
           // Response is a message ID string.
           console.log('Successfully sent message:', response);
           return response;
         })
         .catch((error) => {
           console.log('Error sending message:', error);
           return error;
         });
     }

     return "document was null or emtpy";
})
*/

/*
   exports.sendGoalNotifications = functions.firestore
      .document('Notifications/{notificationId}/notification/{messageId}')
      .onCreate((snap, context) => {
        // Get an object with the current document value.
        // If the document does not exist, it has been deleted.
        const document = snap.exists ? snap.data() : null;

        if (document) {
          var message = {
            notification: {
              title: document.from + ' sent you a message',
              body: document.text
            },
            topic: context.params.chatId
          };

          return admin.messaging().send(message)
            .then((response) => {
              // Response is a message ID string.
              console.log('Successfully sent message:', response);
              return response;
            })
            .catch((error) => {
              console.log('Error sending message:', error);
              return error;
            });
        }

        return "document was null or emtpy";
      });
      */

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
