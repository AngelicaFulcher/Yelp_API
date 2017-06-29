# Documentation

![uml](https://github.com/fosy/Team2_YELP/blob/master/domain-model-yelp.png)

### Home
Path: `/`

It should have the info of the company. Short intro to our service / website. Header with
- about
- contact
- search box

### About
Path: `/about`
- Info about the company

### Contact
Path: `/contact`
- Input form to contact the company (get in touch).

### Sign in
Path: `/users/sign_in`
- It should render a form to log in.

### Sign up
Path: `/users/sign_up`
- It should render a form to register.

### Users edit
Path: `/users/{id}/edit`
- Edit the particular user useing the form for update. A button to delete the account.

### Search restaurant
Path: `/restaurants/search?query=Lilli Jo`
- A search box for searching resaturant based on the query(name)

### Resturant Page Info
Path: `/restaurants/{id}`
- It should render the all infos about the restaurant. A button to write the review

### New Review
Path: `/restaurants/{id}/reviews/new `
- It should render an input field and the rating stars, a submit button.


# API

Public Paths:

- POST: `/api/users/sign_in`. Body should have an `email` and a `password`.

- POST: `/api/users/sign_up`. Body should have an `firstName`, `lastName`, `email` and a `password`.

- GET: `/api/restaurants/search?=params`. Body should have the name of the restaurant

- GET: `/api/restaurants/`

- GET: `/api/restaurants/:id`

Paths where Auth is required:

- PUT: `/api/users/:id`.

- DELETE: `/api/users/:id`.

- POST: `/api/comment`. Body should have `text` and `rating` property.
