package com.geekbrains.android.homework.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_1_2 extends Migration {

    public Migration_1_2() {
        super(1, 2);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {

        database.execSQL("ALTER TABLE City " +
                "ADD COLUMN date TEXT");

        database.execSQL("ALTER TABLE City " +
                "ADD COLUMN temperature TEXT");
    }
}
