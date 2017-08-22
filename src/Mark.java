/**
 * Created by Terdlor on 22.08.2017.
 */

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Mark")
public class Mark {
    public Mark(){

    }
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected  int id;
    @Column(name= "name")
    protected  String name;

    @Column(name= "addDate")
    @Temporal(value=TemporalType.DATE)
    protected Date addDate;

    @Column(name= "note")
    protected String note;

    protected  String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
