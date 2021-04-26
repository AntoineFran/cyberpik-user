insert into location (latitude, longitude)
values (50.63253537832229, 3.0710044149376303);

insert into location (latitude, longitude)
values (50.634355026604226, 3.0677535784641785);

insert into location (latitude, longitude)
values (48.857988, 2.294529);

insert into location (latitude, longitude)
values (40.7486538,-73.9853043);



insert into transformation_type(title)
values ('Neural transfer');

insert into transformation_type(title)
values ('Glitch');



insert into transformation (title, transformation_type_id)
values ('musk', 1);

insert into transformation (title, transformation_type_id)
values ('eye-destroyer', 2);



insert into format (name)
values ('jpg');

insert into format (name)
values ('jpeg');



insert into user_account (email, enable_newsletter, is_admin, is_archived, password, user_name, location)
values ('cochin.valentin@hotmail.fr', false, false, false, 'valou', 'valou', 'Dunkerque, France');

insert into user_account (email, enable_newsletter, is_admin, is_archived, password, user_name, location)
values ('francois.antoine3@gmail.com', true, true, false, 'antoine', 'antoine', 'Lille, France');

insert into user_account (email, enable_newsletter, is_admin, is_archived, password, user_name, location)
values ('cochin.valentin42@gmail.com', false, false, true, 'pedro', 'pedro', 'Mexico City - Mexico');



insert into photo (photo_bytes, photo_url, title, format_id, location_id, user_account_id)
values (NULL, 'https://images.unsplash.com/photo-1606542758304-820b04394ac2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=700&q=80', 'Foo', 1, 1, 3);

insert into photo (photo_bytes, photo_url, title, format_id, location_id, user_account_id)
values (NULL, 'https://images.unsplash.com/photo-1580428180163-76ab1efe2aed?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8cG9ydHJhaXQlMjBjeWJlcnB1bmt8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60', 'filtered_photo_1', 1, 2, 1);

insert into photo (photo_bytes, photo_url, title, format_id, location_id, user_account_id)
values (NULL, 'https://images.unsplash.com/photo-1568668392383-58c369615742?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=661&q=80', 'cyber_eiffel', 2, 3, 2);

insert into photo (photo_bytes, photo_url, title, format_id, location_id, user_account_id)
values (NULL, 'https://images.unsplash.com/photo-1555109307-f7d9da25c244?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1053&q=80', 'cyber_empire', 2, 2, 2);

insert into photo (photo_bytes, photo_url, title, format_id, location_id, user_account_id)
values (NULL, 'https://images.unsplash.com/photo-1531279550271-23c2a77a765c?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=700&q=80', 'cyber_hong_kong', 2, 1, 1);

insert into photo (photo_bytes, photo_url, title, format_id, location_id, user_account_id)
values (NULL, 'https://images.unsplash.com/photo-1558961166-9c584702dcb0?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80', 'cyber_street', 2, 2, 2);



insert into photo_transformations (photo_id, transformation_id) values (1, 2);

insert into photo_transformations (photo_id, transformation_id) values (2, 1);

insert into photo_transformations (photo_id, transformation_id) values (3, 2);

insert into photo_transformations (photo_id, transformation_id) values (4, 1);



update user_account set photo_profile_id = 2 where user_account_id = 1;

update user_account set photo_profile_id = 4 where user_account_id = 2;

update user_account set photo_profile_id = 1 where user_account_id = 3;