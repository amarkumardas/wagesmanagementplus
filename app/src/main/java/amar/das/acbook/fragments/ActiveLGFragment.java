package amar.das.acbook.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import amar.das.acbook.adapters.MestreLaberGAdapter;
import amar.das.acbook.model.MestreLaberGModel;
import amar.das.acbook.PersonRecordDatabase;
import amar.das.acbook.R;
import amar.das.acbook.databinding.FragmentActiveLGBinding;


public class ActiveLGFragment extends Fragment {

    private FragmentActiveLGBinding binding;
    ArrayList<MestreLaberGModel> lGArrayList;
    RecyclerView lGRecyclerView;
    MestreLaberGAdapter madapter;
    PersonRecordDatabase db;
    int amountad=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding=FragmentActiveLGBinding.inflate(inflater,container,false);
         View root=binding.getRoot();
         db=new PersonRecordDatabase(getContext());//this should be first statement to load data from db
        //ids
        lGRecyclerView=root.findViewById(R.id.recycle_active_l_g);

        //mestre
        Cursor cursorGL=db.getData("SELECT IMAGE,ID,NAME FROM "+db.TABLE_NAME1 +" WHERE TYPE='L' OR TYPE='G' AND ACTIVE='1' LIMIT 150");//getting only mestre image from database
        lGArrayList =new ArrayList<>();
        String id,name;
        while(cursorGL.moveToNext()){
            MestreLaberGModel data=new MestreLaberGModel();
            byte[] image=cursorGL.getBlob(0);
            id=cursorGL.getString(1);
            name=cursorGL.getString(2);
            data.setName(name);
            data.setPerson_img(image);
            data.setId(id);
            data.setAdvanceAmount(""+amountad++);
            lGArrayList.add(data);//adding data to mestrearraylist
        }
        cursorGL.close();//closing cursor after finish
        madapter=new MestreLaberGAdapter(getContext(), lGArrayList);
        //activeMestreCount.setText(""+madapter.getItemCount());
        lGRecyclerView.setAdapter(madapter);
        lGRecyclerView.setHasFixedSize(true);
        lGRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));//spancount is number of rows
        db.close();//closing database to prevent dataleak

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}