import com.sun.jmx.snmp.tasks.Task;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * Created by Terdlor on 20.05.2017.
 */

public class ServletSelect extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostgreSQLJDBC pp = new PostgreSQLJDBC();
        sysoutnewcod("-------------------------------------------------POST----------------------");
        sysoutnewcod((new Date()).toString());
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            sysoutnewcod("                     data_in");
            sysoutnewcod(req.getRequestURI());
            int id = Integer.valueOf(req.getParameter("id"));
            String ANDROID_ID = req.getParameter("ANDROID_ID");

            sysoutnewcod("id = "+id) ;
            sysoutnewcod("ANDROID_ID = "+ANDROID_ID) ;

            String read = "";
            while (req.getReader().ready()) {
                String cur = "";
                cur = req.getReader().readLine();
                read = read + cur;
            }
            //sysoutnewcod(read);
            sysoutnewcod("--------------------------");
            switch (id) {
                case 1: //вставка новой марки
                    SelectList item = new SelectList();
                    item = (SelectList) SimplePars.getObject(read, SelectList.class);
                    sysoutnewcod(SimplePars.getXml(item));
                    sysoutnewcod("--------------------------");
                    pp.insertMark(item.getName_mark(), item.getNote_mark());
                    sysoutnewcod("--------------------------");
                    TaskClass res = new TaskClass();
                    res.setErr_code(0);
                    sysoutnewcod("                     data_out");
                    sysoutnewcod(SimplePars.getXml(res));
                    resp.getWriter().write(SimplePars.getXml(res));

                    pp.Log(id,SimplePars.getXml(item), SimplePars.getXml(res),"insert mark",ANDROID_ID);
                    break;
                case 2: //удаление марки
                    item = (SelectList) SimplePars.getObject(read, SelectList.class);
                    sysoutnewcod(SimplePars.getXml(item));
                    sysoutnewcod("--------------------------");
                    pp.deleteMark(item.getId_mark());
                    sysoutnewcod("--------------------------");
                    res = new TaskClass();
                    res.setErr_code(0);
                    sysoutnewcod("                     data_out");
                    sysoutnewcod(SimplePars.getXml(res));
                    resp.getWriter().write(SimplePars.getXml(res));

                    pp.Log(id,SimplePars.getXml(item), SimplePars.getXml(res),"delete mark",ANDROID_ID );
                    break;
                case 3: //обновление марки
                    item = (SelectList) SimplePars.getObject(read, SelectList.class);
                    sysoutnewcod(SimplePars.getXml(item));
                    sysoutnewcod("--------------------------");
                    pp.updateMark(item.getId_mark(), item.getName_mark(), item.getNote_mark());
                    sysoutnewcod("--------------------------");
                    res = new TaskClass();
                    res.setErr_code(0);
                    sysoutnewcod("                     data_out");
                    sysoutnewcod(SimplePars.getXml(res));
                    resp.getWriter().write(SimplePars.getXml(res));

                    pp.Log(id,SimplePars.getXml(item), SimplePars.getXml(res),"update mark",ANDROID_ID );
                    break;
                case 4: //вставка вкуса
                    item = (SelectList) SimplePars.getObject(read, SelectList.class);
                    sysoutnewcod(SimplePars.getXml(item));
                    sysoutnewcod("--------------------------");
                    pp.insertFlavor(item.getId_mark(), item.getName(), item.getNote());
                    sysoutnewcod("--------------------------");
                    res = new TaskClass();
                    res.setErr_code(0);
                    sysoutnewcod("                     data_out");
                    sysoutnewcod(SimplePars.getXml(res));
                    resp.getWriter().write(SimplePars.getXml(res));

                    pp.Log(id,SimplePars.getXml(item), SimplePars.getXml(res),"insert flavor",ANDROID_ID );
                    break;
                case 5: //удаление вкуса
                    item = (SelectList) SimplePars.getObject(read, SelectList.class);
                    sysoutnewcod(SimplePars.getXml(item));
                    sysoutnewcod("--------------------------");
                    pp.deleteFlavor(item.getId());
                    sysoutnewcod("--------------------------");
                    res = new TaskClass();
                    res.setErr_code(0);
                    sysoutnewcod("                     data_out");
                    sysoutnewcod(SimplePars.getXml(res));
                    resp.getWriter().write(SimplePars.getXml(res));

                    pp.Log(id,SimplePars.getXml(item), SimplePars.getXml(res),"delete flavor",ANDROID_ID );
                    break;
                case 6: //обновление вкуса
                    item = (SelectList) SimplePars.getObject(read, SelectList.class);
                    sysoutnewcod(SimplePars.getXml(item));
                    sysoutnewcod("--------------------------");
                    pp.updateFlavor(item.getId(), item.getName(), item.getNote(), item.getId_mark());
                    sysoutnewcod("--------------------------");
                    res = new TaskClass();
                    res.setErr_code(0);
                    sysoutnewcod("                     data_out");
                    sysoutnewcod(SimplePars.getXml(res));
                    resp.getWriter().write(SimplePars.getXml(res));

                    pp.Log(id,SimplePars.getXml(item), SimplePars.getXml(res),"update flavor",ANDROID_ID );
                    break;
                default:
                    sysoutnewcod("uncnow_type");
                    sysoutnewcod("--------------------------");
                    res = new TaskClass();
                    res.setErr_code(-10);
                    res.setErr_note("uncnow_type");
                    sysoutnewcod("                     data_out");
                    sysoutnewcod(SimplePars.getXml(res));
                    resp.getWriter().write(SimplePars.getXml(res));

                    pp.Log(0,read, SimplePars.getXml(res),"uncnow_type",ANDROID_ID );
                    break;
            }
        } catch (Exception e){
            TaskClass res = new TaskClass();
            res.setErr_code(-10);
            res.setErr_note(e.toString());
            sysoutnewcod("                     data_out");
            sysoutnewcod(SimplePars.getXml(res));
            resp.getWriter().write(SimplePars.getXml(res));
            String gg="";
            for(StackTraceElement  s : e.getStackTrace() )
                gg = gg+"--"+s.getMethodName()+s.getLineNumber();

            pp.Log(-1,gg, SimplePars.getXml(res),"Exception", "" );
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostgreSQLJDBC pp = new PostgreSQLJDBC();
        sysoutnewcod("-------------------------------------------------GET----------------------");
        sysoutnewcod((new Date()).toString());
        try {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        sysoutnewcod("                     data_in") ;
        sysoutnewcod(req.getRequestURI() ) ;
        int id = Integer.valueOf(req.getParameter("id"));
        String ANDROID_ID = req.getParameter("ANDROID_ID");

        sysoutnewcod("id = "+id) ;
        sysoutnewcod("ANDROID_ID = "+ANDROID_ID) ;
        sysoutnewcod("--------------------------");
        switch(id) {
            case 1:
                TaskClass res = new TaskClass();
                res.setRow(pp.getSelectMark());

                sysoutnewcod("                     data_out") ;
                sysoutnewcod(SimplePars.getXml(res)) ;

                resp.getWriter().write(SimplePars.getXml(res));
                break;
            default:
                sysoutnewcod("uncnow_type") ;
                sysoutnewcod("--------------------------");
                res = new TaskClass();
                res.setErr_code(-10);
                res.setErr_note("uncnow_type");
                sysoutnewcod("                     data_out");
                sysoutnewcod(SimplePars.getXml(res));
                resp.getWriter().write(SimplePars.getXml(res));

                pp.Log(0,"GET", SimplePars.getXml(res),"uncnow_type",ANDROID_ID );
                break;
        }
        } catch (Exception e){
            TaskClass res = new TaskClass();
            res.setErr_code(-10);
            res.setErr_note(e.toString());
            sysoutnewcod("                     data_out");
            sysoutnewcod(SimplePars.getXml(res));
            resp.getWriter().write(SimplePars.getXml(res));
            String gg="";
            for(StackTraceElement  s : e.getStackTrace() )
                gg = gg+"--"+s.getMethodName()+s.getLineNumber();

            pp.Log(-1,gg, SimplePars.getXml(res),"Exception","" );
        }
    }

    void sysoutnewcod (String sssss){
            try {
                //sssss = new String(sssss.getBytes("utf-8"));
               // System.out.println(sssss) ;
                PrintStream printStream = new PrintStream(System.out, true, "cp866");
                printStream.println(sssss);
            } catch (Exception e){
                System.out.println(e.toString()) ;
                System.out.println(sssss ) ;
            }
    }
}
