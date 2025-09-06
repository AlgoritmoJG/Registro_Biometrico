package com.example.biometric;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DataAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;
    private LayoutInflater inflater;

    public DataAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_user_record, parent, false);
            holder = new ViewHolder();
            holder.tvName = convertView.findViewById(R.id.tvItemName);
            holder.tvCountry = convertView.findViewById(R.id.tvItemCountry);
            holder.tvCity = convertView.findViewById(R.id.tvItemCity);
            holder.tvRegistrationDate = convertView.findViewById(R.id.tvItemRegistrationDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        User user = userList.get(position);
        holder.tvName.setText(user.getName());
        holder.tvCountry.setText(user.getCountry());
        holder.tvCity.setText(user.getCity());
        holder.tvRegistrationDate.setText(user.getRegistrationDate());

        return convertView;
    }

    private static class ViewHolder {
        TextView tvName;
        TextView tvCountry;
        TextView tvCity;
        TextView tvRegistrationDate;
    }
}
