package com.filtatra_f52124072.aplikasi_uts;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    private final List<Court> allCourts = new ArrayList<>();
    private final List<Court> shownCourts = new ArrayList<>();

    private CourtAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        loadCourtData();

        ListView courtListView = findViewById(R.id.courtListView);
        EditText searchInput = findViewById(R.id.searchInput);

        shownCourts.addAll(allCourts);

        adapter = new CourtAdapter();

        courtListView.setAdapter(adapter);

        courtListView.setOnItemClickListener((parent, view, position, id) ->
                showCourtDetail(shownCourts.get(position)));

        searchInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(
                    CharSequence s,
                    int start,
                    int count,
                    int after) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s,
                    int start,
                    int before,
                    int count) {

                filterCourts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void loadCourtData() {

        allCourts.add(new Court(
                "Lapangan Futsal Pipit",
                "Tanamodindi, Mantikulore",
                "4.5",
                "09.00 - 23.30",
                "Hubungi pengelola"
        ));

        allCourts.add(new Court(
                "Belimbing Futsal",
                "Kamonji, Palu Barat",
                "4.2",
                "Perlu dikonfirmasi",
                "Hubungi pengelola"
        ));

        allCourts.add(new Court(
                "Lapangan Futsal Merdeka",
                "Jl. Merdeka, Palu Selatan",
                "4.4",
                "09.00 - 22.00",
                "Hubungi pengelola"
        ));

        allCourts.add(new Court(
                "Yardan Futsal Gelatik",
                "Jl. Gelatik, Palu Selatan",
                "4.3",
                "Perlu dikonfirmasi",
                "Hubungi pengelola"
        ));

        allCourts.add(new Court(
                "Novega Futsal Court",
                "Tanamodindi, Mantikulore",
                "4.3",
                "Perlu dikonfirmasi",
                "Hubungi pengelola"
        ));

        allCourts.add(new Court(
                "Lapangan Futsal Banteng FC",
                "Palu Selatan",
                "3.8",
                "Perlu dikonfirmasi",
                "Hubungi pengelola"
        ));

        allCourts.add(new Court(
                "Futsal Hangtuah",
                "Talise, Palu",
                "3.4",
                "Perlu dikonfirmasi",
                "Hubungi pengelola"
        ));

        allCourts.add(new Court(
                "Lapangan Futsal Tanaris",
                "Jl. Juanda, Palu Timur",
                "3.9",
                "08.00 - 23.00",
                "Hubungi pengelola"
        ));
    }

    private void filterCourts(String keyword) {

        shownCourts.clear();

        String query =
                keyword.toLowerCase(Locale.ROOT).trim();

        for (Court court : allCourts) {

            if (court.name
                    .toLowerCase(Locale.ROOT)
                    .contains(query)
                    ||
                    court.location
                            .toLowerCase(Locale.ROOT)
                            .contains(query)) {

                shownCourts.add(court);
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void showCourtDetail(Court court) {

        String detail =
                "📍 Lokasi: " + court.location
                        + "\n⭐ Rating: " + court.rating + "/5"
                        + "\n🕒 Jam buka: " + court.hours
                        + "\n💰 Harga: " + court.price
                        + "\n\nHubungi pengelola untuk memastikan "
                        + "informasi terbaru.";

        new AlertDialog.Builder(this)
                .setTitle(court.name)
                .setMessage(detail)
                .setPositiveButton("Tutup", null)
                .show();
    }

    private static class Court {

        String name;
        String location;
        String rating;
        String hours;
        String price;

        Court(
                String name,
                String location,
                String rating,
                String hours,
                String price) {

            this.name = name;
            this.location = location;
            this.rating = rating;
            this.hours = hours;
            this.price = price;
        }
    }

    private class CourtAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return shownCourts.size();
        }

        @Override
        public Object getItem(int position) {
            return shownCourts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(
                int position,
                View convertView,
                ViewGroup parent) {

            View row = convertView;

            if (row == null) {

                row = LayoutInflater
                        .from(MainActivity.this)
                        .inflate(
                                R.layout.item_court,
                                parent,
                                false
                        );
            }

            Court court =
                    shownCourts.get(position);

            TextView name =
                    row.findViewById(R.id.courtName);

            TextView location =
                    row.findViewById(R.id.courtLocation);

            TextView info =
                    row.findViewById(R.id.courtInfo);

            name.setText(court.name);

            location.setText(
                    "📍 " + court.location
            );

            info.setText(
                    "⭐ " + court.rating
                            + " • "
                            + court.price
            );

            return row;
        }
    }
}