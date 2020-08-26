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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.addHospitalization.AddHospitalizationParam;
import com.cynoteck.petofyvet.params.addHospitalization.AddHospitalizationRequest;
import com.cynoteck.petofyvet.params.addPetClinicParamRequest.AddPetClinicParam;
import com.cynoteck.petofyvet.params.addPetClinicParamRequest.AddPetClinicRequest;
import com.cynoteck.petofyvet.response.addHospitalizationResponse.AddhospitalizationResposee;
import com.cynoteck.petofyvet.response.addPet.imageUpload.ImageResponse;
import com.cynoteck.petofyvet.response.clinicVisist.ClinicVisitResponse;
import com.cynoteck.petofyvet.response.hospitalTypeListResponse.HospitalAddmissionTypeResp;
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

public class AddHospitalizationDeatilsActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {
    EditText veterian_name_ET,veterian_phone_ET,hospital_name_ET,hospital_phone_ET,reson_of_hospitalization_ET,result_ET;
    Spinner hospital_type_spinner;
    TextView calenderTextView_admission_date,hospitalization_peto_edit_reg_number_dialog,
             calenderTextView_discharge_date_TV,hospitalization_upload_documents;
    Button save_BT;
    ImageView hospitalization_document_name,hospitalization_back_arrow_IV;

    Methods methods;

    ArrayList<String> hospitalTypeArrayList;

    HashMap<String,String> hospitalTypeHashmap=new HashMap<>();

    DatePickerDialog picker;

    String hospitalizationStr="",hospitalizationId="",pet_id="",pet_name="",pet_owner_name="",pet_sex="",pet_unique_id="",strDocumentUrl="";

