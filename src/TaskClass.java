

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terdlor on 23.04.2017.
 */
@Root(name ="HEAD", strict = false)
public class TaskClass {

    @Element(required=false, name="ID")
    private String id;

    @Element(required=false, name="NAME")
    private String name;

    @Element(required=false, name="NOTE")
    private String note;

    @ElementList(required=false, name="ROW", inline = true)
    private List<SelectList> row;

    @Element(required=false, name="ERR_CODE")
    private int err_code;

    @Element(required=false, name="ERR_NOTE")
    private String err_note;


    public int getErr_code() {
        return err_code;
    }
    public void setErr_code(int p_err_note) {
        this.err_code = p_err_note;
    }


    public String getErr_note() {
        return err_note;
    }
    public void setErr_note(String p_err_note) {
        this.err_note = p_err_note;
    }

    public String getId() {
        return id;
    }
    public void setId(String site) {
        this.id = site;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String desc) {
        this.note = desc;
    }

    public List<SelectList> getRow(){return  row;}
    public void setRow(List<SelectList> p_list){this.row = p_list;}

    TaskClass(){
        row = new ArrayList<SelectList>();
    }
}
