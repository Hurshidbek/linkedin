import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, TelegramApiException {

        long st = System.nanoTime();
        getTable();
        long et = System.nanoTime();

        System.out.println(et-st);

    }

    public static void getJson() throws SQLException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/linkedin","postgres","1");
        PreparedStatement preparedStatement = connection.prepareStatement("select get_post_reaction_json(?)");
        preparedStatement.setString(1,"79fcdbf8-5f82-4060-9a97-88f74d6cc093");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        objectMapper.readValue(resultSet.getString(1),UserReaction[].class);



    }
    public static void getTable() throws SQLException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/linkedin","postgres","1");
        PreparedStatement preparedStatement = connection.prepareStatement("select * from get_post_reaction(?)");
        preparedStatement.setString(1,"79fcdbf8-5f82-4060-9a97-88f74d6cc093");
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<UserReaction> list = new ArrayList<>();
        while (resultSet.next()){
            UserReaction userReaction = new UserReaction();
            userReaction.setUserId(UUID.fromString(resultSet.getString("user_id")));
            userReaction.setReaction_type(resultSet.getInt("i_reaction_type"));
            userReaction.setAbout(resultSet.getString("i_about"));
            userReaction.setAvatar(resultSet.getBytes("i_avatar"));
            userReaction.setUsername(resultSet.getString("full_name"));
            list.add(userReaction);
        }

    }
}


