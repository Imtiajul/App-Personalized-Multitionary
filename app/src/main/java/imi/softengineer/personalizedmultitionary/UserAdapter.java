package imi.softengineer.personalizedmultitionary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ImtiajulIslam on 3/13/2018.
 */

class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    List<User> users;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        holder.word.setText(users.get(position).getWord());
        holder.description.setText(users.get(position).getDescription());
        holder.link.setText(users.get(position).getLink());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView word;
        public TextView description;
        public TextView link;

        public ViewHolder(View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.txtViewword);
            description = itemView.findViewById(R.id.txtViewdescription);
            link = itemView.findViewById(R.id.txtViewlink);
        }
    }
}
