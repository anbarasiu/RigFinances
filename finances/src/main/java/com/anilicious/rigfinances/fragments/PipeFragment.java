package com.anilicious.rigfinances.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.anilicious.rigfinances.activities.VouchersActivity;
import com.anilicious.rigfinances.beans.Maintenance;
import com.anilicious.rigfinances.beans.Pipe;
import com.anilicious.rigfinances.database.DBAdapter;
import com.anilicious.rigfinances.finances.R;

/**
 * Created by ANBARASI on 11/11/14.
 */
public class PipeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vouchers_pipe, null);

        // UI Object References
        Button btnSubmit = (Button)view.findViewById(R.id.btn_submit);
        final RadioGroup rgWorkType = (RadioGroup)view.findViewById(R.id.pipe_workType);
        final RadioGroup rgPipeType = (RadioGroup)view.findViewById(R.id.pipe_pipeType);
        final EditText etPipeLength = (EditText)view.findViewById(R.id.editText2);
        final EditText etTotalAmount = (EditText)view.findViewById(R.id.editText3);
        final EditText etRemarks = (EditText)view.findViewById(R.id.editText4);
        final EditText etSpentBy = (EditText)view.findViewById(R.id.editText5);

        // On Form Submission
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                float pipeLength = Float.parseFloat(etPipeLength.getText().toString());
                double totalAmount = Double.parseDouble(etTotalAmount.getText().toString());
                String remarks = etRemarks.getText().toString();
                String spentBy = etSpentBy.getText().toString();
                RadioButton rbWorkType = (RadioButton)rgWorkType.findViewById(rgWorkType.getCheckedRadioButtonId());
                String workType = rbWorkType.getText().toString();
                RadioButton rbPipeType = (RadioButton)rgPipeType.findViewById(rgPipeType.getCheckedRadioButtonId());
                String pipeType = rbPipeType.getText().toString();

                DebitFragment parent = (DebitFragment)getParentFragment();

                Pipe pipe = new Pipe();
                pipe.setLength(pipeLength);
                pipe.setAmount(totalAmount);
                pipe.setRemarks(remarks);
                pipe.setSpentBy(spentBy);
                pipe.setWorkType(workType);
                pipe.setType(pipeType);
                pipe.setDate(parent.getEntryDate());

                // Insert to DB
                DBAdapter dbAdapter = DBAdapter.getInstance(getActivity());
                dbAdapter.insertPipe(pipe);

                // Clear the Form
                ((VouchersActivity)getActivity()).clearForm();
            }
        });

        return view;
    }
}
