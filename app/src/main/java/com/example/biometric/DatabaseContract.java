package com.example.biometric;

import android.provider.BaseColumns;

public final class DatabaseContract {
    private DatabaseContract() {}

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_REGISTRATION_DATE = "registration_date";
        public static final String COLUMN_FINGERPRINT_HASHES = "fingerprint_hashes";
        public static final String COLUMN_IRIS_HASH = "iris_hash";
    }
}
