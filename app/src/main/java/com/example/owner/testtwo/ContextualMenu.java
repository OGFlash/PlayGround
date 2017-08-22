package com.example.owner.testtwo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.jar.Manifest;

/**
 * Created by Owner on 7/20/2017.
 */

public class ContextualMenu extends AppCompatActivity{
    public Button button;
    ActionMode actionMode;
    android.support.design.widget.CoordinatorLayout rootLayout;
    public FloatingActionButton floatingActionButton;
    private static final int REQUEST_ENABLED_BT = 1;
    private static final int REQUEST_TAKE_PHOTO = 2;
    private static final int REQUEST_WRITE = 3;
    private static final int REQUEST_READ = 4;
    private Button buttonOn;
    private Button buttonOff;
    private Button listBtn;
    private Button findBtn;
    private TextView textView;
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> pairDevices;
    private ListView myListView;
    private ArrayAdapter<String> BTArrayAdapter;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private static final String ALLOW_KEY = "ALLOWED";
    private static final String CAMERA_PREF = "camera_pref";
    private ImageView ivPreview;
    private String mCurrentPhotoPath;




    @Override
    protected void onCreate(Bundle saveInstantstate){
        super.onCreate(saveInstantstate);
        setContentView(R.layout.contextual_menu);
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        rootLayout = (android.support.design.widget.CoordinatorLayout) findViewById(R.id.linearLayoutContext);
        button = (Button) findViewById(R.id.button);
        buttonOn = (Button)findViewById(R.id.buttonOn);
        buttonOff = (Button)findViewById(R.id.buttonOff);
        listBtn = (Button)findViewById(R.id.listButton);
        findBtn = (Button)findViewById(R.id.findButton);
        ivPreview = (ImageView) findViewById(R.id.fromCamera);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButtonOne);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(ContextualMenu.this, "Floater Clicked", Toast.LENGTH_SHORT).show();
                CustomDialog customDialog = new CustomDialog(ContextualMenu.this, R.style.custom_dialog_theme);
                customDialog.show();

//                Intent intent = new Intent(getApplicationContext(), GameAreaActivity.class);
//                startActivity(intent);
            }
        });
        if(mToolBar != null) {
            getSupportActionBar().setTitle("Action Bar ToolBar");
            mToolBar.setSubtitle("By Andrew Michael King");
        }
        /********************** Camera Permissions ********************/
        if(checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            if(getFromPref(this, ALLOW_KEY)){
                showSettingsAlert();
            }
            else if(checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.CAMERA)){
                    showAlert();
                }
                else{
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }
        }
        else if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                showReadAlert();
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ);
            }
        }
        else if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                showWriteAlert();
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE);
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionMode = ContextualMenu.this.startSupportActionMode(new ContextualCallBack());

            }
        });


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter == null){
            Toast.makeText(getApplicationContext(), "Your device does not support Bluetooth", Toast.LENGTH_LONG).show();
        } else {
            textView = (TextView)findViewById(R.id.statusText);

            buttonOn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    on(view);
                }
            });
            buttonOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    off(view);
                }
            });
            listBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list(view);
                }
            });
            findBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    find(view);
                }
            });
            myListView = (ListView) findViewById(R.id.listViewBT);
            BTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            myListView.setAdapter(BTArrayAdapter);

            if(myListView!= null){
                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //TODO SOMETHING HERE WHY NOT. MORE INFORMATION ABOUT THE DEVICES?
                    }
                });
            }
        }
    }

    private void showAlert() {
        final AlertDialog alertDialog = new AlertDialog.Builder(ContextualMenu.this).create();
        alertDialog.setTitle("ALERT!");
        alertDialog.setMessage("App Must Access Camera.");
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "DON'T ALLOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
                finish();
            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "ALLOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCompat.requestPermissions(ContextualMenu.this, new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                finish();
            }
        });
        alertDialog.show();
    }

    private void showReadAlert() {
        final AlertDialog alertDialog = new AlertDialog.Builder(ContextualMenu.this).create();
        alertDialog.setTitle("ALERT!");
        alertDialog.setMessage("App Must Access to Write");
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "DON'T ALLOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
                finish();
            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "ALLOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCompat.requestPermissions(ContextualMenu.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE);
                finish();
            }
        });
        alertDialog.show();
    }

    private void showWriteAlert() {
        final AlertDialog alertDialog = new AlertDialog.Builder(ContextualMenu.this).create();
        alertDialog.setTitle("ALERT!");
        alertDialog.setMessage("App Must Access Read.");
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "DON'T ALLOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
                finish();
            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "ALLOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCompat.requestPermissions(ContextualMenu.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ);
                finish();
            }
        });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        final AlertDialog dialog = new AlertDialog.Builder(ContextualMenu.this).create();
        dialog.setTitle("ALERT!");
        dialog.setMessage("App Must Access Camera.");
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "DON'T ALLOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
//                finish();
            }
        });

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "ALLOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                startInstalledAppDetailsActivity(ContextualMenu.this);
            }
        });
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++){
                    String permission = permissions[i];

                    if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                       boolean showReason = shouldShowRequestPermissionRationale(permission);

                        if(showReason){
                            showAlert();
                        }
                        else if(!showReason){
//                            saveToPreferences(ContextualMenu.this, ALLOW_KEY, true);
                        }
                    }
                }
            }
            break;
            case REQUEST_READ:
            {
                for (int i = 0, len = permissions.length; i < len; i++){
                    String permission = permissions[i];

                    if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                        boolean showReason = shouldShowRequestPermissionRationale(permission);

                        if(showReason){
                            showReadAlert();
                        }
                        else if(!showReason){
                            try{
                                dispatchTakePictureIntent();
                            }catch(IOException e){
                            }
                        }
                    }
                }
            }
                break;
            case REQUEST_WRITE:
            {
                for (int i = 0, len = permissions.length; i < len; i++){
                    String permission = permissions[i];

                    if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                        boolean showReason = shouldShowRequestPermissionRationale(permission);

                        if(showReason){
                            showWriteAlert();
                        }
                        else if(!showReason){
                            try{
                                dispatchTakePictureIntent();
                            }catch(IOException e){
                            }
                        }
                    }
                }
            }
                break;
        }
    }

    private void dispatchTakePictureIntent()throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!= null){
            File photoFile = null;
            try{
                photoFile = createImageFile();
            }catch (IOException e){
                return;
            }
            if(photoFile != null){
//                Uri photoUri = Uri.fromFile(createImageFile());
                Uri photoUri = FileProvider.getUriForFile(
                        ContextualMenu.this,
                        BuildConfig.APPLICATION_ID + ".provider" ,
                        createImageFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void saveToPreferences(Context contextualMenu, String allowKey, boolean b) {
        SharedPreferences myPrefs = contextualMenu.getSharedPreferences(CAMERA_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = myPrefs.edit();
        prefEditor.putBoolean(allowKey, b);
        prefEditor.commit();
    }

    private static Boolean getFromPref(Context context, String key){
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void startInstalledAppDetailsActivity(final Activity contextualMenu) {
        if (contextualMenu != null){
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package: " + contextualMenu.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        contextualMenu.startActivity(i);
    }

    public void openCamera(){
        Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intentCamera, 0);
        try{
            dispatchTakePictureIntent();
        }catch (IOException e){
        }
    }

    public void on(View view){
        if(!bluetoothAdapter.isEnabled()){
            Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnIntent, REQUEST_ENABLED_BT);
            Toast.makeText(getApplicationContext(), "BlueTooth Turned on", Toast.LENGTH_LONG);
        }else{
            Toast.makeText(getApplicationContext(), "BlueTooth is already on", Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_ENABLED_BT){
            if(bluetoothAdapter.isEnabled()){
                textView.setText("Status: Enabled");
            }else{
                textView.setText("Status: Disabled");
            }
        }

        else if(requestCode == REQUEST_TAKE_PHOTO) {
            Uri imageUri = Uri.parse(mCurrentPhotoPath);
            File file = new File(imageUri.getPath());
            try {
                InputStream inputStream = new FileInputStream(file);
                ivPreview.setImageBitmap(BitmapFactory.decodeStream(inputStream));
            } catch (FileNotFoundException e) {
                return;
            }

            MediaScannerConnection.scanFile(ContextualMenu.this, new String[]{imageUri.getPath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String s, Uri uri) {
                    Toast.makeText(getApplicationContext(), "OnScanComplete", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void list(View view) {
        pairDevices = bluetoothAdapter.getBondedDevices();

        for (BluetoothDevice device : pairDevices) {
            BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
        }

        Toast.makeText(getApplicationContext(), "Show Paired Devices", Toast.LENGTH_SHORT).show();
    }

        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            }
        };

        public void find(View view){
            if(bluetoothAdapter.isDiscovering()){
                bluetoothAdapter.cancelDiscovery();
            }else {
                BTArrayAdapter.clear();
                bluetoothAdapter.startDiscovery();

                registerReceiver(broadcastReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            }
        }

        public void off(View view){
            bluetoothAdapter.disable();
            textView.setText("Status: Disconnected");

            Toast.makeText(getApplicationContext(), "Bluetooth turned off", Toast.LENGTH_LONG).show();
        }

    @Override
    protected void onDestroy(){
        super.onDestroy();
//        if(broadcastReceiver != null) {
//            unregisterReceiver(broadcastReceiver);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg = "";
        switch (item.getItemId()){
            case R.id.save_menu:
                msg = "Save!";
                break;
            case R.id.settings_menu:
                msg = "Settings!";
                break;
            case R.id.message_menu:
                msg = "message!";
                break;
            case R.id.camera_menu:
                openCamera();
                msg = "camera!";
                break;
            case R.id.share_menu:
                msg = "share!";
                break;
            case R.id.nav_share:
                msg = "nav share!";
                break;
            case R.id.nav_send:
                msg = "nav send!";
                break;
            case R.id.communication_menu:
                msg = "Communication";
                break;
            default:
                msg = "Not Found";
                break;
        }
        Toast.makeText(this, msg + "Clicked !", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }


    private class ContextualCallBack implements ActionMode.Callback{

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_menu_menu, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("My Action Mode");
            mode.setSubtitle("The Boss");
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_snack:
                    showActionSnack();
                    break;
                case R.id.custom_snack:
                    break;
                case R.id.simple_snack:
                    showSimpleSnack();
                    break;
                case R.id.save_contextual:
                    break;
            }

            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }

        public void showSimpleSnack(){
            Snackbar.make(rootLayout, "Simple SnackBar Example", Snackbar.LENGTH_LONG).show();
        }

        public void showActionSnack(){
            Snackbar snackbar;
            snackbar = Snackbar.make(rootLayout, "Action SnackBar Example", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(rootLayout, "Action Confirmed Bro", Snackbar.LENGTH_LONG).show();
                }
            });
            snackbar.show();
        }

        public void showCustomSnack(){
            //TODO LEARN

        }
    }
}
