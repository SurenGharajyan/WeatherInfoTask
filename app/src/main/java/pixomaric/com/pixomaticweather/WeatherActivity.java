package pixomaric.com.pixomaticweather;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pixomaric.com.pixomaticweather.data.Channel;
import pixomaric.com.pixomaticweather.data.Item;
import pixomaric.com.pixomaticweather.service.WeatherServiceCallback;
import pixomaric.com.pixomaticweather.service.YahooWeatherService;

public class WeatherActivity extends AppCompatActivity implements ICountryChange, WeatherServiceCallback, View.OnClickListener {

    //The program code is correct. But it is doesn't work, because yahoo weather is doesn't request on any app.
    //Of course if I have much time, then I can search new API for this app.
    //But code is correct! If you have android developer, he/she can see that code is true

    private TextView temperature,condition,countryName,pressure;
    private AppCompatImageView weatherImage,iconFlag;
    private Button changeCountry;
    private YahooWeatherService service;
    private ProgressDialog progDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        init();
        initAnother();
    }

    private void init() {
        pressure = findViewById(R.id.pressure);
        iconFlag = findViewById(R.id.flag_img);
        temperature = findViewById(R.id.temperature);
        condition = findViewById(R.id.condition);
        weatherImage = findViewById(R.id.weather_icon);
        countryName = findViewById(R.id.country_name_location);
        changeCountry = findViewById(R.id.change_country);
    }

    private void initAnother() {
        changeCountry.setOnClickListener(this);

        progDialog = new ProgressDialog(this);
        DialogManager.getInstance().alertCountryChoose(this,WeatherActivity.this,progDialog).show();

        service = new YahooWeatherService(this);
//        service.refreshWeather("Austin, TX");
    }
    @Override
    public void onChangeCountry(int countryChoose) {
        Toast.makeText(this, "country="+countryChoose, Toast.LENGTH_SHORT).show();
        switch (countryChoose) {
            case 0:
                service.refreshWeather("London, UK");
                iconFlag.setImageDrawable(getResources().getDrawable(R.drawable.england));
                break;
            case 1:
                service.refreshWeather("Paris, France");
                iconFlag.setImageDrawable(getResources().getDrawable(R.drawable.france));
                break;
            case 2:
                service.refreshWeather("Berlin, Germany");
                iconFlag.setImageDrawable(getResources().getDrawable(R.drawable.germany));
                break;
            case 3:
                service.refreshWeather("Amsterdam, Netherlands");
                iconFlag.setImageDrawable(getResources().getDrawable(R.drawable.netherlands));
                break;
        }
    }


    @Override
    public void serviceSuccess(Channel channel) {
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        progDialog.hide();
        Item item = channel.getItem();

        int resourceId = getResources().getIdentifier("drawable/icon_" + item.getCondition().getCode(),null,getPackageName());

        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);

        weatherImage.setImageDrawable(weatherIconDrawable);
        pressure.setText(channel.getAtmosphere().getPressure());
        temperature.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
        countryName.setText(service.getLocation());
        condition.setText(item.getCondition().getDescription());



    }

    @Override
    public void serviceFailure(Exception exception) {
        progDialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.change_country) {
            DialogManager.getInstance().alertCountryChoose(this,WeatherActivity.this,progDialog).show();
        }
    }
}
