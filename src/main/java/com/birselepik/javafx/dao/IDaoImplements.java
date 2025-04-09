package com.birselepik.javafx.dao;

import com.birselepik.javafx.database.SingletonPropertiesDBConnection;

import java.sql.Connection;

public interface IDaoImplements<T> extends ICrud<T>,IGenericsMethod<T> {

    // Gövdeli Method
    default Connection iDaoImplementsDatabaseConnection() {
        // Singleton DB
        //return SingletonDBConnection.getInstance().getConnection();

        // Singleton Config
        return SingletonPropertiesDBConnection.getInstance().getConnection();
    }
}
