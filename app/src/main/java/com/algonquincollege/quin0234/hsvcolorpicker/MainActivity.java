package com.algonquincollege.quin0234.hsvcolorpicker;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

/**
 * HSV Color Picker
 *
 * Main Controller
 * @author Paul Quinnell (quin0234@algonquinlive.com)
 * @version 1.0
 */
public class MainActivity extends Activity implements Observer, SeekBar.OnSeekBarChangeListener {

    private AboutDialogFragment mAboutDialog;
    private HSVModel mHSVModel;
    private TextView mColorSwatch;

    private SeekBar mHueSB;
    private SeekBar mSatSB;
    private SeekBar mValSB;

    private TextView mHueTV;
    private TextView mSatTV;
    private TextView mValTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAboutDialog = new AboutDialogFragment();


        mHSVModel = new HSVModel();
        mHSVModel.addObserver(this);


        mColorSwatch = (TextView) findViewById(R.id.colorSwatch);
        mHueSB = (SeekBar) findViewById(R.id.hueSB);
        mSatSB = (SeekBar) findViewById(R.id.saturationSB);
        mValSB = (SeekBar) findViewById(R.id.valueSB);
        mHueTV = (TextView) findViewById(R.id.hue);
        mSatTV = (TextView) findViewById(R.id.saturation);
        mValTV = (TextView) findViewById(R.id.value);


        mHueSB.setOnSeekBarChangeListener(this);
        mSatSB.setOnSeekBarChangeListener(this);
        mValSB.setOnSeekBarChangeListener(this);

        mColorSwatch.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                Toast.makeText( getApplicationContext(), HSVMessage(), Toast.LENGTH_SHORT ).show();
                return true;
            }
        });

        this.updateView();

    }

    private String HSVMessage(){
        return getResources().getString(
                R.string.hsv_values, mHSVModel.getHue(),
                mHSVModel.getSaturation(),
                mHSVModel.getValue());
    }

    private void updateColorSwatch() {
        float[] hsvColor = {mHSVModel.getHue(), mHSVModel.getSaturation()/100.f, mHSVModel.getValue()/100.f};
        mColorSwatch.setBackgroundColor(Color.HSVToColor(hsvColor));
    }

    private void updateHueSB(){
        mHueSB.setProgress( mHSVModel.getHue() );
    }

    private void updateSaturationSB(){
        mSatSB.setProgress( mHSVModel.getSaturation() );
    }


    private void updateValueSB(){
        mValSB.setProgress( mHSVModel.getValue() );
    }


    public void updateView() {
        this.updateColorSwatch();
        this.updateHueSB();
        this.updateSaturationSB();
        this.updateValueSB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_about){
            mAboutDialog.show( getFragmentManager(), "About" );
            return true;
        }

        return false;
    }


    @Override
    public void update(Observable observable, Object o) {
        this.updateView();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {


        if ( !b ) {
            return;
        }

        switch ( seekBar.getId() ) {
            case R.id.hueSB:
                mHSVModel.setHue(mHueSB.getProgress());
                mHueTV.setText(getResources().getString(R.string.hue_progress, i).toUpperCase());
                break;

            case R.id.saturationSB:
                mHSVModel.setSaturation(mSatSB.getProgress());
                mSatTV.setText(getResources().getString(R.string.saturation_progress, i).toUpperCase());
                break;

            case R.id.valueSB:
                mHSVModel.setValue(mValSB.getProgress());
                mValTV.setText(getResources().getString(R.string.value_progress, i).toUpperCase());
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.hueSB:
                mHueTV.setText(getResources().getString(R.string.hue));
                break;
            case R.id.saturationSB:
                mSatTV.setText(getResources().getString(R.string.saturation));
                break;
            case R.id.valueSB:
                mValTV.setText(getResources().getString(R.string.value));
                break;
        }
    }

    public void onColorButtonClick(View view) {
        switch (view.getId()){
            case R.id.blackButton:
                mHSVModel.asBlack();
                break;
            case R.id.redButton:
                mHSVModel.asRed();
                break;
            case R.id.limeButton:
                mHSVModel.asLime();
                break;
            case R.id.blueButton:
                mHSVModel.asBlue();
                break;
            case R.id.yellowButton:
                mHSVModel.asYellow();
                break;
            case R.id.cyanButton:
                mHSVModel.asCyan();
                break;
            case R.id.magentaButton:
                mHSVModel.asMagenta();
                break;
            case R.id.silverButton:
                mHSVModel.asSilver();
                break;
            case R.id.grayButton:
                mHSVModel.asGray();
                break;
            case R.id.maroonButton:
                mHSVModel.asMaroon();
                break;
            case R.id.oliveButton:
                mHSVModel.asOlive();
                break;
            case R.id.greenButton:
                mHSVModel.asGreen();
                break;
            case R.id.purpleButton:
                mHSVModel.asPurple();
                break;
            case R.id.tealButton:
                mHSVModel.asTeal();
                break;
            case  R.id.navyButton:
                mHSVModel.asNavy();
                break;
        }

        Toast.makeText( getApplicationContext(), HSVMessage(), Toast.LENGTH_SHORT ).show();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        int[] hsvColor = { mHSVModel.getHue(), mHSVModel.getSaturation(), mHSVModel.getValue() };
        savedInstanceState.putIntArray("HSV_COLOR", hsvColor);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        if (savedInstanceState != null && savedInstanceState.containsKey("HSV_COLOR")){
            int[] hsvColor = savedInstanceState.getIntArray("HSV_COLOR");

            if (hsvColor != null && hsvColor.length == 3) {
                mHSVModel.setHSV(hsvColor[0],hsvColor[1],hsvColor[2]);
            }
        }
    }
}