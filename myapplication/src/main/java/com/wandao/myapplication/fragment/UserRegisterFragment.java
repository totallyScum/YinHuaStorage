package com.wandao.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.idl.sample.api.FaceApi;
import com.baidu.idl.sample.common.GlobalSet;
import com.baidu.idl.sample.manager.FaceSDKManager;
import com.baidu.idl.sample.ui.IminectRegisterActivity;
import com.baidu.idl.sample.ui.OrbbecMiniRegisterActivity;
import com.baidu.idl.sample.ui.OrbbecProRegisterActivity;
import com.baidu.idl.sample.utils.ToastUtils;
import com.wandao.myapplication.R;
import com.wandao.myapplication.activity.RegisterActivity;
import com.wandao.myapplication.activity.RegisterInfoActivity;
import com.wandao.myapplication.greendao.User;
import com.wandao.myapplication.ui.QNumberPicker;
import com.wandao.myapplication.utils.DbUtils;
import com.wandao.myapplication.utils.SPUtils;
import com.wandao.myapplication.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserRegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRegisterFragment extends Fragment  implements View.OnClickListener, NumberPicker.OnValueChangeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
QNumberPicker numberPicker;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RelativeLayout mLayoutInput;
    private View mViewBg;
    private EditText mNickView;
    private Button goBtn;
    private Button goRegister;
    private TextView mTextBatchRegister;
    private RegisterInfoActivity mContext;
    private String mNickName;
    private EditText userID;
    private EditText userTel;
    private EditText userEmail;
    private EditText userDepartment;
    private EditText userBoxNumber;
    private Spinner userDepartmentSpinner;
    private String mId;
    private String mTel;
    private String mEmail;
    private String mDepartment;
    private int mBoxNumber;
    private User user;
    private NumberPicker mNumberPicker;
    public boolean isCheckedAdmin=false;
    private View view;
    public UserRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserRegisterFragment newInstance(String param1, String param2) {
        UserRegisterFragment fragment = new UserRegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);

                this.view= inflater.inflate(R.layout.fragment_user_register, container, false);
            }
            return view;
        }
        // Inflate the layout for this fragment
        this.view= inflater.inflate(R.layout.fragment_user_register, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("eeeeee","eeeeeeeeee");
        numberPicker=getActivity().findViewById(R.id.mNumberPicker);
        setNumberPickerDividerColor(numberPicker);
        getSpinnerData();
        initView();
        initListener();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), R.layout.register_spinner_item,
                getSpinnerData());
        userDepartmentSpinner.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = null;
        if (id == R.id.go_btn) {
            if (getEditText()) {
                jumpRegister(intent);
            }
        } else if (id == R.id.text_batch_register) {
//            intent = new Intent(getContext(), BatchImportActivity.class);
//            startActivity(intent);
//            finish();
        }
        }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setNumberPickerDividerColor(NumberPicker numberPicker) {
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(picker, new ColorDrawable(this.getResources().getColor(R.color.grey_background)));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private List<String> getSpinnerData() {
        // 数据源
        List<String> dataList = new ArrayList<String>();

        //dataList = StringUtils.StringToArrayList((String) SPUtils.get(this, "department", "未设置"));
        dataList = StringUtils.StringToArrayList((String) SPUtils.get(getContext(), "department", "合规风控部,固定收益部,集中交易部,权益投资部,研究部,特定客户资产管理部"));

        return dataList;
    }
    private void initView() {
        mLayoutInput = getActivity().findViewById(R.id.layout_input);
        mViewBg = getActivity().findViewById(R.id.view_bg);
        mNickView = getActivity().findViewById(R.id.nick_name);
        goBtn = getActivity().findViewById(R.id.go_btn);
        //     goRegister=findViewById(R.id.go_register);
        userID = getActivity().findViewById(R.id.user_id);
        userTel = getActivity().findViewById(R.id.user_tel);
        //   mEmail = findViewById(R.id.user);
        userDepartmentSpinner = getActivity().findViewById(R.id.user_department_spinner);
        userBoxNumber = getActivity().findViewById(R.id.user_box_number);
        mNumberPicker=null;
        mNumberPicker = (NumberPicker) getActivity().findViewById(R.id.mNumberPicker);
        mNumberPicker.setOrientation(LinearLayout.HORIZONTAL);
//        mNumberPicker.setMaxValue(20);
//        mNumberPicker.setMinValue(1);
//        mNumberPicker.setValue(1);

        ArrayList<String> list = DbUtils.getUnbindNumber();

        if (DbUtils.getUnbindNumber() != null&&getActivity().getIntent().getStringExtra("id")==null) {
            if (DbUtils.getUnbindNumber().size() == 0) {
                mNumberPicker.setVisibility(View.INVISIBLE);
            } else {
                String[] temp = new String[DbUtils.getUnbindNumber().size()];
                for (int i = 0; i < list.size(); i++) {
                    temp[i] = list.get(i);
                }
                mNumberPicker.setMaxValue(list.size()-1);
                mNumberPicker.setDisplayedValues(temp);
            }
            Log.d("qwertyu","666666666666");
        }
        if(getActivity().getIntent().getStringExtra("id")!=null)
        {
            //  mNickView.setText(getIntent().getStringExtra("id"));
            String id =getActivity().getIntent().getStringExtra("id");
            userID.setText(id);
            mNickView.setText(DbUtils.queryUser(Integer.parseInt(id)).getName());
            mNumberPicker.setVisibility(View.INVISIBLE);

        };
    }
    private void initListener() {
        goBtn.setOnClickListener(this);
//        goRegister.setOnClickListener(this);
    }
    private void jumpRegister(Intent intent) {
        // name填写完毕，去注册人脸
        if (GlobalSet.getLiveStatusValue() == GlobalSet.LIVE_STATUS.RGB_DEPTH) {
            GlobalSet.STRUCTURED_LIGHT structuredLightValue = GlobalSet.getStructuredLightValue();
            if (structuredLightValue == GlobalSet.STRUCTURED_LIGHT.OBI_ASTRA_PRO
                    || structuredLightValue == GlobalSet.STRUCTURED_LIGHT.OBI_ATLAS
                    || structuredLightValue == GlobalSet.STRUCTURED_LIGHT.OBI_ASTRA_PRO_s1
                    || structuredLightValue == GlobalSet.STRUCTURED_LIGHT.OBI_ASTRA_DB
                    || structuredLightValue == GlobalSet.STRUCTURED_LIGHT.OBI_DEEYEA) {
                intent = new Intent(getContext(), OrbbecProRegisterActivity.class);
            } else if (structuredLightValue == GlobalSet.STRUCTURED_LIGHT.OBI_ASTRA_MINI) {
                intent = new Intent(getContext(), OrbbecMiniRegisterActivity.class);
            } else if (structuredLightValue == GlobalSet.STRUCTURED_LIGHT.HUAJIE_AMY_MINI) {
                intent = new Intent(getContext(), IminectRegisterActivity.class);
            }
        } else {
            intent = new Intent(getContext(), RegisterActivity.class);
        }
        intent.putExtra("mNickName", mId);
        intent.putExtra("user", user);
        startActivity(intent);
    }


        public boolean getEditText() {
            Editable editable = mNickView.getText();
            Editable editable1 = userID.getText();

            if (editable != null && editable1 != null ) {
                mId = userID.getText().toString();
                mNickName = mNickView.getText().toString();
                mDepartment = userDepartmentSpinner.getSelectedItem().toString();
                mTel = userTel.getText().toString() + "";
                if (getActivity().getIntent().getStringExtra("id")!=null)
                {
                    mBoxNumber=DbUtils.queryUser(Integer.parseInt(getActivity().getIntent().getStringExtra("id"))).getBoxId();
                }else {
                    mBoxNumber = Integer.parseInt(mNumberPicker.getDisplayedValues()[mNumberPicker.getValue()]);
                }
                Log.d("qwertytuy",mBoxNumber+"");
//             if (mNumberPicker.getValue()!=0)
//             else {
//                 mBoxNumber=0;                                                          // 柜门号0为保存字符串
//             }
                String nameResult = FaceApi.getInstance().isValidName(mNickName);      //姓名转化
                String idResult = isValidID(mId);
                //         String telResult = isMobileNo(mTel);
                if ("0".equals(nameResult) && "0".equals(idResult) ) {
                    // 设置注册时的昵称
                    FaceSDKManager.getInstance().getFaceLiveness().setRegistNickName(mId);
                    // 隐藏键盘
                    com.baidu.idl.sample.utils.Utils.hideKeyboard((Activity) mContext);
                    if (isCheckedAdmin==true)
                        user = new User(Long.parseLong(mId), mNickName, mTel, mEmail, mDepartment, true, 0, 0);  //注册为管理员

                    else
                        user = new User(Long.parseLong(mId), mNickName, mTel, mEmail, mDepartment, false, 0, mBoxNumber);
                    return true;
                } else {
                    ToastUtils.toast(getContext(), nameResult);
                    ToastUtils.toast(getContext(), idResult);
                    //   ToastUtils.toast(this, telResult);

                }
            }
            return false;
        }

    public static String isValidID(String id) {
        if (id == null || "".equals(id.trim())) {
            return "ID为空";
        }

        // 姓名过长
        if (id.length() > 20) {
            return "ID过长";
        }

        // 含有特殊符号
        String regex = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）—"
                + "—+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(id);
        if (m.find()) {
            return "ID含有特殊符号";
        }

        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(id);
        if( !isNum.matches() ){
            return "ID不为纯数字";
        }
        if(DbUtils.queryUser(Integer.parseInt(id))!=null)
//        if(false)
            return "ID已存在";
        return "0";
    }
}
