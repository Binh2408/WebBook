/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import context.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;

/**
 *
 * @author toden
 */
public class CartDBContext extends DBContext {

    public Map<Integer, Cart> getAllCart(int accID) {
        Map<Integer, Cart> list = new HashMap<Integer, Cart>();
        String status = "";
        try {
            String sql = "SELECT * FROM Cart where AccountID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, accID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.put(rs.getInt(2), new Cart(new ProductDBContext().getProductById(rs.getInt(2)), rs.getInt(3)));
            }
        } catch (Exception ex) {
            status = ex.getMessage();
        }
        return list;
    }

    public boolean addCart(Map<Integer, Cart> carts, int id) {
        String sql = "insert into Cart values(?,?,?)";
        String delsql = "Delete Cart where AccountID = ?";
        String status = "";
        try {
            PreparedStatement stm = connection.prepareStatement(delsql);
            stm.setInt(1, id);
            stm.executeUpdate();
            stm = connection.prepareStatement(sql);
            for (Map.Entry<Integer, Cart> item : carts.entrySet()) {
                if (item.getKey() != null && item.getKey() != 0 && item.getValue() != null) {
                    stm.setInt(1, id);
                    stm.setInt(2, item.getValue().getProduct().getId());
                    stm.setInt(3, item.getValue().getQuantity());
                    stm.executeUpdate();
                }

            }
            return true;
        } catch (Exception ex) {
            status = ex.getMessage();
        }
        return false;
    }

}
