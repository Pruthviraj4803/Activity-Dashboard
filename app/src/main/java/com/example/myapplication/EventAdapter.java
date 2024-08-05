package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends ListAdapter<Event, EventAdapter.EventViewHolder> {

    private List<Event> eventList = new ArrayList<>();

    private EventViewModel eventViewModel;
    protected EventAdapter(EventViewModel eventViewModel) {
        super(DIFF_CALLBACK);
        this.eventViewModel=eventViewModel;
    }



    private static final DiffUtil.ItemCallback<Event> DIFF_CALLBACK = new DiffUtil.ItemCallback<Event>() {
        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getTime().equals(newItem.getTime()) &&
                    oldItem.getDate().equals(newItem.getDate()) &&
                    oldItem.getRepeat().equals(newItem.getRepeat()) &&
                    oldItem.getList().equals(newItem.getList());
        }
    };

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void setEvents(List<Event> events) {
        this.eventList = events;
        notifyDataSetChanged();
    }

    public void deleteEvent(int position) {
        eventViewModel.deleteEvent(eventList.get(position));
        notifyDataSetChanged();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        private CheckBox Name;
        private TextView Time;
        private TextView Date;
        private TextView Repeat;
        private TextView List;
        private ImageView deleteButton;

        public EventViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.checkBox);
            Time = itemView.findViewById(R.id.time);
            Date = itemView.findViewById(R.id.date);
            Repeat = itemView.findViewById(R.id.repeat);
            List = itemView.findViewById(R.id.list);
            deleteButton = itemView.findViewById(R.id.delete);
            deleteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    deleteEvent(position);
                }
            });
        }

        public void bind(Event event) {
            Name.setText(event.getName());
            Time.setText(event.getTime());
            Date.setText(event.getDate());
            Repeat.setText(event.getRepeat());
            List.setText(event.getList());
        }
    }
}
