package  com.example.shaiful.eventbundling.configure;
import com.example.shaiful.eventbundling.contract.Contract;
import  com.example.shaiful.eventbundling.model.Distribution;
import com.example.shaiful.eventbundling.model.DistributionFactory;
import com.example.shaiful.eventbundling.model.Emitter;
import com.example.shaiful.eventbundling.model.Model;
import com.example.shaiful.eventbundling.model.PrQueue;
import com.example.shaiful.eventbundling.presenter.DroppingPresenter;
import com.example.shaiful.eventbundling.presenter.BundlingSendAllPresenter;
import com.example.shaiful.eventbundling.presenter.NoBundlingPresenter;
import com.example.shaiful.eventbundling.presenter.PresenterFactory;
import com.example.shaiful.eventbundling.view.View;
import com.example.shaiful.eventbundling.view.MainActivity;

public class ConfigureAndRun {

	public static ConfigureAndRun instance=null;
	public int noOfEmitters;
	public float rate;
	public int duration;
	public Distribution d;
	public int bundlingTime;
	public static int waitBeforeSimulate=10000;
	public static boolean continueRun;
	Contract.Presenter p;
	Model m;

	public static synchronized ConfigureAndRun getInstance(){

	    if(instance==null)
			instance= new ConfigureAndRun();

		return instance;
	}


	private ConfigureAndRun() {

	    this.noOfEmitters = MainActivity.noOfEmitters;
	    this.rate = MainActivity.rate;
        this.d = DistributionFactory.createDistribution
                (MainActivity.distributionType);

        this.duration = MainActivity.duration;
        continueRun = true;
       	p = PresenterFactory.createPresenter(MainActivity.presenterType);
                BundlingSendAllPresenter.getInstance();

        bundlingTime=MainActivity.bundlingTime;
        m = Model.getInstance();
        m.registerObserver(p);
	}

	public void setConfigurations()
	{

		for(int i = 0; i< noOfEmitters; i++) {

		    View v=new View(i);
			p.registerObserver(v);

            PrQueue.queue.add(new Emitter(i));
		}
	}
	
	
	public void runExperiments() {

	// wait till things are cool down
        try {
			Thread.sleep(waitBeforeSimulate);
        }catch(Exception e){}

        p.setTimerTransmission();
        Model m=Model.getInstance();
        m.checkAndRun();

	}
		
}




