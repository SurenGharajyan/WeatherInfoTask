package pixomaric.com.pixomaticweather.data;

import org.json.JSONObject;

/**
 * Created by USER on 06.02.2018.
 */

public class Atmosphere implements JSONPopulator {
    private int pressure;

    public int getPressure() {
        return pressure;
    }

    @Override
    public void populate(JSONObject data) {
        pressure = data.optInt("pressure");
    }
}
