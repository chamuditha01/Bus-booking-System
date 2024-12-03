package com.example.busbookingsystem_madcw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "bus_booking.db";
    private static final int DATABASE_VERSION = 1;

    // Table: Users
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_PROFILE_PICTURE = "profile_picture";

    // Table: Buses
    private static final String TABLE_BUSES = "buses";
    private static final String COLUMN_BUS_ID = "id";
    private static final String COLUMN_BUS_NUMBER = "bus_number";
    private static final String COLUMN_ROUTE_ID = "route_id";
    private static final String COLUMN_OWNER_ID = "owner_id";
    private static final String COLUMN_DRIVER_ID = "driver_id";

    // Table: Routes
    private static final String TABLE_ROUTES = "routes";
    private static final String COLUMN_ROUTE_START = "start_point";
    private static final String COLUMN_ROUTE_END = "end_point";
    private static final String COLUMN_ROUTE_MAP_COORDINATES = "map_coordinates";

    // Table: Bookings
    private static final String TABLE_BOOKINGS = "bookings";
    private static final String COLUMN_BOOKING_ID = "id";
    private static final String COLUMN_PASSENGER_ID = "passenger_id";
    private static final String COLUMN_BUS_ID_BOOKING = "bus_id";
    private static final String COLUMN_SEAT_NUMBER = "seat_number";
    private static final String COLUMN_STATUS = "status";

    // Table: Ratings
    private static final String TABLE_RATINGS = "ratings";
    private static final String COLUMN_RATING_ID = "id";
    private static final String COLUMN_RATING_BUS_ID = "bus_id";
    private static final String COLUMN_RATING_PASSENGER_ID = "passenger_id";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_COMMENTS = "comments";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_ROLE + " TEXT, "
                + COLUMN_PROFILE_PICTURE + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);

        // Create Buses table
        String CREATE_BUSES_TABLE = "CREATE TABLE " + TABLE_BUSES + "("
                + COLUMN_BUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BUS_NUMBER + " TEXT, "
                + COLUMN_ROUTE_ID + " INTEGER, "
                + COLUMN_OWNER_ID + " INTEGER, "
                + COLUMN_DRIVER_ID + " INTEGER)";
        db.execSQL(CREATE_BUSES_TABLE);

        // Create Routes table
        String CREATE_ROUTES_TABLE = "CREATE TABLE " + TABLE_ROUTES + " ("
                + COLUMN_ROUTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ROUTE_START + " TEXT, "
                + COLUMN_ROUTE_END + " TEXT, "
                + COLUMN_ROUTE_MAP_COORDINATES + " TEXT)";
        db.execSQL(CREATE_ROUTES_TABLE);

        // Insert sample data into the routes table
        db.execSQL("INSERT INTO " + TABLE_ROUTES + " (" + COLUMN_ROUTE_START + ", " + COLUMN_ROUTE_END + ", " + COLUMN_ROUTE_MAP_COORDINATES + ") " +
                "VALUES ('Colombo', 'Galle', '[{\"lat\":6.9271, \"lng\":79.8612}, {\"lat\":6.0259, \"lng\":80.217}]')");
        db.execSQL("INSERT INTO " + TABLE_ROUTES + " (" + COLUMN_ROUTE_START + ", " + COLUMN_ROUTE_END + ", " + COLUMN_ROUTE_MAP_COORDINATES + ") " +
                "VALUES ('Kandy', 'Matara', '[{\"lat\":7.2906, \"lng\":80.6337}, {\"lat\":5.9485, \"lng\":80.5353}]')");

        // Create Bookings table
        String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + TABLE_BOOKINGS + "("
                + COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PASSENGER_ID + " INTEGER, "
                + COLUMN_BUS_ID_BOOKING + " INTEGER, "
                + COLUMN_SEAT_NUMBER + " INTEGER, "
                + COLUMN_STATUS + " TEXT)";
        db.execSQL(CREATE_BOOKINGS_TABLE);

        // Create Ratings table
        String CREATE_RATINGS_TABLE = "CREATE TABLE " + TABLE_RATINGS + "("
                + COLUMN_RATING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RATING_BUS_ID + " INTEGER, "
                + COLUMN_RATING_PASSENGER_ID + " INTEGER, "
                + COLUMN_RATING + " REAL, "
                + COLUMN_COMMENTS + " TEXT)";
        db.execSQL(CREATE_RATINGS_TABLE);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        // Custom downgrade logic, e.g., deleting or modifying tables
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATINGS);
        onCreate(db);
    }

    // Add User
    public boolean addUser(String name, String email, String password, String role, String profilePicture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ROLE, role);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }
    //getall routes
    public HashMap<String, Integer> getAllRoutes() {
        HashMap<String, Integer> routeMap = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to retrieve route_id, start_point, and end_point
        Cursor cursor = db.rawQuery("SELECT route_id, start_point, end_point FROM " + TABLE_ROUTES, null);

        if (cursor.moveToFirst()) {
            do {
                int routeId = cursor.getInt(cursor.getColumnIndexOrThrow("route_id"));
                String startPoint = cursor.getString(cursor.getColumnIndexOrThrow("start_point"));
                String endPoint = cursor.getString(cursor.getColumnIndexOrThrow("end_point"));

                // Format the display string as "StartPoint - EndPoint"
                String routeDisplay = startPoint + " - " + endPoint;

                // Map the display string to the route ID
                routeMap.put(routeDisplay, routeId);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return routeMap;
    }


    // Method to get all drivers from the database
    public ArrayList<String> getAllDrivers() {
        ArrayList<String> drivers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Assuming driver information is stored in the users table (role = "driver")
        String query = "SELECT " + COLUMN_NAME + " FROM " + TABLE_USERS + " WHERE " + COLUMN_ROLE + " = 'Driver'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                drivers.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return drivers;
    }


    // Method to get user details by userId
    public HashMap<String, String> getUserDetails(int userId) {
        HashMap<String, String> userDetails = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to fetch user details based on userId
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve values for each column
            userDetails.put("id", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)));
            userDetails.put("name", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            userDetails.put("email", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));
            userDetails.put("password", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)));
            userDetails.put("role", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE)));
            userDetails.put("profile_picture", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_PICTURE)));
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return userDetails;
    }

    //get busses by route
    public List<HashMap<String, String>> getBusNumbersAndRoutes(String from, String to) {
        List<HashMap<String, String>> busDetailsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to join Buses and Routes tables and filter by start and end points
        String query = "SELECT b.id,b.bus_number, r.start_point, r.end_point " +
                "FROM Buses b " +
                "JOIN Routes r ON b.route_id = r.route_id " +
                "WHERE r.start_point = ? AND r.end_point = ?";

        Log.d("DB", "Query: " + query);
        Log.d("DB", "Params: " + from.trim() + ", " + to.trim());

        Cursor cursor = db.rawQuery(query, new String[]{from.trim(), to.trim()});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String busNumber = cursor.getString(cursor.getColumnIndexOrThrow("bus_number"));
                String busId = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String startPoint = cursor.getString(cursor.getColumnIndexOrThrow("start_point"));
                String endPoint = cursor.getString(cursor.getColumnIndexOrThrow("end_point"));

                Log.d("DB", "Fetched data: BusNumber = " + busNumber + ", BusId = " + busId + ", Route = " + startPoint + " to " + endPoint);

                HashMap<String, String> busDetail = new HashMap<>();
                busDetail.put("bus_number", busNumber);
                busDetail.put("bid", busId);
                busDetail.put("route", startPoint + " to " + endPoint);

                busDetailsList.add(busDetail); // Add each bus detail to the list
            } while (cursor.moveToNext());
        } else {
            Log.d("TAG", "No buses found for the specified route.");
        }


        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return busDetailsList;
    }


    public void logAllRoutesAndBuses() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Log Routes
        Cursor routeCursor = db.rawQuery("SELECT * FROM Routes", null);
        if (routeCursor != null && routeCursor.moveToFirst()) {
            do {
                Log.d("DB", "Route ID: " + routeCursor.getInt(routeCursor.getColumnIndexOrThrow("route_id")) +
                        ", Start: " + routeCursor.getString(routeCursor.getColumnIndexOrThrow("start_point")) +
                        ", End: " + routeCursor.getString(routeCursor.getColumnIndexOrThrow("end_point")));
            } while (routeCursor.moveToNext());
            routeCursor.close();
        }

        // Log Buses
        Cursor busCursor = db.rawQuery("SELECT * FROM Buses", null);
        if (busCursor != null && busCursor.moveToFirst()) {
            do {
                Log.d("DB", "Bus ID: " + busCursor.getInt(busCursor.getColumnIndexOrThrow("id")) +
                        ", Bus Number: " + busCursor.getString(busCursor.getColumnIndexOrThrow("bus_number")) +
                        ", Route ID: " + busCursor.getInt(busCursor.getColumnIndexOrThrow("route_id")));
            } while (busCursor.moveToNext());
            busCursor.close();
        }

        db.close();
    }



    // Add Bus
    public boolean insertBus(String busNumber, int route, int ownerId, String driver) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get route ID and driver ID based on their names

        int driverId = getDriverIdByName(driver);

        ContentValues values = new ContentValues();
        values.put("bus_number", busNumber); // Replace with your column name
        values.put("route_id", route); // Replace with your column name
        values.put("owner_id", ownerId); // Replace with your column name
        values.put("driver_id", driverId); // Replace with your column name

        long result = db.insert("buses", null, values); // Replace with your table name

        return result != -1; // Return true if insertion is successful
    }

    private int getRouteIdByName(String routeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT id FROM routes WHERE route_name = ?"; // Replace column/table names accordingly
        try (Cursor cursor = db.rawQuery(query, new String[]{routeName})) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        }
        return -1; // Default value if not found
    }

    private int getDriverIdByName(String driverName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT id FROM users WHERE name = ?"; // Replace column/table names accordingly
        try (Cursor cursor = db.rawQuery(query, new String[]{driverName})) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        }
        return -1; // Default value if not found
    }


    public ArrayList<String> getAllBuses(int userId) {
        ArrayList<String> buses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get bus numbers for the given user
        Cursor cursor = db.rawQuery("SELECT bus_number FROM " + TABLE_BUSES + " WHERE owner_id = ?", new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            // Get the index of the column dynamically using getColumnIndexOrThrow, which ensures the column exists
            int busNumberIndex = cursor.getColumnIndex("bus_number");

            // Check if the column exists and if it's a valid index
            if (busNumberIndex != -1) {
                do {
                    buses.add(cursor.getString(busNumberIndex)); // Add bus number to list
                } while (cursor.moveToNext());
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();
        return buses;
    }


    // Validate User Login
    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null,
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }

    // Add more methods for other tables as needed (e.g., addRoute, addBooking, addRating, get methods)
}
