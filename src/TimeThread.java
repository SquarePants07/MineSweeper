/**
 * Created by Anatoliy.Polyakov on 29.08.2019.
 */
public class TimeThread extends Thread {
    int timer;
    boolean stop;
    MainWindow parentWindow;

    public TimeThread(MainWindow parentWindow){
        timer = 0;
        stop = false;
        this.parentWindow = parentWindow;
    }

    @Override
    public void run(){
        while(!stop){
            try {
                sleep(1000);
                timer++;
                parentWindow.time.setText("Время: " + timer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
