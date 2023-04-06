import java.util.ArrayList;
import DispatcherController;

public class DisplayOpenRidesPage{
    // uses database of available rides and maps and requestride share page to find nearst available rides
    // and display it

    TextView text_view;
    ArrayList<TripInformation> availableRides = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.DisplayOpenRidesPage);

        text_view = findViewById(R.id.text_view);
    }

    // content that will be displayed on the page (all the open rides with the accept and reject buttons)
    private void showRidesContent(){
        //call dispatcher to get array of rides offers

    }
}