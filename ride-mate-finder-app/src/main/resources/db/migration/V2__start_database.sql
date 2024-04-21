CREATE TABLE IF NOT EXISTS Pictures (
    picture_id UUID PRIMARY KEY,
    picture_path VARCHAR(511) NOT NULL
);

CREATE TABLE IF NOT EXISTS "User" (
    user_id UUID PRIMARY KEY,
    driver_id UUID,
    picture_id UUID REFERENCES Pictures(picture_id),
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    gender BIT,
    age INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS PaymentData (
    driver_id UUID PRIMARY KEY REFERENCES "User"(user_id),
    bank_account VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS OpinionUser (
    user_id_setter UUID REFERENCES "User"(user_id),
    user_id_receiver UUID REFERENCES "User"(user_id),
    opinion TEXT,
    stars INTEGER
);

CREATE TABLE IF NOT EXISTS Car (
    car_id UUID PRIMARY KEY,
    driver_id UUID REFERENCES "User"(user_id),
    car_picture_id UUID REFERENCES Pictures(picture_id),
    vin VARCHAR(255),
    model VARCHAR(255),
    year INTEGER,
    num_of_seats INTEGER
);

CREATE TABLE IF NOT EXISTS OpinionCar (
    user_id_setter UUID REFERENCES "User"(user_id),
    car_id UUID REFERENCES Car(car_id),
    opinion TEXT,
    stars INTEGER
);

CREATE TABLE IF NOT EXISTS Route (
    route_id UUID PRIMARY KEY,
    driver_id UUID REFERENCES "User"(user_id),
    car_id UUID REFERENCES Car(car_id),
    start_point VARCHAR(255) ,
    end_point VARCHAR(255),
    date_of_ride DATE,
    url_to_maps VARCHAR(511),
    available_seats INTEGER
);

CREATE TABLE IF NOT EXISTS Passengers (
    route_id UUID REFERENCES Route(route_id),
    user_id UUID REFERENCES "User"(user_id),
    passenger_spot VARCHAR(255),
    accepted BIT,
    PRIMARY KEY (route_id, user_id)
);

CREATE TABLE IF NOT EXISTS Badges (
    badge_id UUID PRIMARY KEY,
    badge_picture_id UUID REFERENCES Pictures(picture_id),
    badge_description TEXT
);

CREATE TABLE IF NOT EXISTS UserBadges (
    user_id UUID REFERENCES "User"(user_id),
    badge_id UUID REFERENCES Badges(badge_id),
    PRIMARY KEY (user_id, badge_id)
);
