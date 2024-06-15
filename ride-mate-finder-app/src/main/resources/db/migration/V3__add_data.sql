-- Dodanie przykładowych wartości do tabeli Pictures
INSERT INTO Pictures (id, data) VALUES
('11111111-1111-1111-1111-111111111111', 'picture data 1'::bytea),
('22222222-2222-2222-2222-222222222222', 'picture data 2'::bytea);

-- Dodanie przykładowych wartości do tabeli user
INSERT INTO "user" (id, driver_id, pic_id, name, surname, email, gender, age) VALUES
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '11111111-1111-1111-1111-111111111111', 'John', 'Doe', 'john.doe@example.com', 'Male', 30),
('cccccccc-cccc-cccc-cccc-cccccccccccc', 'dddddddd-dddd-dddd-dddd-dddddddddddd', '22222222-2222-2222-2222-222222222222', 'Jane', 'Smith', 'jane.smith@example.com', 'Female', 28);

-- Dodanie przykładowych wartości do tabeli Car
INSERT INTO Car (id, user_id, car_picture_id, vin, model, year, num_of_seats) VALUES
('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '11111111-1111-1111-1111-111111111111', '1HGCM82633A123456', 'Honda Civic', 2010, 5),
('ffffffff-ffff-ffff-ffff-ffffffffffff', 'dddddddd-dddd-dddd-dddd-dddddddddddd', '22222222-2222-2222-2222-222222222222', '2HGFG118X8H123456', 'Toyota Corolla', 2015, 4);

-- Dodanie przykładowych wartości do tabeli Route
INSERT INTO Route (id, user_id, car_id, start_point, end_point, date_of_ride, url_to_maps, available_seats) VALUES
('55555555-5555-5555-5555-555555555555', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Point A', 'Point B', '2024-06-20', 'http://maps.google.com/example1', 3),
('44444444-4444-4444-4444-444444444444', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'ffffffff-ffff-ffff-ffff-ffffffffffff', 'Point C', 'Point D', '2024-06-25', 'http://maps.google.com/example2', 2);
