import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terdlor on 03.06.2017.
 */
@Root(name ="ROW", strict = false)
public class SelectList {

    @Element(required=false, name="ID")
    private Integer Id;
    @Element(required=false, name="ID_MARK")
    private Integer Id_mark;
    @Element(required=false, name="ID_SALE")
    private Integer Id_sale;
    @Element(required=false, name="COUNT")
    private Integer Count;
    @Element(required=false, name="RANG")
    private Integer Rang;
    @Element(required=false, name="PRICE")
    private Long Price;
    @Element(required=false, name="NAME")
    private String Name;
    @Element(required=false, name="NAME_MARK")
    private String Name_mark;
    @Element(required=false, name="NOTE")
    private String Note;
    @Element(required=false, name="NOTE_MARK")
    private String Note_mark;
    @Element(required=false, name="DATE")
    private String Date;
    @Element(required=false, name="DATE_MARK")
    private String Date_mark;
    @Element(required=false, name="ADRESS")
    private String Adress;

    @ElementList(required=false, name="ROW", inline = true)
    public List<SelectList> subrow;




    void setId (Integer p_id){
        this.Id = p_id;
    }
    Integer getId(){
        return Id;
    }

    void setRang (Integer p_id){
        this.Rang = p_id;
    }
    Integer getRang(){
        return Rang;
    }

    void setPrice (long p_id){
        this.Price = p_id;
    }
    long getPrice(){
        return Price;
    }

    void setId_mark (Integer p_id){
        this.Id_mark = p_id;
    }
    Integer getId_mark(){
        return Id_mark;
    }

    void setCount (Integer p_id){
        this.Count = p_id;
    }
    Integer getCount(){
        return Count;
    }

    void setId_sale (Integer p_id){
        this.Id_sale = p_id;
    }
    Integer getId_sale(){
        return Id_sale;
    }

    void setName_mark(String p_name){
        this.Name_mark = p_name;
    }
    String getName_mark (){
        return this.Name_mark;
    }

    void setName(String p_name){
        this.Name = p_name;
    }
    String getName (){
        return this.Name;
    }

    void setNote_mark(String p_note){
        this.Note_mark = p_note;
    }
    String getNote_mark(){
        return this.Note_mark;
    }

    void setNote(String p_note){
        this.Note = p_note;
    }
    String getNote(){
        return this.Note;
    }

    void  setDate(String p_date){
        this.Date =p_date;
    }
    String getDate(){
        return  Date;
    }

    void  setDate_mark(String p_date){
        this.Date_mark =p_date;
    }
    String getDate_mark(){
        return  Date_mark;
    }

    void  setAdress(String p_Adress){
        this.Adress =p_Adress;
    }
    String getAdress(){
        return  Adress;
    }

    public List<SelectList> getRow(){return  subrow;}
    public void setRow(List<SelectList> p_list){this.subrow = p_list;}

    SelectList(){
        subrow = new ArrayList<SelectList>();
    }
}
