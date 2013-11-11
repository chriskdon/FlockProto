**Backend API List**

fasthash = Some hashing function that is very quick and simple.
userhash = The result of fasthash(UserID + Email + RandomValue), RandomValue is stored in the User's Table. We may just end up using user ID for this.

*User*

 - Register User (i.e. Add new user)
     - Parms: All user information fields.
     - Returns: userhash to save in application for future requests.
         - This is not a very secure way of doing things but will work as a very basic authentication mechanism for Flock alpha version
 - Login User
     - Params: Username, Password
         - This is not secure, but will work for Flock alpha.
     - Returns: userhash
         - Stored on device.
 - Edit User (may not be used in Flock alpha)
     - Params: All user information fields
     - Returns: userhash
         - return userhash as email may have changed.
 - Delete User
     - Params: userhash, password
     - Returns: True on successful deletion
          
*Connections/Friends*

 - Search (for users when adding friends)
     - Params: Search text
     - Returns: json object with array of user information matching request.
 - Issue Friend Request
     - Params: userhash (current user), UserID of requested friend.
     - Returns: True on successful request.
 - Accept/Deline Friend Request
     - Params: userhash (current user), UserID of friend, boolean to accept/decline (T/F).
     - Returns: True on successful accept/decline.
 - Remove Friend
     - Params: userhash, UserID of friend to remove.
     - Returns: True on successful removal.
 - Get friend information (only for approved friends)
     - Params: userhash, friend's user id
     - Returns: Friend's profile information in json object.

*Peck*

 - Send peck to friend
     - Params: userhash, friend UserID
     - Returns: True if the peck was successfully sent.
     - 
*Notifications*

 - Get Notifications
     - Params: userhash
     - Returns: peck and friend request data in json object
 - Delete Notification (may not be used in Flock alpha)
     - Params: userhash, notification id
     - Returns: True on successful deletion
 - Delete All Notifications
     - Params: userhash
     - Returns: True on successful deletion
     
*Location*

 - Get Location of Friend
     - Params: userhash, friend's UserID
     - Returns: json object of latitude and longitude, or False if there was an error (e.g. they aren't a friend, or they are invisible)
 - Set Visibility (set your own visibility to other users)
     - Params: userhash, visibility status
     - Returns: True on successful change of status
     
