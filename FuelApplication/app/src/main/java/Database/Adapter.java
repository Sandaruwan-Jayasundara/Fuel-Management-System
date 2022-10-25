package Database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fuel.fuelapplication.R;
import java.util.List;
import Models.ModelClass;

/**
 * Adapter class helps to work Recycle view in the Home class
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<ModelClass> userList;
    private ItemClickListner itemClickListner;

    //Assign values to the variables
    public Adapter(List<ModelClass>userList,ItemClickListner itemClickListner) {
        this.userList=userList;
        this.itemClickListner=itemClickListner;
    }

    //Filtered list for search feature
    public void setFilteredList(List<ModelClass> modelList){
        userList = modelList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shed_list,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        //Assign values for the variables
        int resource = userList.get(position).getImageview();
        String name=userList.get(position).getTextview1();
        String msg=userList.get(position).getTextview2();
        String time=userList.get(position).getTextview3();
        String phone=userList.get(position).getPhone();

        holder.setData(resource,name,msg,time,phone);
        holder.itemView.setOnClickListener(view ->{
            itemClickListner.onItemClick(userList.get(position));
        });
    }

    //to get the number of shed count
    @Override
    public int getItemCount() {
        return userList.size();
    }

    public interface ItemClickListner{
        void onItemClick(ModelClass modelClass);
    }

    //view holder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private TextView textView2;
        private TextView textview3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Declare item views and assign the values
            imageView=itemView.findViewById(R.id.imageview);
            textView=itemView.findViewById(R.id.textview);
            textView2=itemView.findViewById(R.id.textview2);
            textview3=itemView.findViewById(R.id.textview3);
        }
        //set data to the variables to display data
        public void setData(int resource, String name, String msg, String time,String line) {
            imageView.setImageResource(resource);
            textView.setText(name);
            textView2.setText(msg);
            textview3.setText("");
        }
    }
}