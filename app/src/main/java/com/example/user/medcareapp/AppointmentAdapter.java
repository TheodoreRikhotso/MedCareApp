package com.example.user.medcareapp;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.medcareapp.com.example.user.medcareapp.pojo.Appointment;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {

    private Activity context;
    private List<Appointment> appointments;
    private Activity applicationContext;
    private Appointment appointment;

    public AppointmentAdapter(Activity context, List<Appointment> appointments) {
        this.context = context;
        this.appointments = appointments;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointmet_model, null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Appointment appointment = appointments.get(position);
        holder.tvClinic.setText(appointment.getClinic());
        holder.tvDate.setText(appointment.getDate());
        holder.tvTime.setText(appointment.getTime());






    }

    @Override
    public int getItemCount() {
        return (null != appointments ? appointments.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvClinic, tvDate, tvTime;



        public MyViewHolder(View itemView) {
            super(itemView);
            tvClinic = itemView.findViewById(R.id.tvClinic);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);



        }

    }

}
