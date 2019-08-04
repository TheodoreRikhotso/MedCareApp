package com.example.user.medcareapp;

import android.app.DatePickerDialog;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.medcareapp.com.example.user.medcareapp.pojo.Appointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddAppointmentActivity extends AppCompatActivity {
private EditText etAppDate;
private Spinner spinnerClinc;
private RadioGroup rgTime;
private Button btn_submit;
private String[] clinics;
private String[] contacts;
private Appointment app;
private Calendar myCalendar;
private View popupInputDialogView = null;
private DatabaseReference appointmentDB;
private FirebaseAuth auth;
private RadioButton radioButton;


@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);


        spinnerClinc = findViewById(R.id.spinnerClinc);
        rgTime = findViewById(R.id.rgTime);
        btn_submit = findViewById(R.id.btn_submit);

        app = new Appointment();
        clinics =new String[]{"Midrand Municipality Clinic","Bophelong Clinic","Halfway House Clinic","Mpumelelo Clinic","Rabie Ridge Clinic","Thuthukani  Clinic"};
        contacts=new String[]{"+27 21 441 9700","011 464 795","011 805 3112","011 261 0910","011 310 197","011 261 0658"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, clinics);

        myCalendar = Calendar.getInstance();

        auth = FirebaseAuth.getInstance();

        spinnerClinc.setAdapter(adapter);

        etAppDate = findViewById(R.id.etAppDate);





        addAppointment();

        }


public void addAppointment() {



        spinnerClinc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
@Override
public void onItemSelected(AdapterView<?> parent, View view,
                           int position, long id) {
        app.setClinic(clinics[position]);
        app.setContact(contacts[position]);
        }

@Override
public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
        }
        });


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





        etAppDate.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        new DatePickerDialog(AddAppointmentActivity.this, date, myCalendar
        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

        Toast.makeText(getApplicationContext(), "enter", Toast.LENGTH_SHORT).show();
        final String dateselected = etAppDate.getText().toString();
//            if(dateselected.isEmpty())
//            {
//                etAppDate.setError("Date must not be empty");
//            }else {

        int selectedId = rgTime.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = findViewById(selectedId);

        Toast.makeText(getApplicationContext(),
                radioButton.getText(), Toast.LENGTH_SHORT).show();

        appointmentDB = FirebaseDatabase.getInstance().getReference("Appointment/" + auth.getUid());
        String id = appointmentDB.push().getKey();

        app.setId(id);
        app.setDate(dateselected);
        app.setTime("" + radioButton.getText());
        appointmentDB.child(id).setValue(app);
        Fragment argumentFragment = new AppointmentFragment();//Get Fragment Instance
//        Bundle data = new Bundle();//Use bundle to pass data
//        data.putString("data", "This is Argument Fragment");//put string, int, etc in bundle with a key value
//        argumentFragment.setArguments(data);//Finally set argument bundle to fragment

        //fragmentManager.beginTransaction().replace(R.id.content, argumentFragment).commit();

}



        });

        }
private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        etAppDate.setText(sdf.format(myCalendar.getTime()));
        }

        }

