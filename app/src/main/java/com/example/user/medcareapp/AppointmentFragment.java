package com.example.user.medcareapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.medcareapp.com.example.user.medcareapp.pojo.Appointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AppointmentFragment extends Fragment {


    private FloatingActionButton fabAddApp;
    private Toolbar toolbar;
    private View view;

    private FirebaseAuth auth;
    private DatabaseReference appointmentDB;

    private List<Appointment> appointmentList;
    private LinearLayoutManager layoutManager;
    private RecyclerView rvAppointment;
    private AppointmentAdapter appointmentAdapter;
    public AppointmentFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_appointmet, container, false);
        toolbar = view.findViewById(R.id.toolbar_appoint);
        fabAddApp= view.findViewById(R.id.fabAddApp);

        toolbar.setTitle("Appointment");
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();

        appointmentDB = FirebaseDatabase.getInstance().getReference("Appointment/"+auth.getUid());

        rvAppointment = view.findViewById(R.id.rvAppointment);

        appointmentList = new ArrayList<>();



        appointmentDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                appointmentList.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    rvAppointment = view.findViewById(R.id.rvAppointment);
                    Appointment catalog = catalogSnapshot.getValue(Appointment.class);
                    appointmentList.add(catalog);

                    layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    appointmentAdapter = new AppointmentAdapter(getActivity(), appointmentList);

                    rvAppointment.setLayoutManager(layoutManager);

                    rvAppointment.setAdapter(appointmentAdapter);
                }














                            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        fabAddApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddAppointmentActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


}
