package com.cynoteck.petofyOPHR.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.cynoteck.petofyOPHR.R;
import com.cynoteck.petofyOPHR.api.ApiClient;
import com.cynoteck.petofyOPHR.api.ApiResponse;
import com.cynoteck.petofyOPHR.api.ApiService;
import com.cynoteck.petofyOPHR.params.addHospitalization.AddHospitalizationParam;
import com.cynoteck.petofyOPHR.params.addHospitalization.AddHospitalizationRequest;
import com.cynoteck.petofyOPHR.params.updateHospitalizationParams.UpdateHospitalizationParams;
import com.cynoteck.petofyOPHR.params.updateHospitalizationParams.UpdateHospitalizationRequest;
import com.cynoteck.petofyOPHR.response.addHospitalizationResponse.AddhospitalizationResposee;
import com.cynoteck.petofyOPHR.response.addPet.imageUpload.ImageResponse;
import com.cynoteck.petofyOPHR.response.hospitalTypeListResponse.HospitalAddmissionTypeResp;
import com.cynoteck.petofyOPHR.utils.Config;
import com.cynoteck.petofyOPHR.utils.FilePath;
import com.cynoteck.petofyOPHR.utils.Methods;
import com.google.android.material.card.MaterialCardView;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class AddHospitalizationDeatilsActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {
    EditText veterian_name_ET,veterian_phone_ET,hospital_name_ET,hospital_phone_ET,reson_of_hospitalization_ET,result_ET;
    Spinner hospital_type_spinner;
    TextView document_headline_TV,  calenderTextView_admission_date,hospitalization_peto_edit_reg_number_dialog,
            calenderTextView_discharge_date_TV,doctorPrescription_headline_TV;
    Button save_BT;
    ImageView hospitalization_document_name,hospitalization_upload_documents;
    MaterialCardView back_arrow_CV;
    Methods methods;

    ArrayList<String> hospitalTypeArrayList;

    HashMap<String,String> hospitalTypeHashmap=new HashMap<>();

    DatePickerDialog picker;

    String report_id="",hospitalizationStr="",hospitalizationId="",pet_id="",pet_name="",pet_owner_name="",pet_sex="",pet_unique_id="",strDocumentUrl="",hospital_type,hospital_name,hospital_phone,admission,discharge,result,reason,type;

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
        back_arrow_CV=findViewById(R.id.back_arrow_CV);
        doctorPrescription_headline_TV=findViewById(R.id.doctorPrescription_headline_TV);
        document_headline_TV=findViewById(R.id.document_headline_TV);
        calenderTextView_admission_date.setOnClickListener(this);
        calenderTextView_discharge_date_TV.setOnClickListener(this);
        save_BT.setOnClickListener(this);
        hospitalization_upload_documents.setOnClickListener(this);
        back_arrow_CV.setOnClickListener(this);

        if (extras != null) {
            report_id = extras.getString("report_id");
            pet_id = extras.getString("pet_id");
            pet_name = extras.getString("pet_name");
            pet_owner_name = extras.getString("pet_owner_name");
            pet_sex = extras.getString("pet_sex");
            pet_unique_id = extras.getString("pet_unique_id");
            hospital_type = extras.getString("hospital_type");
            hospital_name = extras.getString("hospital_name");
            hospital_phone = extras.getString("hospital_phone");
            admission = extras.getString("admission");
            discharge = extras.getString("discharge");
            result = extras.getString("result");
            reason = extras.getString("reason");
            type = extras.getString("type");

            hospitalization_peto_edit_reg_number_dialog.setText(pet_unique_id);
            veterian_name_ET.setText(Config.user_Veterian_name);
            veterian_phone_ET.setText(Config.user_Veterian_phone);
        }

        methods=new Methods(this);
        if (type.equals("Update Hospitalization")){
            doctorPrescription_headline_TV.setText(type);
            calenderTextView_admission_date.setText(admission);
            calenderTextView_discharge_date_TV.setText(discharge);
            hospital_name_ET.setText(hospital_name);
            hospital_phone_ET.setText(hospital_phone);
            reson_of_hospitalization_ET.setText(reason);
            result_ET.setText(result);
            save_BT.setText("UPDATE");

        }else {
            save_BT.setText("Save");
        }

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
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderTextView_admission_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                break;
            case R.id.calenderTextView_discharge_date_TV:
                final Calendar cldrDis = Calendar.getInstance();
                int dayDis = cldrDis.get(Calendar.DAY_OF_MONTH);
                int monthDis = cldrDis.get(Calendar.MONTH);
                int yearDis = cldrDis.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderTextView_discharge_date_TV.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, yearDis, monthDis, dayDis);
                picker.show();
                break;
            case R.id.hospitalization_upload_documents:
                showPictureDialog();
