package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Collection;
import com.mobileclient.service.CollectionService;
import com.mobileclient.domain.Novel;
import com.mobileclient.service.NovelService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class CollectionAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明被收藏小说下拉框
	private Spinner spinner_novelObj;
	private ArrayAdapter<String> novelObj_adapter;
	private static  String[] novelObj_ShowText  = null;
	private List<Novel> novelList = null;
	/*被收藏小说管理业务逻辑层*/
	private NovelService novelService = new NovelService();
	// 声明收藏用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*收藏用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明收藏时间输入框
	private EditText ET_collectTime;
	protected String carmera_path;
	/*要保存的收藏信息*/
	Collection collection = new Collection();
	/*收藏管理业务逻辑层*/
	private CollectionService collectionService = new CollectionService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.collection_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加收藏");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_novelObj = (Spinner) findViewById(R.id.Spinner_novelObj);
		// 获取所有的被收藏小说
		try {
			novelList = novelService.QueryNovel(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int novelCount = novelList.size();
		novelObj_ShowText = new String[novelCount];
		for(int i=0;i<novelCount;i++) { 
			novelObj_ShowText[i] = novelList.get(i).getNovelName();
		}
		// 将可选内容与ArrayAdapter连接起来
		novelObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, novelObj_ShowText);
		// 设置下拉列表的风格
		novelObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_novelObj.setAdapter(novelObj_adapter);
		// 添加事件Spinner事件监听
		spinner_novelObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				collection.setNovelObj(novelList.get(arg2).getNovelId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_novelObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的收藏用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				collection.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_collectTime = (EditText) findViewById(R.id.ET_collectTime);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加收藏按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取收藏时间*/ 
					if(ET_collectTime.getText().toString().equals("")) {
						Toast.makeText(CollectionAddActivity.this, "收藏时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_collectTime.setFocusable(true);
						ET_collectTime.requestFocus();
						return;	
					}
					collection.setCollectTime(ET_collectTime.getText().toString());
					/*调用业务逻辑层上传收藏信息*/
					CollectionAddActivity.this.setTitle("正在上传收藏信息，稍等...");
					String result = collectionService.AddCollection(collection);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
