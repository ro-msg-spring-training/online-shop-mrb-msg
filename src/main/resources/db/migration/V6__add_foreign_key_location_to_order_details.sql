ALTER TABLE Order_Details
ADD CONSTRAINT fk_location_id
FOREIGN KEY(location_id) REFERENCES Locations(id);