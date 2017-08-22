import javax.naming.Context;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terdlor on 03.06.2017.
 */
public class PostgreSQLJDBC {

    Context context;

    Connection c = null;
    Statement stmt = null;

    PostgreSQLJDBC(){
        //context = p_context;

        try {
            Class.forName("org.postgresql.Driver") ;
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:8082/postgres",
                            "postgres" , "admin");
            c.setAutoCommit(false) ;
        } catch (Exception e) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        System.out.println("Records created successfully") ;
    }

    //log_mark
    void Log(int p_type_id,  String p_date_in, String p_date_out, String p_note, String p_android_id){
        try {
            //p_note = new String(p_note.getBytes("utf-8"));
           // p_note  = p_note.getBytes("UTF-8").toString();
            // Вторым параметром передаем массив полей, значниея которых нам нужны
            PreparedStatement stmt = c.prepareStatement("INSERT INTO log_mark (type_id, data_in, data_out, note, android_id) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, p_type_id);
            stmt.setString(2, p_date_in);
            stmt.setString(3, p_date_out);
            stmt.setString(4, p_note);
            stmt.setString(5, p_android_id);
            stmt.executeUpdate();

            c.commit();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        System.out.println("Operation insert in LOG_mark successfully") ;
    }

    //mark
    void deleteMark(int p_id){
        try {
        // Вторым параметром передаем массив полей, значниея которых нам нужны
        PreparedStatement stmt = c.prepareStatement("DELETE from mark where  id = ?");
        stmt.setInt(1, p_id);
        stmt.executeUpdate();

        c.commit();
        stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        System.out.println("Operation delete "+p_id+" from mark successfully");
    }

    int insertMark(String p_name, String p_note){
        int Id = -1;
        try {
            // Вторым параметром передаем массив полей, значниея которых нам нужны
            PreparedStatement stmt = c.prepareStatement("INSERT INTO mark (name, note, start_date) VALUES (?, ?, CURRENT_DATE )", new String[] {"id", "name"});
            stmt.setString(1, p_name);
            stmt.setString(2, p_note);
            stmt.executeUpdate();


            // Получаем список данных дял сгенерированных ключей
            ResultSet gk = stmt.getGeneratedKeys();
            if(gk.next()) {
                // Получаем поле contact_id
                Id = gk.getInt("id");
            }
            c.commit();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        System.out.println("Operation insert in mark successfully (PK = "+Id+")") ;
        return Id;
    }

    void updateMark(int p_id, String p_name, String p_note){
        try {
            // Вторым параметром передаем массив полей, значниея которых нам нужны
            PreparedStatement stmt = c.prepareStatement("UPDATE mark set name = ?, note = ? where id = ?");
            stmt.setString(1, p_name);
            stmt.setString(2, p_note);
            stmt.setInt(3, p_id);
            stmt.executeUpdate();

            c.commit();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        System.out.println("Operation update mark successfully (PK = "+p_id+")") ;
    }

    //марки с вкусами
    List<SelectList> getSelectMark(){
        List<SelectList> list = new ArrayList<>();

        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT mark.id id_m, mark.name name_m, mark.note note_m, to_char(mark.start_date,'dd.mm.yyyy hh:mi:ss') date_m " +
                                                           "FROM mark ");
            while (rs.next()) {
                Integer id_mark = rs.getInt("id_m");
                String name_mark = rs.getString("name_m");
                String note_mark = rs.getString("note_m");
                String date_mark = rs.getString("date_m");

                SelectList item = new SelectList();
                item.setId_mark(id_mark);
                item.setName_mark(name_mark);
                item.setNote_mark(note_mark);
                item.setDate_mark(date_mark);

                List<SelectList> sublist = new ArrayList<>();

                Statement stmt2 = c.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT "+
                        "flavor.id id_f, flavor.name name_f, flavor.note note_f, to_char(flavor.start_date,'dd.mm.yyyy hh:mi:ss') date_f  " +
                        "FROM mark INNER JOIN flavor ON (flavor.id_mark = mark.id) where mark.id = "+id_mark);
                while (rs2.next()) {
                    Integer id = rs2.getInt("id_f");
                    String name = rs2.getString("name_f");
                    String note = rs2.getString("note_f");
                    String date = rs2.getString("date_f");

                    SelectList subitem = new SelectList();

                    subitem.setId(id);
                    subitem.setName(name);
                    subitem.setNote(note);
                    subitem.setDate(date);
                    item.subrow.add(subitem);
                }
                rs2.close();
                stmt2.close();
                list.add(item);
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            String ff = "";
            for ( StackTraceElement ee : e.getStackTrace())  ff = ff+" - "+ ee.getClassName() +" - "+ee.getMethodName()+" - "+ee.getLineNumber()+" \\ ";

            System.err.println( e.getClass() .getName()+": "+ e.getMessage() +" : "+ff);
        }
        return list;
    }

    //flavor
    int insertFlavor(int p_id_mark, String p_name, String p_note){
        int Id = -1;
        try {
            // Вторым параметром передаем массив полей, значниея которых нам нужны
            PreparedStatement stmt = c.prepareStatement("INSERT INTO flavor(id_mark, name, note, start_date) " +
                                                           " VALUES (?, ?, ?, CURRENT_DATE )", new String[] {"id", "name"});
            stmt.setInt(1, p_id_mark);
            stmt.setString(2, p_name);
            stmt.setString(3, p_note);
            stmt.executeUpdate();


            // Получаем список данных дял сгенерированных ключей
            ResultSet gk = stmt.getGeneratedKeys();
            if(gk.next()) {
                // Получаем поле contact_id
                Id = gk.getInt("id");
            }
            c.commit();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        System.out.println("Operation insert in flavor successfully (PK = "+Id+")") ;
        return Id;
    }

    void deleteFlavor(int p_id){
        try {
            // Вторым параметром передаем массив полей, значниея которых нам нужны
            PreparedStatement stmt = c.prepareStatement("DELETE from flavor where  id = ?");
            stmt.setInt(1, p_id);
            stmt.executeUpdate();

            c.commit();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        System.out.println("Operation delete "+p_id+" from flavor successfully");
    }

    void updateFlavor(int p_id, String p_name, String p_note, int p_id_mark){
        try {
            // Вторым параметром передаем массив полей, значниея которых нам нужны
            PreparedStatement stmt = c.prepareStatement("UPDATE flavor set name = ?, note = ? where id = ? and id_mark = ?");
            stmt.setString(1, p_name);
            stmt.setString(2, p_note);
            stmt.setInt(3, p_id);
            stmt.setInt(4, p_id_mark);
            stmt.executeUpdate();

            c.commit();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        System.out.println("Operation update flavor successfully (PK = "+p_id+")") ;
    }

    //sale
    void deleteSale(int p_id){
        try {
            // Вторым параметром передаем массив полей, значниея которых нам нужны
            PreparedStatement stmt = c.prepareStatement("DELETE from sale where  id = ?");
            stmt.setInt(1, p_id);
            stmt.executeUpdate();

            c.commit();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        System.out.println("Operation delete "+p_id+" from sale successfully");
    }

    int insertSale(String p_adress, String p_note){
        int Id = -1;
        try {
            // Вторым параметром передаем массив полей, значниея которых нам нужны
            PreparedStatement stmt = c.prepareStatement("INSERT INTO sale (adress, note, date) VALUES (?, ?, CURRENT_DATE )", new String[] {"id"});
            stmt.setString(1, p_adress);
            stmt.setString(2, p_note);
            stmt.executeUpdate();


            // Получаем список данных дял сгенерированных ключей
            ResultSet gk = stmt.getGeneratedKeys();
            if(gk.next()) {
                // Получаем поле contact_id
                Id = gk.getInt("id");
            }
            c.commit();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        System.out.println("Operation insert in sale successfully (PK = "+Id+")") ;
        return Id;
    }

    void updateSale(int p_id, String p_adress, String p_note, String p_date){
        try {
            // Вторым параметром передаем массив полей, значниея которых нам нужны
            PreparedStatement stmt = c.prepareStatement("UPDATE sale set adress = ?, note = ?, date = ? where id = ?");
            stmt.setString(1, p_adress);
            stmt.setString(2, p_note);
            stmt.setInt(3, p_id);
            stmt.executeUpdate();

            c.commit();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        System.out.println("Operation update sale successfully (PK = "+p_id+")") ;
    }

    List<SelectList> getSelectSale(){
        List<SelectList> list = new ArrayList<>();

        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sale");
            while (rs.next()) {
                int id = rs.getInt("id");
                String adress = rs.getString("adress");
                String note = rs.getString("note");
                String date = rs.getString("date");
                SelectList item = new SelectList();
                item.setId(id);
                item.setAdress(adress);
                item.setNote(note);
                item.setDate(date);
                list.add(item);
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        return list;
    }

    //mark_item
    void deleteMarkItem(int p_id_mark, int p_id_sale){
        try {
            // Вторым параметром передаем массив полей, значниея которых нам нужны
            PreparedStatement stmt = c.prepareStatement("DELETE from mark_item where  id_mark = ? and id_sale = ?");
            stmt.setInt(1, p_id_mark);
            stmt.setInt(2, p_id_sale);
            stmt.executeUpdate();

            c.commit();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        System.out.println("Operation delete mark ="+p_id_mark+", sale ="+p_id_sale+" from sale successfully");
    }

    void insertMarkItem(int p_id_mark, int p_id_sale, int p_count, long p_price, String p_note){
        try {
            // Вторым параметром передаем массив полей, значниея которых нам нужны
            PreparedStatement stmt = c.prepareStatement("INSERT INTO mark_item (id_mark, id_sale, count, price, note) " +
                                                                  "VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, p_id_mark);
            stmt.setInt(2, p_id_sale);
            stmt.setInt(3, p_count);
            stmt.setLong(4, p_price);
            stmt.setString(5, p_note);
            stmt.executeUpdate();
            c.commit();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        System.out.println("Operation insert in mark_item successfully (id_mark = "+p_id_mark+", id_sale = "+p_id_sale+")") ;
    }

    void updateMarkItem(int p_id_mark, int p_id_sale, int p_count, long p_price, String p_note, int p_rang){
        try {
            // Вторым параметром передаем массив полей, значниея которых нам нужны
            PreparedStatement stmt = c.prepareStatement("UPDATE mark_item set count = ?, price = ?, note = ?, rang = ? where id_mark = ? and id_sale = ?");
            stmt.setInt(1, p_count);
            stmt.setLong(2, p_price);
            stmt.setString(3, p_note);
            stmt.setInt(4, p_rang);
            stmt.setInt(5, p_id_mark);
            stmt.setInt(6, p_id_sale);
            stmt.executeUpdate();

            c.commit();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        System.out.println("Operation update mark_item successfully (id_mark = "+p_id_mark+", id_sale = "+p_id_sale+")") ;
    }

    List<SelectList> getSelectMarkItem(int p_id_sale){
        List<SelectList> list = new ArrayList<>();

        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM mark_item where id_sale = "+p_id_sale);
            while (rs.next()) {
                int id_mark = rs.getInt("id_mark");
                int id_sale = rs.getInt("id_sale");
                int count = rs.getInt("count");
                long price = rs.getLong("price");
                int rang = rs.getInt("rang");
                String note = rs.getString("note");
                SelectList item = new SelectList();
                item.setId_mark(id_mark);
                item.setId_sale(id_sale);
                item.setCount(count);
                item.setPrice(price);
                item.setRang(rang);
                item.setNote(note);
                list.add(item);
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        return list;
    }

    //какаята выборка
    String varcharSelect(String p_fnc_name, String p_param){
        String Id = "";
        try {
            stmt = c.createStatement();
            PreparedStatement stmt = c.prepareStatement("Select "+p_fnc_name+" ( ? )");
            stmt.setString(1, p_param);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                Id = rs.getString(p_fnc_name);
            }
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass() .getName()+": "+ e.getMessage() );
        }
        return Id;
    }


    public  static void main(String ar[]){
        PostgreSQLJDBC pp = new PostgreSQLJDBC();
      /*  TaskClass task = new TaskClass();
        task.setId("45");
        task.setName("какоето имя");
        task.setErr_code(0);
        task.setErr_note("Если не ноль то плохо");
        task.setNote("описание происходящего");
        task.setRow(pp.getSelectMarkItem(5));

        System.out.println(SimplePars.getXml(task)) ;
*/


        TaskClass res = new TaskClass();
        res.setRow(pp.getSelectMark());
        System.out.println(SimplePars.getXml(res)) ;

        /*
        for (SelectList el : list) {
            System.out.println(el.getNote()) ;
        }*/



    }

}
