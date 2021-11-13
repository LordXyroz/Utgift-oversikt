package com.utgiftoversikt;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public String fromPerson(Person person) {
        return person.name();
    }

    @TypeConverter
    public Person toPerson(String value) {
        return Person.valueOf(value);
    }

    @TypeConverter
    public String fromTransaction(TransactionType type) {
        return type.name();
    }

    @TypeConverter
    public TransactionType toTransaction(String value) {
        return TransactionType.valueOf(value);
    }
}
