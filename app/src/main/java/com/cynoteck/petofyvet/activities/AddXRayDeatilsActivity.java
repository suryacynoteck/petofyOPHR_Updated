package com.cynoteck.petofyvet.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.addTestXRayParams.AddTestXRayParams;
import com.cynoteck.petofyvet.params.addTestXRayParams.AddTestXRayRequest;
import com.cynoteck.petofyvet.response.addLabWorkResponse.AddLabWorkResponse;
import com.cynoteck.petofyvet.response.addPet.imageUpload.ImageResponse;
import com.cynoteck.petofyvet.response.addTestAndXRayResponse.AddTestXRayResponse;
import com.cynoteck.petofyvet.response.hospitalTypeListResponse.HospitalAddmissionTypeResp;
import com.cynoteck.petofyvet.response.testResponse.XrayTestResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AddXRayDeatilsActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {
    TextView peto_edit_reg_number_dialog,calenderTextViewtestdate,folow_up_dt_view,xray_test_upload_documents,xray_peto_edit_reg_number_dialog;
    AppCompatSpinner nature_of_visit_spinner;
    EditText description_ET;
    Button save_BT;
    ImageView xray_document,x_ray_back_arrow_IV;

    Methods methods;

    ArrayList<String> testTypeList;

    HashMap<String,String> testTypehas=new HashMap<>();

    String testTypeId="",pet_id="",pet_name="",pet_owner_name="",pet_sex="",pet_unique_id="",strDocumentUrl="";

    DatePickerDialog picker;

    private static final String IMAGE_DIRECTORY = "/Picture";
    private int GALLERY = 1, CAMERA = 2;
    File file = null;
    Dialog dialog;
    Bitmap bitmap, thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_x_ray_deatils);
        init();
        requestMultiplePermissions();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        xray_peto_edit_reg_number_dialog=findViewById(R.id.xray_peto_edit_reg_number_dialog);
        peto_edit_reg_number_dialog=findViewById(R.id.xray_peto_edit_reg_number_dialog);
        calenderTextViewtestdate=findViewById(R.id.calenderTextViewtestdate);
        folow_up_dt_view=findViewById(R.id.folow_up_dt_view);
        nature_of_visit_spinner=findViewById(R.id.nature_of_visit_spinner);
        description_ET=findViewById(R.id.description_ET);
        xray_test_upload_documents=findViewById(R.id.xray_test_upload_documents);
        xray_document=findViewById(R.id.xray_document);
        save_BT=findViewById(R.id.save_BT);
        x_ray_back_arrow_IV=findViewById(R.id.x_ray_back_arrow_IV);

        save_BT.setOnClickListener(this);
        calenderTextViewtestdate.setOnClickListener(this);
        folow_up_dt_view.setOnClickListener(this);
        xray_test_upload_documents.setOnClickListener(this);
        x_ray_back_arrow_IV.setOnClickListener(this);

        if (extras != null) {
            pet_id = extras.getString("pet_id");
            pet_name = extras.getString("pet_name");
            pet_owner_name = extras.getString("pet_owner_name");
            pet_sex = extras.getString("pet_sex");
            pet_unique_id = extras.getString("pet_unique_id");
            xray_peto_edit_reg_number_dialog.setText(pet_unique_id);
        }




        methods=new Methods(this);

        if (methods.isInternetOn()){
            getTestTypeList();
        }else {
            methods.DialogInternet();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.folow_up_dt_view:
                final Calendar cldrFolwUpDt = Calendar.getInstance();
                int dayFolwUpDt = cldrFolwUpDt.get(Calendar.DAY_OF_MONTH);
                int monthFolwUpDt = cldrFolwUpDt.get(Calendar.MONTH);
                int yearFolwUpDt = cldrFolwUpDt.get(Calendar.YEAR);
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                folow_up_dt_view.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, dayFolwUpDt, monthFolwUpDt, yearFolwUpDt);
                picker.show();
                break;
            case R.id.calenderTextViewtestdate:
                final Calendar cldrDeschrgDt = Calendar.getInstance();
                int dayDeschrgDt = cldrDeschrgDt.get(Calendar.DAY_OF_MONTH);
                int monthDeschrgDt = cldrDeschrgDt.get(Calendar.MONTH);
                int yearDeschrgDt = cldrDeschrgDt.get(Calendar.YEAR);
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderTextViewtestdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, dayDeschrgDt, monthDeschrgDt, yearDeschrgDt);
                picker.show();
                break;
            case R.id.xray_test_upload_documents:
                showPictureDialog();
                break;
            case R.id.save_BT:
                String strDescription=description_ET.getText().toString();
                String strPetUniqueId=peto_edit_reg_number_dialog.getText().toString();
                String strDt=calenderTextViewtestdate.getText().toString();
                String strFolowUpDt=folow_up_dt_view.getText().toString();

                if(strDescription.isEmpty()){
                    description_ET.setError("Enter Results");
                    calenderTextViewtestdate.setError(null);
                }
                else if(strDt.isEmpty())
                {
                    description_ET.setError(null);
                    calenderTextViewtestdate.setError("Enter test Date");
                }
                else if(testTypeId.isEmpty())
                {
                    description_ET.setError(null);
                    calenderTextViewtestdate.setError(null);
                    Toast.makeText(this, "Select Test type ", Toast.LENGTH_SHORT).show();
                }
                else{
                    description_ET.setError(null);
                    calenderTextViewtestdate.setError(null);
                    AddTestXRayParams addTestXRayParams=new AddTestXRayParams();
                    addTestXRayParams.setPetId(pet_id);
                    addTestXRayParams.setTypeOfTestId(testTypeId);
                    addTestXRayParams.setDocuments(strDocumentUrl);
                    addTestXRayParams.setFollowUpDate(strFolowUpDt);
                    addTestXRayParams.setDateTested(strDt);
                    addTestXRayParams.setResults(strDescription);
                    AddTestXRayRequest addTestXRayRequest=new AddTestXRayRequest();
                    addTestXRayRequest.setData(addTestXRayParams);
                    if(methods.isInternetOn())
                    {
                        addPetXray(addTestXRayRequest);
                    }
                    else
                    {
                        methods.DialogInternet();
                    }
                }
                break;
            case R.id.x_ray_back_arrow_IV:
                onBackPressed();
                break;

        }

    }

    private void showPictureDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_layout);

        TextView select_camera = (TextView) dialog.findViewById(R.id.select_camera);
        TextView select_gallery = (TextView) dialog.findViewById(R.id.select_gallery);
        TextView cancel_dialog = (TextView) dialog.findViewById(R.id.cancel_dialog);

        select_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhotoFromCamera();
            }
        });

        select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhotoFromGallary();
            }
        });

        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void choosePhotoFromGallary() {


        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);

    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialog.dismiss();
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {

                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                    xray_document.setImageBitmap(bitmap);
                    saveImage(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AddXRayDeatilsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if (requestCode == CAMERA) {

            if (data.getData() == null)
            {
                thumbnail = (Bitmap) data.getExtras().get("data");
                Log.e("jghl",""+thumbnail);
                xray_document.setImageBitmap(thumbnail);
                saveImage(thumbnail);
                Toast.makeText(AddXRayDeatilsActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            }

            else{
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(AddXRayDeatilsActivity.this.getContentResolver(), data.getData());
                    xray_document.setImageBitmap(bitmap);
                    saveImage(bitmap);
                    Toast.makeText(AddXRayDeatilsActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return;
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            file = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".png");
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{file.getPath()},
                    new String[]{"image/png"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + file.getAbsolutePath());
            UploadImages(file);
            return file.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void UploadImages(File absolutePath) {
        MultipartBody.Part userDpFilePart = null;
        if (absolutePath != null) {
            RequestBody userDpFile = RequestBody.create(MediaType.parse("image/*"), absolutePath);
            userDpFilePart = MultipartBody.Part.createFormData("file", absolutePath.getName(), userDpFile);
        }

        ApiService<ImageResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().uploadImages(Config.token,userDpFilePart), "UploadDocument");
        Log.e("DATALOG","check1=> "+service);

    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(AddXRayDeatilsActivity.this, "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(AddXRayDeatilsActivity.this, "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void addPetXray(AddTestXRayRequest addTestXRayRequest) {
        ApiService<AddTestXRayResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().addTestXRay(Config.token,addTestXRayRequest), "AddTestXRay");
        Log.d("addPetLabParams",""+addTestXRayRequest);
    }

    private void getTestTypeList() {
        ApiService<XrayTestResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getTestTypeList(Config.token), "GetTestTypeList");
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "GetTestTypeList":
                try {
                    XrayTestResponse xrayTestResponse = (XrayTestResponse) arg0.body();
                    Log.d("GetTestTypeList", xrayTestResponse.toString());
                    int responseCode = Integer.parseInt(xrayTestResponse.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        testTypeList = new ArrayList<>();
                        testTypeList.add("Select Test Type");
                        for (int i = 0; i < xrayTestResponse.getData().size(); i++) {
                            testTypeList.add(xrayTestResponse.getData().get(i).getTestType());
                            testTypehas.put(xrayTestResponse.getData().get(i).getTestType(), xrayTestResponse.getData().get(i).getId());
                        }
                        setSpinnerTestType();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, xrayTestResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "UploadDocument":
                try {
                    Log.d("UploadDocument",arg0.body().toString());
                    ImageResponse imageResponse = (ImageResponse) arg0.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        strDocumentUrl=imageResponse.getData().getDocumentUrl();
                    }else if (responseCode==614){
                        Toast.makeText(this, imageResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "AddTestXRay":
                try {
                    AddTestXRayResponse addTestXRayResponse = (AddTestXRayResponse) arg0.body();
                    Log.d("GetTestTypeList", addTestXRayResponse.toString());
                    int responseCode = Integer.parseInt(addTestXRayResponse.getResponse().getResponseCode());

                    if (responseCode == 109) {

                    } else if (responseCode == 614) {
                        Toast.makeText(this, addTestXRayResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }

    }

    private void setSpinnerTestType() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,testTypeList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        nature_of_visit_spinner.setAdapter(aa);
        nature_of_visit_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                testTypeId=testTypehas.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onError(Throwable t, String key) {

    }
}
