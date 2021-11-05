package com.springbootsecurityrest.services;

import com.springbootsecurityrest.model.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;

@Service
public class DBService {

   private static File file = new File("src/main/resources/config.properties");

    public static String addUser(String login, String password, String firstname, String lastname,
                                 int age, String address, String role) throws FileNotFoundException, SQLException {

        String add_user ="Insert into users (login, password, first_name, last_name, age, address, role) " +
                "values ('"+ login + "', '" + password + "', '" + firstname + "', '" + lastname+ "', "+ age +
                ", '"+ address+ "', '"+role+"')";

        System.out.println( add_user);

       try{ Scanner sc = new Scanner(file);
        String url = sc.next();
        Connection con = DriverManager.getConnection(url);
        Statement st = con.createStatement();
        st.executeUpdate(add_user);
        st.close();
        con.close();
        sc.close();

        return "User inserted successfully!";}
       catch (Exception e){
           return "Something went wrong please check again!";
       }
    }

    public static String updateUser(int id, String login, String password, String firstname, String lastname,
                                 int age, String address, String role) throws FileNotFoundException, SQLException {

        String update_User ="update users set login='"+login+"', password='"+password +
                "', first_name='" + firstname + "', last_name='" + lastname + "', age=" + age +
                ", address='" + address+ "', role='" + role+ "' where id="+id;

            try{ Scanner sc = new Scanner(file);
            String url = sc.next();
            Connection con = DriverManager.getConnection(url);
            Statement st = con.createStatement();
            st.executeUpdate(update_User);
            st.close();
            con.close();
            sc.close();

            return "User updated successfully!";}
        catch (Exception e){
            return "Something went wrong please check again!";
        }
    }

    public static String createQuery(int user_id, String header, String body, String date)
            throws FileNotFoundException, SQLException {

        String create_query ="Insert into queries (user_id, header, body, created_date) " +
                "values("+ user_id + ", '" + header + "', '" + body+ "', '"+date.toString() +"')";

        try{ Scanner sc = new Scanner(file);
            String url = sc.next();
            Connection con = DriverManager.getConnection(url);
            Statement st = con.createStatement();
            st.executeUpdate(create_query);
            st.close();
            con.close();
            sc.close();

            return "Application sent successfully!";}
        catch (Exception e){
            return "Something went wrong please check again!";
        }
    }

    public static String updateQuery(int id, String body, int user_id) throws FileNotFoundException, SQLException {

            String update_Query ="update queries set body='"+body+"' where id="+id + " and user_id=" + user_id;

            try{ Scanner sc = new Scanner(file);
                String url = sc.next();
                Connection con = DriverManager.getConnection(url);
                Statement st = con.createStatement();
                st.executeUpdate(update_Query);
                st.close();
                con.close();
                sc.close();

                return "Application updated successfully!";}
            catch (Exception e){
                return "Something went wrong please check again!";
            }
        }

    public static String DeleteQuery(int id, int user_id) throws FileNotFoundException, SQLException {

        String delete_Query ="delete from queries where id="+id + " and user_id=" + user_id;

        try{ Scanner sc = new Scanner(file);
            String url = sc.next();
            Connection con = DriverManager.getConnection(url);
            Statement st = con.createStatement();
            int count=st.executeUpdate(delete_Query);
            st.close();
            con.close();
            sc.close();
            return count==0? "no record found" : "Application removed successfully!";
        }
        catch (Exception e){
            return "Something went wrong please check again!";
        }
    }

    public static String addComment(int appclication_id, int admin_id, String text, String date)
            throws FileNotFoundException, SQLException{

        String add_comment ="Insert into feedback (application_id, admin_id, text, date) " +
                "values( "+ appclication_id + ", " + admin_id+ ", '"+text +"', '" + date + "')";

        try{ Scanner sc = new Scanner(file);
            String url = sc.next();
            Connection con = DriverManager.getConnection(url);
            Statement st = con.createStatement();
            st.executeUpdate(add_comment);
            st.close();
            con.close();
            sc.close();

            return "Feedback successfully attached!";}
        catch (Exception e){
            return "Something went wrong please check again!";
        }
    }

    public static JSONArray getCommentsByAppId(int application_id, int user_id){

        try{ Scanner sc = new Scanner(file);
            String url = sc.next();
            Connection con = DriverManager.getConnection(url);
            Statement st = con.createStatement();
            JSONArray json = new JSONArray();

            ResultSet rs = st.executeQuery("select ap.id as application_id, ap.header, f.text as comment, " +
                    "u.first_name as commentor, f.date from feedback f inner join queries ap on f.application_id=ap.id " +
                    "inner join users u on f.admin_id=u.id  where f.application_id="+application_id +" and ap.user_id = "+user_id+
                    " order by f.date");

            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                int numColumns = rsmd.getColumnCount();
                JSONObject obj = new JSONObject();
                for (int i=1; i<=numColumns; i++) {
                    String column_name = rsmd.getColumnName(i);
                    obj.put(column_name, rs.getObject(column_name));

            }
                json.put(obj);
            }
            st.close();
            con.close();
            sc.close();

            return json;
        }
        catch (Exception e){
            return null;
        }
    }
}
