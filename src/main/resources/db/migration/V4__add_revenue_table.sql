CREATE TABLE Revenues(
        id UUID PRIMARY KEY,
        location_id UUID,
        date DATE,
        sum FLOAT,
        FOREIGN KEY(location_id) REFERENCES Locations(id));;