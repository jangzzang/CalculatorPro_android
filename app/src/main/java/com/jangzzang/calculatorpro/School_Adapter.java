package com.jangzzang.calculatorpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class School_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Double Total1;
    private Double Total2;
    private Double Total3;

    private final List<School_item> mDataList;
    Context context;

    public School_Adapter(List<School_item> mDataList, Context context) {
        this.mDataList = mDataList;
        this.context = context;
    }

    public interface OnPlusClickListener {
        void onPlusClick(View v, int position);
    }
    private OnPlusClickListener mListener = null;
    public void setOnPlusClickListener(OnPlusClickListener listener){
        this.mListener = listener;
    }

    public interface OnOtherClickListener {
        void onOtherClick(View v, int position);
    }
    private OnOtherClickListener mListener2 = null;
    public void setOnOtherClickListener(OnOtherClickListener listener){
        this.mListener2 = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType == 1){
            view = inflater.inflate(R.layout.school_item1, parent, false);
            return new School_Adapter.FirstViewHolder(view);
        }
        else if(viewType == 2){
            view = inflater.inflate(R.layout.school_item2, parent, false);
            return new School_Adapter.SecondViewHolder(view);
        }
        else{
            view = inflater.inflate(R.layout.school_item3, parent, false);
            return new School_Adapter.ThirdViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof FirstViewHolder){
            ((FirstViewHolder)holder).e1.setText(mDataList.get(position).getName());
            ((FirstViewHolder)holder).e2.setText(mDataList.get(position).getScore1());
            ((FirstViewHolder)holder).e3.setText(mDataList.get(position).getScore2());
        }




    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getViewtype();
    }




    private class FirstViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView e1;
        TextView e2;
        TextView e3;

        public FirstViewHolder(@NonNull View itemView) {
            super(itemView);

            e1 = itemView.findViewById(R.id.e1);
            e2 = itemView.findViewById(R.id.e2);
            e3 = itemView.findViewById(R.id.e3);

            imageView = itemView.findViewById(R.id.cancel);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener2 != null){
                            mListener2.onOtherClick(v, pos);
                        }
                    }
                }
            });
        }
    }

    private class SecondViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public SecondViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.plus);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onPlusClick(v,pos);
                        }
                    }
                }
            });
        }
    }

    private class ThirdViewHolder extends RecyclerView.ViewHolder{

        TextView t1;
        TextView t2;
        TextView t3;

        public ThirdViewHolder(@NonNull View itemView) {
            super(itemView);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            int pos = getAdapterPosition();

        }
    }
}
