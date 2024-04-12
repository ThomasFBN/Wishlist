package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class WishRepository {
    private Wishlist wishlist;


    public void createWishlist(Wishlist wishlist) throws SQLException {
        Connection connection = ConnectionManager.getConnection("jdbc:mysql://wishlistdb.mysql.database.azure.com/wishlist_schema", "wishlist", "Database1");

        {
            String SQL = "INSERT INTO WISHLIST(WISHLISTNAME)" + "values(?)";

            try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, wishlist.getWishListName());

                int rowsAffected = ps.executeUpdate();
                ResultSet resultSet = ps.getGeneratedKeys();
                if (resultSet.next()) {
                    long generatedId = resultSet.getLong(1);
                    wishlist.setListId((int) generatedId);
                }

            }


        }
    }


    public Wish createWish(Wish wish, int listId) throws SQLException {
        Connection connection = ConnectionManager.getConnection("jdbc:mysql://wishlistdb.mysql.database.azure.com/wishlist_schema", "wishlist", "Database1");
        String SQL = "INSERT INTO WISH(NAME,ITEMURL,PRICE,LISTID) VALUES (?,?,?,?)";

        try (PreparedStatement ps = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, wish.getName());
            ps.setString(2, wish.getItemURL());
            ps.setDouble(3, wish.getPrice());
            ps.setInt(4, listId);

            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);

            return wish;


        }
    }
}



