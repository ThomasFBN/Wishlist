package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WishRepository {
    private Wishlist wishlist;
    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String pwd;


    public void createWishlist(Wishlist wishlist) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, username, pwd);

        {
            String SQL = "INSERT INTO WISHLIST (WISHLISTNAME) VALUES (?)";

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
        Connection connection = ConnectionManager.getConnection(db_url, username, pwd);
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

    public void deleteWish(int wishId) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "DELETE FROM WISH WHERE name = ?";

        try (PreparedStatement ps = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, wishId);

            ps.execute();
        }
    }

    public void editWish(Wish wish, int wishId) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "UPDATE WISH SET NAME=?, ITEMURL=?, PRICE=? WHERE ID=?";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, wish.getName());
            ps.setString(2, wish.getItemURL());
            ps.setDouble(3, wish.getPrice());
            ps.setInt(4, wishId);

            ps.executeUpdate();
        }
    }

    public int getHighestId() throws SQLException {
        int id = 0;
        Connection connection = ConnectionManager.getConnection(db_url, username, pwd);
        String SQL = "SELECT MAX(LISTID) FROM WISHLIST";
        PreparedStatement ps = connection.prepareStatement(SQL);

        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("MAX(LISTID)");
    }

    public List<Wish> findAllByWishlistId(int wishlistId) throws SQLException {
        List<Wish> wishes = new ArrayList<>();
        Connection connection = DriverManager.getConnection(db_url, username, pwd);
        String SQL = "SELECT * FROM WISH WHERE LISTID = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, wishlistId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Wish wish = new Wish();
                    wish.setWishId(rs.getInt("WISHID"));
                    wish.setName(rs.getString("NAME"));
                    wish.setItemURL(rs.getString("ITEMURL"));
                    wish.setPrice(rs.getDouble("PRICE"));
                    wishes.add(wish);
                }
            }
        }
        return wishes;
    }
}

