package com.example.local.database.dao;

import com.example.local.model.TransactionModel;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


@Dao
public abstract class TransactionDao {

    @Insert
    public abstract void insert(TransactionModel transactionModel);

    @Query("SELECT * FROM transaction_table")
    public abstract List<TransactionModel> getAllTransactionData();

}