//                Intent intent = new Intent();
//                intent.setType("application/pdf");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select PDF"), 1);
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
                else {
                    methods.showCustomProgressBarDialog(this);

                    if (type.equals("Update Hospitalization")) {
                        UpdateHospitalizationParams updateHospitalizationParams = new UpdateHospitalizationParams();
                        updateHospitalizationParams.setId(report_id);
                        updateHospitalizationParams.setPetId(pet_id);
                        updateHospitalizationParams.setRequestingVeterinarian(strRequstVeterian);
                        updateHospitalizationParams.setVeterinarianPhone(strPhoneNumber);
                        updateHospitalizationParams.setHospitalizationTypeId(hospitalizationId);
                        updateHospitalizationParams.setAdmissionDate(strHospitalAdmissionDt);
                        updateHospitalizationParams.setDischargeDate(strHospitalDischargeDt);
                        updateHospitalizationParams.setHospitalName(strHospitalName);
                        updateHospitalizationParams.setHospitalPhone(strHospitalphoneNumber);
                        updateHospitalizationParams.setAddress("");
                        updateHospitalizationParams.setCountryId("");
                        updateHospitalizationParams.setStateId("");
                        updateHospitalizationParams.setCityId("");
                        updateHospitalizationParams.setZipCode("");
                        updateHospitalizationParams.setReasonForHospitalization(strResonsOfHospitalization);
                        updateHospitalizationParams.setDiagnosisTreatmentProcedure(strResult);
                        updateHospitalizationParams.setDocuments(strDocumentUrl);
                        UpdateHospitalizationRequest updateHospitalizationRequest  = new UpdateHospitalizationRequest();
                        updateHospitalizationRequest.setData(updateHospitalizationParams);
                        if (methods.isInternetOn()) {
                            updateHospitalization(updateHospitalizationRequest);
                        } else {
                            methods.DialogInternet();
                        }
                    } else {
                        AddHospitalizationParam addHospitalizationParam = new AddHospitalizationParam();
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
                        AddHospitalizationRequest addHospitalizationRequest = new AddHospitalizationRequest();
                        addHospitalizationRequest.setData(addHospitalizationParam);
                        if (methods.isInternetOn()) {
                            addHospitalization(addHospitalizationRequest);
                        } else {
                            methods.DialogInternet();
                        }
                    }

                }
                break;
            case R.id.back_arrow_CV:
                onBackPressed();
                break;

        }

    }

    private void updateHospitalization(UpdateHospitalizationRequest updateHospitalizationRequest) {
        ApiService<AddhospitalizationResposee> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().updatePetHospitalization(Config.token,updateHospitalizationRequest), "UpdatePetHospitalization");
        Log.e("AddPetHospitalParam","===>"+updateHospitalizationRequest);

    }

    private void showPictureDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        RelativeLayout select_camera = (RelativeLayout) dialog.findViewById(R.id.select_camera);
        RelativeLayout select_gallery = (RelativeLayout) dialog.findViewById(R.id.select_gallery);
        RelativeLayout cancel_dialog = (RelativeLayout) dialog.findViewById(R.id.cancel_dialog);

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
    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        dialog.dismiss();
        if (resultCode == RESULT_CANCELED) {
            return;
        }
/*        if (requestCode == 1) {
            Uri contentURI = data.getData();
            String path = FilePath.getPath(this, contentURI);
//            File file = new File(contentURI.getPath());
            UploadImages(file);
        }else*/
        if (requestCode == GALLERY) {
            dialog.dismiss();
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
            dialog.dismiss();
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
        methods.showCustomProgressBarDialog(this);
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
                            Log.d("PERMISSION","All permissions are granted by user!");
//                            Toast.makeText(AddHospitalizationDeatilsActivity.this, "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
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
                        //Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
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
                    methods.customProgressDismiss();
                    Log.d("UploadDocument",arg0.body().toString());
                    ImageResponse imageResponse = (ImageResponse) arg0.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        document_headline_TV.setText("Document Uploaded");
//                        hospitalization_upload_documents.setVisibility(View.GONE);
                       // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
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
                    methods.customProgressDismiss();
                    AddhospitalizationResposee addhospitalizationResposee = (AddhospitalizationResposee) arg0.body();
                    Log.d("AddPetHospitalization", addhospitalizationResposee.toString());
                    int responseCode = Integer.parseInt(addhospitalizationResposee.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        Config.type ="Hospitalization";
                        onBackPressed();
                        Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, addhospitalizationResposee.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "UpdatePetHospitalization":
                try {
                    methods.customProgressDismiss();
                    AddhospitalizationResposee addhospitalizationResposee = (AddhospitalizationResposee) arg0.body();
                    Log.d("UpdateHospitalization", addhospitalizationResposee.toString());
                    int responseCode = Integer.parseInt(addhospitalizationResposee.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        Config.type ="Hospitalization";
                        onBackPressed();
                        Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
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
        if (type.equals("Update Hospitalization")) {
            if (!hospital_type.equals("")) {
                int spinnerPosition = aa.getPosition(hospital_type);
                hospital_type_spinner.setSelection(spinnerPosition);
            }
        }
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
        Log.e("Error",t.getLocalizedMessage());
    }
}