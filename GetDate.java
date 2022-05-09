
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDate {

    public static String date(){

        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat(" E_dd_MM_yyyy_hh_mm_ss_a");
        String stringDate= DateFor.format(date);
        return stringDate;

    }

}
