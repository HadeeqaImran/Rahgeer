
create table if not exists User (
    name varchar(255) not null,
    email varchar(255) not null unique,
    password varchar(255) not null,
    username varchar(255) not null primary key
);

create table if not exists BlockedUser (
    username varchar(255) not null primary key,
    foreign key (username) references User(username)
);

create table if not exists Request(
    createdBy varchar(255) not null,
    createdOn datetime not null,
    acceptedBy varchar(255),
    acceptedOn datetime,
    description varchar(500) not null,
    primary key (createdBy, createdOn),
    foreign key (createdBy) references User(username),
    foreign key (acceptedBy) references User(username)
);


create table if not exists Review(
    reviewTo varchar(255) not null,
    reviewedOn datetime not null,
    revieweBy varchar(255) not null,
    description varchar(500) not null,
    primary key (reviewTo, reviewedOn, revieweBy),
    foreign key (reviewTo) references User(username),
    foreign key (revieweBy) references User(username)
);

create table if not exists Tourist(
    username varchar(255) not null primary key,
    passportNum varchar(255) unique not null,
    rating float not null,
    foreign key (username) references User(username)
);

create table if not exists Host(
    username varchar(255) not null primary key,
    rating float not null,
    CNIC varchar(255) unique not null,
    foreign key (username) references User(username)
);

create table if not exists Rating(
    ratedBy varchar(255) not null,
    ratedOn datetime not null,
    ratedTo varchar(255) not null,
    rating float not null,
    primary key (ratedBy, ratedOn, ratedTo),
    foreign key (ratedBy) references User(username),
    foreign key (ratedTo) references User(username)
);

create table if not exists Message(
    sentBy varchar(255) not null,
    sentOn datetime not null,
    sentTo varchar(255) not null,
    messageBody varchar(500) not null,
    primary key (sentBy, sentOn, sentTo),
    foreign key (sentTo) references User(username),
    foreign key (sentBy) references User(username)
);

create table if not exists Place(
    placeID int not null primary key auto_increment,
    placeName varchar(255) not null,
    placeDescription varchar(500) not null,
    placeAddress varchar(255) not null,
    placeRating float not null,
    placeOwner varchar(255) not null,
    foreign key (placeOwner) references Host(username)
);

create table if not exists PlaceRating(
    ratedBy varchar(255) not null,
    ratedOn datetime not null,
    rating float not null,
    placeID int not null,
    primary key (ratedBy, ratedOn, placeID),
    foreign key (ratedBy) references Tourist(username),
    foreign key (placeID) references Place(placeID)
);

create table if not exists Hostel(
    costPerNight float not null,
    hostelID int not null,
    totalRooms int not null,
    availableRooms int not null,
    BedsPerRoom int not null,
    primary key (hostelID),
    foreign key (hostelID) references Place(placeID)
);

create table if not exists Hotel(
    costPerNight float not null,
    hotelID int not null,
    totalRooms int not null,
    availableRooms int not null,
    primary key (hotelID),
    foreign key (hotelID) references Place(placeID)
);

create table if not exists Menu(
    menuID int not null primary key auto_increment,
    menuItem varchar(255) not null,
    menuPrice float not null
);

create table if not exists Restaurant(
    restaurantID int not null,
    tableCount int not null,
    primary key (restaurantID),
    foreign key (restaurantID) references Place(placeID)
);

create table if not exists RestaurantMenu(
    restaurantID int not null,
    menuID int not null,
    primary key (restaurantID, menuID),
    foreign key (restaurantID) references Restaurant(restaurantID),
    foreign key (menuID) references Menu(menuID)
);

create table if not exists Service(
    serviceID int not null primary key auto_increment,
    serviceName varchar(255) not null
);

create table if not exists PlaceService(
    placeID int not null,
    serviceID int not null,
    primary key (placeID, serviceID),
    foreign key (placeID) references Place(placeID),
    foreign key (serviceID) references Service(serviceID)
);

create table if not exists MealPrice(
    placeID int not null,
    breakfastPrice float not null,
    lunchPrice float not null,
    dinnerPrice float not null,
    primary key (placeID),
    foreign key (placeID) references Place(placeID)
);

-- Add column city to Place table
alter table Place add column city varchar(255);
alter table Request add column city varchar(255);
alter table Request add column isRated boolean default false;

insert into Service(serviceName) values ('Meal');
insert into Service(serviceName) values ('Wifi');
insert into Service(serviceName) values ('Laundry');
insert into Service(serviceName) values ('Parking');

create table if not exists Trip(
    tripID int not null primary key auto_increment,
    tripBy varchar(255) not null,
    source varchar(255) not null,
    destination varchar(255) not null,
    startDate date not null,
    endDate date not null,
    isRated boolean not null,
    foreign key (tripBy) references Tourist(username)
);

create table if not exists TripPlace(
    tripID int not null,
    placeID int not null,
    primary key (tripID, placeID),
    foreign key (tripID) references Trip(tripID),
    foreign key (placeID) references Place(placeID)
);

create table if not exists TripCost(
    tripID int not null,
    totalCost float not null,
    mealCost float not null,
    placeID int not null,
    bookingCost float not null,
    primary key (tripID, placeID),
    foreign key (tripID) references Trip(tripID),
    foreign key (placeID) references Place(placeID)
);

create table if not exists TripBooking(
    tripID int not null,
    placeID int not null,
    fromDate date not null,
    toDate date not null,
    totalCost float not null,
    primary key (tripID, placeID, fromDate, toDate),
    foreign key (tripID) references Trip(tripID),
    foreign key (placeID) references Place(placeID)
);

create table if not exists Admin(
    username varchar(255) not null primary key,
    foreign key (username) references User(username)
);
