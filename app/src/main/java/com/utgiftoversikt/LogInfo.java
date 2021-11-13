package com.utgiftoversikt;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LogInfo {
    @PrimaryKey (autoGenerate = true)
    public int id;

    public Person person;
    public float transactionAmount;
    public TransactionType transactionType;
    public String description;

    public LogInfo(Person person, float transactionAmount, TransactionType transactionType, String description) {

        this.person = person;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.description = description;
    }
}
