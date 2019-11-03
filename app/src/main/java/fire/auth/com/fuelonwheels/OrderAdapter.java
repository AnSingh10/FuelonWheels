package fire.auth.com.fuelonwheels;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<Orders> mList;


    public OrderAdapter(){}

    public OrderAdapter(List<Orders> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_order_item, viewGroup, false);

        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.type.setText(mList.get(i).getFuel_type());
        viewHolder.location.setText(mList.get(i).getAddress());
        viewHolder.qty.setText(mList.get(i).getCapacity());
        viewHolder.date.setText(mList.get(i).getDnt());
        viewHolder.price.setText(mList.get(i).getPrice());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView type, location, date, qty, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.singleOrderType);
            location = itemView.findViewById(R.id.singleOrderlocation);
            date = itemView.findViewById(R.id.singleOrderDate);
            qty = itemView.findViewById(R.id.singleOrderQty);
            price = itemView.findViewById(R.id.singleOrderPrice);
        }
    }
}
