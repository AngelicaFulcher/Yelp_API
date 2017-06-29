delete from review;
delete from user;
delete from restaurant;

insert into user(id, email, first_name, last_name, password) values(1, 'xyz@email.com', 'Laurent','Hoxhaj', '1234');
insert into user(id, email, first_name, last_name, password) values(2, 'fake3@email.com', 'Bob','Doe', '8900');

insert into restaurant(id, address, city, logo_uri, name,  phone_number, uri) values(1, 'Ludretikonerstrasse 21', 'Zurich', 'logo', 'RuenThai', '043 443 59 89', 'http');
insert into restaurant(id, address, city, logo_uri, name,  phone_number, uri) values(2, 'Maagpl. 5', 'Zurich', 'logo1', 'Clouds', '044 404 30 00', 'http1');

insert into review(id, date_created, rating, text, restaurant_id, user_id) values(1, {ts '2017-01-01 00:00:00.00'}, 5, 'excellent', 1, 1);
insert into review(id, date_created, rating, text, restaurant_id, user_id) values(2, {ts '2017-01-01 00:00:00.00'}, 3, 'good', 1, 2);
insert into review(id, date_created, rating, text, restaurant_id, user_id) values(3, {ts '2017-01-01 00:00:00.00'}, 4, 'very good', 2, 1);
