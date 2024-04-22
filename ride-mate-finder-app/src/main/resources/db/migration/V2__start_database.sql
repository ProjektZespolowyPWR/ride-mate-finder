CREATE TABLE IF NOT EXISTS Pictures (
    id UUID PRIMARY KEY,
    picture_path VARCHAR(511) NOT NULL
);

CREATE TABLE IF NOT EXISTS "user" (
    id UUID PRIMARY KEY,
    driver_id UUID UNIQUE,
    pic_id UUID REFERENCES Pictures(id),
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    gender BIT,
    age INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS PaymentData (
    user_id UUID PRIMARY KEY REFERENCES "user"(driver_id),
    bank_account VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS OpinionUser (
    id UUID PRIMARY KEY,
    user_id_setter UUID REFERENCES "user"(id),
    user_id_receiver UUID REFERENCES "user"(id),
    opinion TEXT,
    stars INTEGER
);

CREATE TABLE IF NOT EXISTS Car (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES "user"(driver_id),
    car_picture_id UUID REFERENCES Pictures(id),
    vin VARCHAR(255),
    model VARCHAR(255),
    year INTEGER,
    num_of_seats INTEGER
);

CREATE TABLE IF NOT EXISTS OpinionCar (
    id UUID PRIMARY KEY,
    user_id_setter UUID REFERENCES "user"(id),
    vehicle_id UUID REFERENCES Car(id),
    opinion TEXT,
    stars INTEGER
);

CREATE TABLE IF NOT EXISTS Route (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES "user"(driver_id),
    car_id UUID REFERENCES Car(id),
    start_point VARCHAR(255) ,
    end_point VARCHAR(255),
    date_of_ride DATE,
    url_to_maps VARCHAR(511),
    available_seats INTEGER
);

CREATE TABLE IF NOT EXISTS Passengers (
    id UUID PRIMARY KEY,
    route_id UUID REFERENCES Route(id),
    passenger_id UUID REFERENCES "user"(id),
    passenger_spot VARCHAR(255),
    accepted BIT
);

CREATE TABLE IF NOT EXISTS Badges (
    id UUID PRIMARY KEY,
    badge_picture_id UUID REFERENCES Pictures(id),
    badge_description TEXT
);

CREATE TABLE IF NOT EXISTS UserBadges (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES "user"(id),
    badge_id UUID REFERENCES Badges(id)
);