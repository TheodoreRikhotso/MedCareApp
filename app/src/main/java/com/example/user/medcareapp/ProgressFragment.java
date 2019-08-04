package com.example.user.medcareapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.medcareapp.com.example.user.medcareapp.pojo.Progress;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


public class ProgressFragment extends Fragment {
    private Toolbar mTopToolbar;
    private LineChartView lineChartView;
    private Calendar myCalendar;

    private EditText etGlucoseLevel = null;
    private EditText etSelectedDate = null;
    private Button saveUserDataButton = null;
    private Button cancelUserDataButton = null;
    private View popupInputDialogView = null;
    private ArrayList<Integer> yAxisData;
    private View view;
    private FirebaseAuth auth;
    private DatabaseReference illnessDB;
    public ProgressFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view =inflater.inflate(R.layout.fragment_progress, container, false);
        lineChartView = view.findViewById(R.id.chart);
        myCalendar = Calendar.getInstance();
        yAxisData = new ArrayList();
        mTopToolbar = view.findViewById(R.id.my_toolbar);
        auth = FirebaseAuth.getInstance();

        mTopToolbar.setTitle("Progress");
        ((AppCompatActivity)getActivity()).setSupportActionBar(mTopToolbar);

        DatabaseReference progessRef = FirebaseDatabase.getInstance().getReference("Illness/diabetics/" + auth.getUid());
        progessRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    Progress progress = catalogSnapshot.getValue(Progress.class);
                    graphProgress(progress.getNum());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }
    private void initPopupViewControls()
    {
        // Get layout inflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

        // Inflate the popup dialog from a layout xml file.
        popupInputDialogView = layoutInflater.inflate(R.layout.popup_input_dialog, null);

        // Get user input edittext and button ui controls in the popup dialog.
        etGlucoseLevel =popupInputDialogView.findViewById(R.id.etGlucoseLevel);
        etSelectedDate =  popupInputDialogView.findViewById(R.id.Birthday);

        saveUserDataButton = popupInputDialogView.findViewById(R.id.button_save_user_data);
        cancelUserDataButton = popupInputDialogView.findViewById(R.id.button_cancel_user_data);



    }

    public void graphProgress(int num){
        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();

        Line line = new Line(yAxisValues).setColor(Color.parseColor("#866ec7"));
        yAxisData.add(num);


        for (int i = 0; i < yAxisData.size(); i++) {
            int dayNum=i+1;
            axisValues.add(i, new AxisValue(i).setLabel("Day "+dayNum));
        }

        for (int i = 0; i < yAxisData.size(); i++) {
            yAxisValues.add(new PointValue(i, yAxisData.get(i)));

        }

        List lines = new ArrayList();
        lines.add(line);


        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#03A9F4"));
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        yAxis.setName("Glucose mmol/L");
        yAxis.setTextColor(Color.parseColor("#03A9F4"));

        yAxis.setTextSize(16);

        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = 30;

        viewport.bottom = 0;

        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
    }



    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        etSelectedDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.appointment_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {

            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                //
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }

            };
            // Create a AlertDialog Builder.
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            // Set title, icon, can not cancel properties.
            alertDialogBuilder.setTitle("Enter Sugar Level");
            alertDialogBuilder.setIcon(R.drawable.ic_launcher_background);
            alertDialogBuilder.setCancelable(false);

            // Init popup dialog view and it's ui controls.
            initPopupViewControls();

            // Set the inflated layout view object to the AlertDialog builder.
            alertDialogBuilder.setView(popupInputDialogView);

            // Create AlertDialog and show.
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


            etSelectedDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(getActivity(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            // When user click the save user data button in the popup dialog.
            saveUserDataButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Get user data from popup dialog editeext.
                    String glucoseLevel = etGlucoseLevel.getText().toString();
                    String dateselected = etSelectedDate.getText().toString();

                    if(glucoseLevel.isEmpty()){
                        etGlucoseLevel.setError("Enter Glucose level");

                    } else  {

                        Integer num = Integer.parseInt(glucoseLevel);
                        if (num >= 0 && num < 30) {
                            if(dateselected.isEmpty())
                            {
                                etSelectedDate.setError("Date must not be empty");
                            }else {

                                num = Integer.parseInt(glucoseLevel);
                                illnessDB = FirebaseDatabase.getInstance().getReference("Illness/diabetics/" + auth.getUid());
                                String id =illnessDB.push().getKey();

                                Progress p =new Progress();
                                p.setDate(dateselected);
                                p.setNum(num);
                                illnessDB.child(id).setValue(p);

                                alertDialog.cancel();
                            }
                        } else {
                            etGlucoseLevel.setError("Glucose level must be between 0 to 30");

                        }

                    }








                }
            });

            cancelUserDataButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.cancel();
                }
            });

            return true;
        }else  if (id == R.id.itemLogout) {
            logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        auth.signOut();
        if (auth.getCurrentUser() == null)
        {
            Intent intent = new Intent(getActivity(), SignInActivity.class);
            startActivity(intent);
        }
    }

}
