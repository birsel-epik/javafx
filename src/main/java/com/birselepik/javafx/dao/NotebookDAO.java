package com.birselepik.javafx.dao;

import com.birselepik.javafx.database.SingletonPropertiesDBConnection;
import com.birselepik.javafx.dto.NotebookDTO;
import com.birselepik.javafx.dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 📒 Not Defteri Veri Erişim Objesi (DAO)
 * Notların veritabanı ile etkileşimini sağlar.
 */
public class NotebookDAO implements IDaoImplements<NotebookDTO> {

    private Connection connection;

    public NotebookDAO() {
        this.connection = SingletonPropertiesDBConnection.getInstance().getConnection();
    }

    // 📥 Yeni Not Ekleme
    @Override
    public Optional<NotebookDTO> create(NotebookDTO notebookDTO) {
        String sql = "INSERT INTO notebook_table (title, content, category, pinned, user_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, notebookDTO.getTitle());
            ps.setString(2, notebookDTO.getContent());
            ps.setString(3, notebookDTO.getCategory());
            ps.setBoolean(4, notebookDTO.getPinned());
            ps.setInt(5, notebookDTO.getUserDTO().getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        notebookDTO.setId(rs.getInt(1));
                        return Optional.of(notebookDTO);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    // 📄 Tüm Notları Listeleme
    @Override
    public Optional<List<NotebookDTO>> list() {
        List<NotebookDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM notebook_table ORDER BY id DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToObjectDTO(rs));
            }
            return list.isEmpty() ? Optional.empty() : Optional.of(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // 📄 ID ile Not bulma
    @Override
    public Optional<NotebookDTO> findById(int id) {
        String sql = "SELECT * FROM notebook_table WHERE id = ?";
        return selectSingle(sql, id);
    }

    // 📄 Title ile Not bulma
    @Override
    public Optional<NotebookDTO> findByName(String title) {
        String sql = "SELECT * FROM notebook_table WHERE title = ?";
        return selectSingle(sql, title);
    }

    // 🔄 Not Güncelleme
    @Override
    public Optional<NotebookDTO> update(int id, NotebookDTO updated) {
        Optional<NotebookDTO> existing = findById(id);
        if (existing.isPresent()) {
            String sql = "UPDATE notebook_table SET title=?, content=?, category=?, pinned=?, user_id=? WHERE id=?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, updated.getTitle());
                ps.setString(2, updated.getContent());
                ps.setString(3, updated.getCategory());
                ps.setBoolean(4, updated.getPinned());
                ps.setInt(5, updated.getUserDTO().getId());
                ps.setInt(6, id);

                int affected = ps.executeUpdate();
                if (affected > 0) {
                    updated.setId(id);
                    return Optional.of(updated);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }


    // 🗑️ Not silme
    @Override
    public Optional<NotebookDTO> delete(int id) {
        Optional<NotebookDTO> existing = findById(id);
        if (existing.isPresent()) {
            String sql = "DELETE FROM notebook_table WHERE id=?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                int affected = ps.executeUpdate();
                if (affected > 0) return existing;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }


    // 📄 Listeyi güncelleme
    @Override
    public NotebookDTO mapToObjectDTO(ResultSet rs) throws SQLException {
        UserDTO userDTO = getUserById(rs.getInt("user_id"));
        return NotebookDTO.builder()
                .id(rs.getInt("id"))
                .title(rs.getString("title"))
                .content(rs.getString("content"))
                .category(rs.getString("category"))
                .pinned(rs.getBoolean("pinned"))
                .userDTO(userDTO)
                .build();
    }


    private UserDTO getUserById(int userId) {
        UserDAO userDAO = new UserDAO();
        return userDAO.findById(userId).orElse(null);
    }


    // 📄 Not seçimi
    @Override
    public Optional<NotebookDTO> selectSingle(String sql, Object... params) {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapToObjectDTO(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


}