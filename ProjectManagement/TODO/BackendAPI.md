**Backend API List**

fasthash = Some hashing function that is very quick and simple.
userhash = The result of fasthash(UserID + Email + RandomValue), RandomValue is stored in the User's Table. We may just end up using user ID for this.

*Users*

 - Register User (i.e. Add new user)
     - Parms: All user information fields.
     - Returns unique code for user to save in application for future requests.
         - userhash
         - This note a very secure way of doing things but will work as a very basic authentication mechanism for Flock alpha version
 - Login User
     - Params: Username, Password
         - This is not secure, but will work for Flock alpha.
     - Returns userhash
         - Stored on device.
 - Edit User (may not be used in Flock alpha)
     - Params: All user information fields
     - Returns: userhash
         - Email may have changed that's why we return hash.
 - Search (for users when adding friends)
     - Params: Search text
     - Returns: json object with array of user information matching request.
          
*Connections/Friends*

 - Add Friend
     - Params: userhash (current user), UserID of friend to add.
     - Returns: True on successful add.
 - Remove Friend
     - Params: userhash, UserID of friend to remove.
     - Returns: True on successful removal.
 - Get friend information (only for approved friends)
     - Params: userhash, friend's user id
     - Returns: User profile information in json object.

*Peck*
 - Send peck to friend
     - Params: userhash, friend UserID
     - Returns: True if the Peck was successfully sent.
     
*Location*

 - Get Location of Friend
     - Params: userhash, friend's UserID
     - Returns: json object of latitude and longitude, or False if there was an error (e.g. they aren't a friend, or they are invisible)
 - Set Visibility (set your own visibility to other users)
     - Params: userhash, visibility status
     - Returns: True on successful change of status
     