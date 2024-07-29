### This practical coursework is about developing a diabetes monitoring app. More specifically, students are required to:
#### Design and develop a RESTful server-side application in J2EE that can perform CRUD1 operations:
on a simple back-end MySQL database that holds the following data:
* Daily blood glucose level (measured in mg/dL).
* Daily carb intake (measured in grams).
* Daily medication dose.
In particular, read operations should include:
* Displaying all the above data over a (user-specified) time period.
* Displaying the average daily blood glucose level over a (user- specified) period.
* Displaying the average carb intake over a (user-specified) period.
#### Design and develop a RESTful client application that interacts with the server-side application, issue requests for the performance of CRUD operations on the database (including the aforementioned readoperations), and present dynamic content inserted in an HTML UI.

#### Deploy their application on the Heroku cloud platform or Azure

#### Enable the server-side application to dynamically generate line charts depicting the daily blood glucoselevel and carbon intake over a (user-specified) period; also, enable the client application to display thecharts.
#### Enable role-based access control features. More specifically, ensure that all aforementioned CRUDoperations can only be performed by users with the role “ADMIN”, and that ‘read’ operations may alsobe performed by users with the role “PHYSICIAN”. This should be done through:
* The use of appropriate Jersey Security annotations at server-side classes and methods
*  The registration of a client-side filter that ensures that all client requests encompass an authentication
header with a username and password.
*  The registration of a server-side filter that ensures that a client request can only be served by a
method if it encompasses an authentication header with a username and password that match a role
that is permitted to access that method. You may assume that the usernames and passwords that
correspond to the “ADMIN” and “PHYSICIAN” roles are known a priori. 
