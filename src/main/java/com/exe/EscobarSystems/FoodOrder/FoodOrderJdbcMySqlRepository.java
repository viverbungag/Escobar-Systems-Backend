package com.exe.EscobarSystems.FoodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("foodOrder_jdbc_mysql")
public class FoodOrderJdbcMySqlRepository implements FoodOrderDao{

    private final JdbcTemplate jdbcTemplate ;

    @Autowired
    public FoodOrderJdbcMySqlRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Long orderIdResult(final ResultSet rs) throws SQLException {
        return rs.getLong("LAST_INSERT_ID()");
    }

    @Override
    public Long insertFoodOrder(Long menuId, Integer menuQuantity){
        String query = """
                INSERT INTO food_order(menu_id, menu_quantity)
                VALUES (?, ?);
                """;

        jdbcTemplate.update(query, menuId, menuQuantity);

        String queryForGettingId = "SELECT LAST_INSERT_ID();";

        List<Long> id = jdbcTemplate.query(queryForGettingId, (rs, rowNum) -> orderIdResult(rs));

        return id.get(0);
    }
}
