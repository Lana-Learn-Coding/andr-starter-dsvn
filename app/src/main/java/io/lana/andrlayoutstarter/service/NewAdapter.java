package io.lana.andrlayoutstarter.service;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.List;

import io.lana.andrlayoutstarter.FormActivity;
import io.lana.andrlayoutstarter.R;

public class NewAdapter extends ArrayAdapter<New> {
    public NewAdapter(@NonNull Context context, List<New> news) {
        super(context, 0, news);
    }

    public NewAdapter(@NonNull Context context, New[] news) {
        super(context, 0, news);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        New item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_news, parent, false);
        }

        TextView textContent = convertView.findViewById(R.id.content);
        TextView textTitle = convertView.findViewById(R.id.title);
        if (textTitle != null) textTitle.setText(item.getTitle());
        if (textContent != null) textContent.setText(item.getContent());

        convertView.setOnLongClickListener(view -> {
            Context context = getContext();

            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.getMenuInflater().inflate(R.menu.news_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                int id = menuItem.getItemId();
                if (id == R.id.item_view_all) {
                    context.startActivity(new Intent(context, NewActivity.class));
                    return true;
                }

                Toast.makeText(context, menuItem.getTitle() + ": " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            });
            popupMenu.show();
            return true;
        });

        return convertView;
    }
}
