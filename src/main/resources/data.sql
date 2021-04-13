insert into country (name)
values ('France');

insert into country (name)
values ('USA');

insert into country (name)
values ('China');


insert into city (name, country_country_id)
values ('Bergues', 1);

insert into city (name, country_country_id)
values ('Dunkerque', 1);

insert into city (name, country_country_id)
values ('Lille', 1);

insert into city (name, country_country_id)
values ('New-York', 2);


insert into location (latitude, longitude)
values (50.63253537832229, 3.0710044149376303);
insert into location (latitude, longitude)
values (50.634355026604226, 3.0677535784641785);


insert into transformation_type(title)
values ('Neural transfer');
insert into transformation_type(title)
values ('Glitch');

insert into transformation (title, transformation_type_transformation_type_id)
values ('musk', 1);
insert into transformation (title, transformation_type_transformation_type_id)
values ('eye-destroyer', 2);


insert into format (name)
values ('jpg');

insert into format (name)
values ('jpeg');

insert into format (name)
values ('png');


insert into user_account (email, enable_newsletter, is_admin, is_archived, password, user_name, city_city_id)
values ('cochin.valentin@hotmail.fr', false, false, false, 'valou', 'valou', 1);

insert into user_account (email, enable_newsletter, is_admin, is_archived, password, user_name, city_city_id)
values ('francois.antoine3@gmail.com', true, true, false, 'antoine', 'antoine', 3);

insert into user_account (email, enable_newsletter, is_admin, is_archived, password, user_name, city_city_id)
values ('cochin.valentin42@gmail.com', false, false, true, 'pedro', 'pedro', 4);

insert into photo (is_profile_picture, photo_bytes, photo_url, title, format_format_id, location_location_id,
                   user_account_user_account_id)
values (true, NULL, 'foo.com', 'Foo', 1, 1, 1);

insert into photo (is_profile_picture, photo_bytes, photo_url, title, format_format_id, location_location_id,
                   user_account_user_account_id)
values (false, NULL, 'foo.com', 'filtered_photo_1', 1, null, 1);

insert into photo_transformations (photo_id, transformation_id) values (2, 1);