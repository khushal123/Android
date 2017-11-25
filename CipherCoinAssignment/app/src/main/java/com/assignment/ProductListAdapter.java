package com.assignment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by khushal on 25/11/17.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    Context context;
    JSONArray productList;

    public ProductListAdapter(Context context, JSONArray productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProductListAdapter.ViewHolder holder, int position) {
        try {
            JSONObject currentObject = productList.getJSONObject(position);
            JSONObject info = currentObject.getJSONObject("info");
            JSONObject stats = currentObject.getJSONObject("stats");
            JSONObject total = stats.getJSONObject("total");
            JSONObject monthly_average = stats.getJSONObject("monthly_average");
            JSONObject pricing = currentObject.getJSONObject("pricing");
            holder.title.setText(info.getString("title"));
            holder.price.setText(pricing.getString("amount"));
            StringBuilder builder = new StringBuilder();
            builder.append("Total Sales: "+total.getString("sales"));
            builder.append("\n");
            builder.append("Total earnings: "+total.getString("earnings"));
            builder.append("\n");
            builder.append("Monthly Average Sales: "+monthly_average.getString("sales"));
            builder.append("\n");
            builder.append("Monthly Average Earning: "+monthly_average.getString("earnings"));
            holder.sale.setText(builder.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return productList.length();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, price, sale;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            sale = (TextView) itemView.findViewById(R.id.sale);
        }
    }
}
