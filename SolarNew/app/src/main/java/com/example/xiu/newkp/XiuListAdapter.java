package com.example.xiu.newkp;

        import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

public class XiuListAdapter extends ArrayAdapter {

    // Tao một Layout riêng cho mình với Tittle và data trong ListView


    public XiuListAdapter(Activity context, String[] nameArrayParam, String[] infoArrayParam){

        super(context,R.layout.listview_row , nameArrayParam);
        this.context=context;
        this.nameArray = nameArrayParam;
        this.infoArray = infoArrayParam;

    }
    private final Activity context;

    //to store the list of countries
    private final String[] nameArray;

    //to store the list of countries
    private final String[] infoArray;


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.tittle);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.info);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        infoTextField.setText(infoArray[position]);

        return rowView;

    };


}


