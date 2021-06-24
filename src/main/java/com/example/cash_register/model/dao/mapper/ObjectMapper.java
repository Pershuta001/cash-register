package com.example.cash_register.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ObjectMapper<E>{

    E  extractFromResultSet(ResultSet rs) throws SQLException;


}
