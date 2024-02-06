# Api Docs
## By Group 2


### 1.1 Sign In and Authenticate
    [POST] /api/auth/signIn
    To log into a valid account that exists in the
    database and store the authentication token in a http cookie.
    (can be accessed by members of program committee)
    Headers: None
    Body: { email:string,
            password:string}
    Response: {
        id: string,
        username: string,
        email: string,
        tokenType: string,
        accessToken: string
    }
    

![alt_text](./SignIn.png "image_tooltip")

<br>

### 1.2 Create an Account
    [POST] /api/auth/create
    To create an account and is inserted into the 
    database (only allowed to the database administrators)
    Headers: None
    Body: { username:string,
            email:string,
            password:string}
    Response: {
        "message": "Account created"
    }
![alt_text](./Signup.png "image_tooltip")


<br>

### 1.3 Logout from an Account
    [POST] /api/auth/logout
    To logout from an account and delete the cookie storing the 
    authentication token(only allowed to the database administrators)
    Headers: None
    Body: None
    Response: {
        "message": "Logout Successful"
    }
![alt_text](./logout.png "image_tooltip")

<br>

### 2.1 Sent Link to mail
    [POST] /api/forgotPassword/sentlink
    To sent a mail containing the link to the reset password page 
    to the email given
    Headers: None
    Body: {
        email:string
    }
    Response: {
        "message": "Reset Link Sent to email"
    }
![alt_text](./sentlink.png "image_tooltip")

<br>

### 2.2 Password Reset
    [POST] /api/forgotPassword/updatePassword?token=<token>
    To change the password of the user having the resetToken derived from the request url
    Headers: None
    Body: {
        password:string
    }
    Response: {
        "message": "PasswordSuccessfully changed"
    }
![alt_text](./resetPassword.png "image_tooltip")

### 3.1 Get user profile
    [GET] /api/profile
        To display the profile details of the currently logged in user.
    Headers: None
    Body: None
    Response: {
        id: string,
        username: string,	
        email: string,
        password: idk,
        contact: string,
        profession: string,
        DOJ: date
        profilePic: byteArray,
        resetPassswordToken: idk
    }

![alt_text](./userprofile.jpeg "image_tooltip")

### 3.2 Get profile pic
    [GET] /api/profile/pic
    To display the profile picture of the currently logged in user.
    Headers: None
    Body: None
    Response: profile picture in .png format

![alt_text](./getprofilepic.jpeg "image_tooltip")

### 3.3 Update profile
    [PUT] /api/profile
    To update the details of the currently logged in user.
    Headers: None
    Body: {
	    username: string,
	    email: string,
	    contact: string,
	    profession: string
    }
    Response:  String (“Updated successfully” if success and error message if failed)
    
![alt_text](./putprofile.jpeg "image_tooltip")

### 3.4 Update profile pic
    [PUT] /api/profile/pic
    To update the profile picture of the currently logged in user.
    Headers: None
    Body: Multipart file containing the image in .png format
    Response: Updated user details
    
![alt_text](./putprofilepic.jpeg "image_tooltip")
    
### 4.1 Get All Papers
    [GET] /api/paper/all
    Retrieves all papers.
    Headers: None
    Body: None
    Response: 
        200 OK - List of Papers:
        [
            {
                "id": "string",
                "status": "string",
                "paperName": "string",
                "rating": number or null,
                "authorName": "string",
                "tags": ["string"],
                "reviews": [
                    {
                        "reviewer": "string",
                        "comments": "string",
                        "rating": number,
                        "reviewerDecision": "string"
                    },
                    // ... additional review objects
                ],
                "decision": "string"
            },
            // ... additional paper objects
        ]

        404 Not Found - If there are no papers or an error occurs.

![alt_text](./getall.png "image_tooltip")

### 4.2 Get Paper By Id

    [GET] /api/paper/{id}
        Retrieves a paper by ID.
        Headers: None
        Body:None
        Path Parameters:
            - id: (String) The ID of the paper.
        Response:
            200 OK - Paper:
            {
                "id": "string",
                "status": "string",
                "paperName": "string",
                "rating": number or null,
                "authorName": "string",
                "tags": ["string"],
                "reviews": [
                    {
                        "reviewer": "string",
                        "comments": "string",
                        "rating": number,
                        "reviewerDecision": "string"
                    },
                    // ... additional review objects
                ],
                "decision": "string"
            }

        404 Not Found - If the paper with the specified ID is not found.
        
![alt_text](./getbyid.png "image_tooltip")

### 4.3 Update Decision of Paper
    [PUT] /api/paper/updateDecision/{id}?newDecision=decision
        Updates the decision of a paper by ID to 'decision'.
        Headers: None
        Body: None
        Path Parameters:
            - id: (String) The ID of the paper.
        Query Parameters:
            - newDecision: (String) The new decision status (e.g., 'Accept','Reject').
        Response:
            200 OK - String:
            "Paper decision updated successfully"
    
            404 Not Found - If the paper with the specified ID is not found.
            
![alt_text](./updatedecision.png "image_tooltip")