    private static final String IMAGE_DIRECTORY = "/Picture";
    private int GALLERY = 1, CAMERA = 2;
    File file = null;
    Dialog dialog;
    Bitmap bitmap, thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hospitalization_deatils);
        init();
        requestMultiplePermissions();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        hospitalization_peto_edit_reg_number_dialog=findViewById(R.id.hospitalization_peto_edit_reg_number_dialog);
        veterian_name_ET=findViewById(R.id.veterian_name_ET);
        veterian_phone_ET=findViewById(R.id.veterian_phone_ET);
        hospital_name_ET=findViewById(R.id.hospital_name_ET);
        hospital_phone_ET=findViewById(R.id.hospital_phone_ET);
        reson_of_hospitalization_ET=findViewById(R.id.reson_of_hospitalization_ET);
        hospitalization_upload_documents=findViewById(R.id.hospitalization_upload_documents);
        result_ET=findViewById(R.id.result_ET);
        hospitalization_document_name=findViewById(R.id.hospitalization_document_name);
        hospital_type_spinner=findViewById(R.id.hospital_type_spinner);
        calenderTextView_admission_date=findViewById(R.id.calenderTextView_admission_date);
        calenderTextView_discharge_date_TV=findViewById(R.id.calenderTextView_discharge_date_TV);
        save_BT=findViewById(R.id.save_BT);
        hospitalization_back_arrow_IV=findViewById(R.id.hospitalization_back_arrow_IV);

        calenderTextView_admission_date.setOnClickListener(this);
        calenderTextView_discharge_date_TV.setOnClickListener(this);
        save_BT.setOnClickListener(this);
        hospitalization_upload_documents.setOnClickListener(this);
        hospitalization_back_arrow_IV.setOnClickListener(this);

        if (extras != null) {
            pet_id = extras.getString("pet_id");
            pet_name = extras.getString("pet_name");
            pet_owner_name = extras.getString("pet_owner_name");
            pet_sex = extras.getString("pet_sex");
            pet_unique_id = extras.getString("pet_unique_id");
            hospitalization_peto_edit_reg_number_dialog.setText(pet_unique_id);
            veterian_name_ET.setText(Config.user_Veterian_name);
            veterian_phone_ET.setText(Config.user_Veterian_phone);
        }

        methods=new Methods(this);

        if (methods.isInternetOn()){
            getHospitalTypeList();
        }else {
            methods.DialogInternet();
        }


    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.calenderTextView_admission_date:
                final Calendar cldrFolwUpDt = Calendar.getInstance();
                int dayFolwUpDt = cldrFolwUpDt.get(Calendar.DAY_OF_MONTH);
                int monthFolwUpDt = cldrFolwUpDt.get(Calendar.MONTH);
                int yearFolwUpDt = cldrFolwUpDt.get(Calendar.YEAR);
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderTextView_admission_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, dayFolwUpDt, monthFolwUpDt, yearFolwUpDt);
                picker.show();
                break;
            case R.id.calenderTextView_discharge_date_TV:
                final Calendar cldrDeschrgDt = Calendar.getInstance();
                int dayDeschrgDt = cldrDeschrgDt.get(Calendar.DAY_OF_MONTH);
                int monthDeschrgDt = cldrDeschrgDt.get(Calendar.MONTH);
                int yearDeschrgDt = cldrDeschrgDt.get(Calendar.YEAR);
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderTextView_discharge_date_TV.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, dayDeschrgDt, monthDeschrgDt, yearDeschrgDt);
                picker.show();
                break;
            case R.id.hospitalization_upload_documents:
                showPictureDialog();
                break;
            case R.id.save_BT:
                String strRequstVeterian=veterian_name_ET.getText().toString();
                String strPhoneNumber=veterian_phone_ET.getText().toString();
                String strHospitalName=hospital_name_ET.getText().toString();
                String strHospitalphoneNumber=hospital_phone_ET.getText().toString();
                String strResonsOfHospitalization=reson_of_hospitalization_ET.getText().toString();
                String strResult=result_ET.getText().toString();
                String strHospitalAdmissionDt=calenderTextView_admission_date.getText().toString();
                String strHospitalDischargeDt=calenderTextView_discharge_date_TV.getText().toString();

                if(strRequstVeterian.isEmpty()){
                    veterian_name_ET.setError("Enter Veterinarian Name");
                    hospital_name_ET.setError(null);
                    reson_of_hospitalization_ET.setError(null);
                    calenderTextView_admission_date.setError(null);
                    calenderTextView_discharge_date_TV.setError(null);
                }
                else if(strHospitalName.isEmpty())
                {
                    veterian_name_ET.setError(null);
                    hospital_name_ET.setError("Enter Hospital Name");
                    reson_of_hospitalization_ET.setError(null);
                    calenderTextView_admission_date.setError(null);
                    calenderTextView_discharge_date_TV.setError(null);
                }
                else if(strResonsOfHospitalization.isEmpty())
                {
                    veterian_name_ET.setError(null);
                    hospital_name_ET.setError(null);
                    reson_of_hospitalization_ET.setError("Reason of Hospitalization");
                    calenderTextView_admission_date.setError(null);
                    calenderTextView_discharge_date_TV.setError(null);
                }
                else if(strHospitalAdmissionDt.isEmpty())
                {
                    veterian_name_ET.setError(null);
                    hospital_name_ET.setError(null);
                    reson_of_hospitalization_ET.setError(null);
                    calenderTextView_admission_date.setError("Enter Admission Date");
                    calenderTextView_discharge_date_TV.setError(null);
                }
                else if(strHospitalDischargeDt.isEmpty())
                {
                    veterian_name_ET.setError(null);
                    hospital_name_ET.setError(null);
                    reson_of_hospitalization_ET.setError(null);
                    calenderTextView_admission_date.setError(null);
                    calenderTextView_discharge_date_TV.setError("Enter discharge Date");
                }
                else if(hospitalizationStr.equals("Select Hospital Type"))
                {
                    veterian_name_ET.setError(null);
                    hospital_name_ET.setError(null);
                    reson_of_hospitalization_ET.setError(null);
                    calenderTextView_admission_date.setError(null);
                    calenderTextView_discharge_date_TV.setError(null);
                    Toast.makeText(this, "Select Hospitalization type ", Toast.LENGTH_SHORT).show();
                }
                else{
                    AddHospitalizationParam addHospitalizationParam=new AddHospitalizationParam();
                    addHospitalizationParam.setPetId(pet_id);
                    addHospitalizationParam.setRequestingVeterinarian(strRequstVeterian);
                    addHospitalizationParam.setVeterinarianPhone(strPhoneNumber);
                    addHospitalizationParam.setHospitalizationTypeId(hospitalizationId);
                    addHospitalizationParam.setAdmissionDate(strHospitalAdmissionDt);
                    addHospitalizationParam.setDischargeDate(strHospitalDischargeDt);
                    addHospitalizationParam.setHospitalName(strHospitalName);
                    addHospitalizationParam.setHospitalPhone(strHospitalphoneNumber);
                    addHospitalizationParam.setAddress("");
                    addHospitalizationParam.setCountryId("");
                    addHospitalizationParam.setStateId("");
                    addHospitalizationParam.setCityId("");
                    addHospitalizationParam.setZipCode("");
                    addHospitalizationParam.setReasonForHospitalization(strResonsOfHospitalization);
                    addHospitalizationParam.setDiagnosisTreatmentProcedure(strResult);
                    addHospitalizationParam.setDocuments(strDocumentUrl);
                    AddHospitalizationRequest addHospitalizationRequest=new AddHospitalizationRequest();
                    addHospitalizationRequest.setData(addHospitalizationParam);

                    if (methods.isInternetOn()){
                        addHospitalization(addHospitalizationRequest);
                    }else {
                        methods.DialogInternet();
                    }

                }
                break;
            case R.id.hospitalization_back_arrow_IV:
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
                    hospitalization_document_name.setImageBitmap(bitmap);
                    saveImage(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AddHospitalizationDeatilsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if (requestCode == CAMERA) {

            if (data.getData() == null)
            {
                thumbnail = (Bitmap) data.getExtras().get("data");
                Log.e("jghl",""+thumbnail);
                hospitalization_document_name.setImageBitmap(thumbnail);
                saveImage(thumbnail);
                Toast.makeText(AddHospitalizationDeatilsActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            }

            else{
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(AddHospitalizationDeatilsActivity.this.getContentResolver(), data.getData());
                    hospitalization_document_name.setImageBitmap(bitmap);
                    saveImage(bitmap);
                    Toast.makeText(AddHospitalizationDeatilsActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AddHospitalizationDeatilsActivity.this, "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AddHospitalizationDeatilsActivity.this, "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void addHospitalization(AddHospitalizationRequest addHospitalizationRequest) {
        ApiService<AddhospitalizationResposee> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().addPetHospitalization(Config.token,addHospitalizationRequest), "AddPetHospitalization");
        Log.e("AddPetHospitalParam","===>"+addHospitalizationRequest);

    }

    private void getHospitalTypeList() {
        ApiService<HospitalAddmissionTypeResp> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getHospitalTypeList(Config.token), "getHospitalTypeList");
    }


    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "getHospitalTypeList":
                try {
                    HospitalAddmissionTypeResp hospitalAddmissionTypeResponse = (HospitalAddmissionTypeResp) arg0.body();
                    Log.d("getHospitalTypeList", hospitalAddmissionTypeResponse.toString());
                    int responseCode = Integer.parseInt(hospitalAddmissionTypeResponse.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        hospitalTypeArrayList = new ArrayList<>();
                        hospitalTypeArrayList.add("Select Hospital Type");
                        for (int i = 0; i < hospitalAddmissionTypeResponse.getData().size(); i++) {
                            hospitalTypeArrayList.add(hospitalAddmissionTypeResponse.getData().get(i).getHospitalization());
                            hospitalTypeHashmap.put(hospitalAddmissionTypeResponse.getData().get(i).getHospitalization(), hospitalAddmissionTypeResponse.getData().get(i).getId());
                        }
                        setSpinnerHospitalizationType();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, hospitalAddmissionTypeResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
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
            case "AddPetHospitalization":
                try {
                    AddhospitalizationResposee addhospitalizationResposee = (AddhospitalizationResposee) arg0.body();
                    Log.d("AddPetHospitalization", addhospitalizationResposee.toString());
                    int responseCode = Integer.parseInt(addhospitalizationResposee.getResponse().getResponseCode());

                    if (responseCode == 109) {

                    } else if (responseCode == 614) {
                        Toast.makeText(this, addhospitalizationResposee.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }

    }

    private void setSpinnerHospitalizationType() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,hospitalTypeArrayList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        hospital_type_spinner.setAdapter(aa);
        hospital_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                hospitalizationStr=item;
                hospitalizationId=hospitalTypeHashmap.get(item);
                Log.d("Hospitalization_Id",""+hospitalizationId);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onError(Throwable t, String key) {

    }
}
